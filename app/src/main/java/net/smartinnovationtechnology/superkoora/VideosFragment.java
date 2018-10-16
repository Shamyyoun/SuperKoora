package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import adapters.VideosAdapter;
import datamodels.Cache;
import datamodels.Constants;
import datamodels.Video;
import json.VideosHandler;
import utils.InternetUtil;
import views.SwipeProgressFragment;

/**
 * Created by Shamyyoun on 2/24/2015.
 */
public class VideosFragment extends SwipeProgressFragment {
    private MainActivity mActivity;
    private NetworkController mNetworkController;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private VideosAdapter mVideosAdapter;
    private List<Video> mVideos;
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
        mVideos = new ArrayList<>();

        // set activity title
        mActivity.setTitle(getString(R.string.videos));

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
                        loadMore(mVideos.get(mVideos.size() - 1).getId());
                    }
                }
            }
        });

        // get cached videos
        String response = Cache.getVideosResponse(mActivity);
        if (response != null) {
            try {
                // parse videos
                JSONArray jsonArray = new JSONArray(response);
                VideosHandler handler = new VideosHandler(jsonArray);
                mVideos = handler.handle();

                // set recycler adapter
                setVideosAdapter();
                showMain();

                // load from server to update items
                loadVideos(false);
            } catch (Exception e) {
                // load from server showing msgs
                loadVideos(true);
                e.printStackTrace();
            }
        } else {
            // load from server showing msgs
            loadVideos(true);
        }
    }

    /**
     * method, used to set videos recycler adapter and set item click listener
     */
    private void setVideosAdapter() {
        mVideosAdapter = new VideosAdapter(mActivity, mVideos, R.layout.recycler_videos_item);
        mRecyclerView.setAdapter(mVideosAdapter);
        mVideosAdapter.setOnItemClickListener(new VideosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // check internet connection
                if (InternetUtil.isConnected(mActivity)) {
                    // connected
                    // start video player activity
                    Intent intent = new Intent(mActivity, VideoPlayerActivity.class);
                    intent.putExtra(Constants.KEY_VIDEO_ID, mVideos.get(position).getYoutubeId());
                    startActivity(intent);
                } else {
                    // not connected
                    // show error toast
                    Toast.makeText(mActivity, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * method, used to get videos from server and handle it recycler view
     */
    private void loadVideos(final boolean showMsgs) {
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
        String url = AppController.getInstance(mActivity).getEndPoint() + "/Videos-List/"
                + AppController.getActiveUser(mActivity).getId() + "/" + Constants.SERVER_LIMIT_VIDEOS;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // handle items
                VideosHandler handler = new VideosHandler(jsonArray);
                mVideos = handler.handle();

                // check items
                if (mVideos != null) {
                    // check list size
                    if (mVideos.size() == 0) {
                        if (showMsgs)
                            // show empty
                            showEmpty(R.string.no_news);
                    } else {
                        // set recycler adapter
                        setVideosAdapter();
                        // cache videos
                        Cache.updateVideosResponse(mActivity, jsonArray.toString());
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
        request.setTag(Constants.VOLLEY_REQ_VIDEOS);
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.CON_TIMEOUT_VIDEOS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_VIDEOS);
        mNetworkController.addToRequestQueue(request);
    }

    /**
     * method, used to get more videos from server and handle it recycler view
     */
    private void loadMore(int lastId) {
        // check internet connection
        if (!InternetUtil.isConnected(mActivity)) {
            return;
        }

        // add progress footer
        Video footerItem = new Video(-1);
        mVideos.add(footerItem);
        mFooterPosition = mVideos.size() - 1;
        mVideosAdapter.notifyItemInserted(mFooterPosition);
        mLoadingItems = true;

        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/Videos-List/"
                + AppController.getActiveUser(mActivity).getId() + "/" + Constants.SERVER_LIMIT_VIDEOS + "/" + lastId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // handle items
                VideosHandler handler = new VideosHandler(jsonArray);
                List<Video> newVideos = handler.handle();

                // check items
                if (newVideos != null) {
                    // check size
                    if (newVideos.size() != 0) {
                        // check if size is less than limit
                        if (newVideos.size() < Constants.SERVER_LIMIT_VIDEOS) {
                            // not more items in the next time
                            mNoMoreItems = true;
                        }

                        // add new items
                        mVideos.addAll(newVideos);
                        mVideosAdapter.notifyItemRangeInserted(mVideos.size() - newVideos.size() - 1, newVideos.size());
                    } else {
                        // no more items
                        mNoMoreItems = true;
                    }
                }

                // remove progress footer
                mVideos.remove(mFooterPosition);
                mVideosAdapter.notifyItemRemoved(mFooterPosition);
                mLoadingItems = false;
                mFooterPosition = 0;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // remove progress footer
                mVideos.remove(mFooterPosition);
                mVideosAdapter.notifyItemRemoved(mFooterPosition);
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
        request.setTag(Constants.VOLLEY_REQ_VIDEOS);
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.CON_TIMEOUT_VIDEOS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_VIDEOS);
        mNetworkController.addToRequestQueue(request);
    }

    /**
     * overridden method, used to refresh content
     */
    @Override
    protected void onRefresh() {
        loadVideos(true);
    }

    /*
     * overridden method
     */
    @Override
    public void onDestroy() {
        // cancel all appmsgs
        AppMsg.cancelAll(mActivity);
        // cancel running requests
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_VIDEOS);

        super.onDestroy();
    }
}
