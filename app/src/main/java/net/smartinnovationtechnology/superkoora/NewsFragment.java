package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.devspark.appmsg.AppMsg;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.NewsAdapter;
import datamodels.Cache;
import datamodels.Constants;
import datamodels.NewsItem;
import json.NewsHandler;
import utils.InternetUtil;
import views.SwipeProgressFragment;

/**
 * Created by Shamyyoun on 2/24/2015.
 */
public class NewsFragment extends SwipeProgressFragment {
    private MainActivity mActivity;
    private NetworkController mNetworkController;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mNewsAdapter;
    private List<NewsItem> mNewsItems;
    private boolean mLoadingItems; // flag used to know if loading items from server is running or not
    private int mFooterPosition; // used to hold footer position in recycler view
    private boolean mNoMoreItems; // flag used to know if no more items to load

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initComponents(rootView);

        return rootView;
    }

    /**
     * overridden abstract method, used to set content layout resource
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_content;
    }

    /**
     * method used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (MainActivity) getActivity();
        mNetworkController = NetworkController.getInstance(mActivity);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mNewsItems = new ArrayList<>();

        // set activity title
        mActivity.setTitle(getString(R.string.news));

        // customize recycler view
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // check if not loading more is running and there is new items
                if (!mLoadingItems && !mNoMoreItems) {
                    // check if reached to end of the list
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loadMore(mNewsItems.get(mNewsItems.size() - 1).getId());
                    }
                }
            }
        });

        // get cached news
        String response = Cache.getNewsResponse(mActivity);
        if (response != null) {
            try {
                // parse news items
                JSONArray jsonArray = new JSONArray(response);
                NewsHandler handler = new NewsHandler(jsonArray);
                mNewsItems = handler.handle();

                // set recycler adapter
                setNewsAdapter();
                showMain();

                // load from server to update items
                loadNews(false);
            } catch (Exception e) {
                // load from server showing msgs
                loadNews(true);
                e.printStackTrace();
            }
        } else {
            // load news from server showing msgs
            loadNews(true);
        }
    }

    /**
     * method, used to set news recycler adapter and set item click listener
     */
    private void setNewsAdapter() {
        mNewsAdapter = new NewsAdapter(mActivity, mNewsItems, R.layout.recycler_news_item);
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, NewsDetailsActivity.class);
                intent.putExtra(Constants.KEY_NEWS_ITEM, mNewsItems.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * method, used to get news from server and handle it recycler view
     */
    private void loadNews(final boolean showMsgs) {
        // check internet connection
        if (!InternetUtil.isConnected(mActivity)) {
            if (showMsgs)
                // show error
                showError(R.string.no_internet_connection);
            return;
        }

        // show progress
        showProgress();
        mLoadingItems = true;

        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/News-List/"
                + AppController.getActiveUser(mActivity).getId() + "/" + Constants.SERVER_LIMIT_NEWS;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // handle items
                NewsHandler handler = new NewsHandler(jsonArray);
                mNewsItems = handler.handle();

                // check items
                if (mNewsItems != null) {
                    // check list size
                    if (mNewsItems.size() == 0) {
                        if (showMsgs)
                            // show empty
                            showEmpty(R.string.no_news);
                    } else {
                        // set recycler adapter
                        setNewsAdapter();
                        // cache news
                        Cache.updateNewsResponse(mActivity, jsonArray.toString());
                        // reset flags
                        mNoMoreItems = false;
                        mLoadingItems = false;
                        // show main view
                        showMain();
                    }
                } else {
                    if (showMsgs)
                        showError(R.string.unexpected_error);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // show error
                if (showMsgs)
                    showError(R.string.connection_error);
                volleyError.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // add headers to request
                Map<String, String> headers = new HashMap();
                headers.put("Superkoora-Api-Key", AppController.SUPER_KOORA_API_KEY);
                return headers;
            }
        };

        // add request to request queue
        request.setTag(Constants.VOLLEY_REQ_NEWS);
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.CON_TIMEOUT_NEWS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_NEWS);
        mNetworkController.addToRequestQueue(request);
    }

    /**
     * method, used to get more news from server and handle it recycler view
     */
    private void loadMore(int lastId) {
        // check internet connection
        if (!InternetUtil.isConnected(mActivity)) {
            return;
        }

        // add progress footer
        NewsItem footerItem = new NewsItem(-1);
        mNewsItems.add(footerItem);
        mFooterPosition = mNewsItems.size() - 1;
        mNewsAdapter.notifyItemInserted(mFooterPosition);
        mLoadingItems = true;

        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/News-List/"
                + AppController.getActiveUser(mActivity).getId() + "/" + Constants.SERVER_LIMIT_NEWS + "/" + lastId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // handle items
                NewsHandler handler = new NewsHandler(jsonArray);
                List<NewsItem> newItems = handler.handle();

                // check items
                if (newItems != null) {
                    // check size
                    if (newItems.size() != 0) {
                        // check if size is less than limit
                        if (newItems.size() < Constants.SERVER_LIMIT_NEWS) {
                            // not more items in the next time
                            mNoMoreItems = true;
                        }

                        // add new items
                        mNewsItems.addAll(newItems);
                        mNewsAdapter.notifyItemRangeInserted(mNewsItems.size() - newItems.size() - 1, newItems.size());
                    } else {
                        // no more items
                        mNoMoreItems = true;
                    }
                }

                // remove progress footer
                mNewsItems.remove(mFooterPosition);
                mNewsAdapter.notifyItemRemoved(mFooterPosition);
                mLoadingItems = false;
                mFooterPosition = 0;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // remove progress footer
                mNewsItems.remove(mFooterPosition);
                mNewsAdapter.notifyItemRemoved(mFooterPosition);
                mLoadingItems = false;
                mFooterPosition = 0;
                volleyError.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // add headers to request
                Map<String, String> headers = new HashMap();
                headers.put("Superkoora-Api-Key", AppController.SUPER_KOORA_API_KEY);
                return headers;
            }
        };

        // add request to request queue
        request.setTag(Constants.VOLLEY_REQ_NEWS);
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.CON_TIMEOUT_NEWS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_NEWS);
        mNetworkController.addToRequestQueue(request);
    }

    /**
     * overridden method, used to refresh content
     */
    @Override
    protected void onRefresh() {
        loadNews(true);
    }

    /*
     * overridden method
     */
    @Override
    public void onDestroy() {
        // cancel all appmsgs
        AppMsg.cancelAll(mActivity);
        // cancel running requests
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_NEWS);

        super.onDestroy();
    }
}
