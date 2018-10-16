package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.devspark.appmsg.AppMsg;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.mahmoudelshamy.floatingactionbutton.FloatingActionButton;
import com.mahmoudelshamy.stickyparallax.StickyParallaxScrollView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import datamodels.Constants;
import datamodels.NewsItem;
import json.NewsItemHandler;
import utils.InternetUtil;
import utils.StringUtil;
import utils.ViewUtil;
import views.ProgressActivity;

public class NewsDetailsActivity extends ProgressActivity implements View.OnClickListener {
    private NewsItem mNewsItem;
    private NetworkController mNetworkController;
    private BottomSheetLayout mBottomSheet;
    private ImageButton mButtonIcon;
    private TextView mTextTitle;
    private StickyParallaxScrollView mScrollView;
    private ImageView mImageDefault;
    private ImageView mImageImage;
    private TextView mTextNewsTitle;
    private TextView mTextDesc;
    private TextView mTextTags;
    private FloatingActionButton mFabShare;
    private IntentPickerSheetView mIntentPickerSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }


    /**
     * overridden abstract method, used to set content layout resource
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news_details;
    }

    /**
     * method, used to init components
     */
    private void initComponents() {
        mNewsItem = (NewsItem) getIntent().getExtras().getSerializable(Constants.KEY_NEWS_ITEM);
        mNetworkController = NetworkController.getInstance(this);
        mBottomSheet = (BottomSheetLayout) findViewById(R.id.bottomSheet);
        mButtonIcon = (ImageButton) findViewById(R.id.button_icon);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mScrollView = (StickyParallaxScrollView) findViewById(R.id.scrollView);
        mImageDefault = (ImageView) findViewById(R.id.image_default);
        mImageImage = (ImageView) findViewById(R.id.image_image);
        mTextNewsTitle = (TextView) findViewById(R.id.text_newsTitle);
        mTextDesc = (TextView) findViewById(R.id.text_desc);
        mTextTags = (TextView) findViewById(R.id.text_tags);
        mFabShare = (FloatingActionButton) findViewById(R.id.fab_share);

        // customize actionbar
        mTextTitle.setText("");
        mButtonIcon.setImageResource(R.drawable.back_icon);
        mButtonIcon.setOnClickListener(this);

        // customize fab share
        mFabShare.attachToScrollView(mScrollView);

        // load item details
        loadDetails();
    }

    /**
     * method, used to get news item details from server and handle it this views
     */
    private void loadDetails() {
        // check internet connection
        if (!InternetUtil.isConnected(this)) {
            showError(R.string.no_internet_connection);
            return;
        }

        // show progress
        showProgress();

        // make & send request
        String url = AppController.getInstance(this).getEndPoint() + "/News-Details/" + mNewsItem.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // handle item details
                NewsItemHandler handler = new NewsItemHandler(mNewsItem, jsonObject);
                mNewsItem = handler.handle();

                // check item details
                if (mNewsItem != null) {
                    // set data
                    mTextNewsTitle.setText(mNewsItem.getTitle());
                    mTextDesc.setText(Html.fromHtml(mNewsItem.getDetails()));
                    String date = StringUtil.convertToString(mNewsItem.getDate(), "dd-MM-yyyy, HH:mm");
                    mTextTags.setText(mNewsItem.getTags() + " - " + date + " - " + getString(R.string.from) + " " + mNewsItem.getSource());
                    // load image
                    if (!mNewsItem.getImage().isEmpty()) {
                        Picasso.with(NewsDetailsActivity.this).load(mNewsItem.getImage()).into(mImageImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                ViewUtil.fadeView(mImageDefault, false);
                            }

                            @Override
                            public void onError() {
                                ViewUtil.fadeView(mImageDefault, true);
                            }
                        });
                    }

                    // customize share intent
                    final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, mNewsItem.getUrl());
                    mIntentPickerSheet = new IntentPickerSheetView(NewsDetailsActivity.this, shareIntent, R.string.share, new IntentPickerSheetView.OnIntentPickedListener() {
                        @Override
                        public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                            mBottomSheet.dismissSheet();
                            startActivity(activityInfo.getConcreteIntent(shareIntent));
                        }
                    });
                    // add click listener to fab
                    mFabShare.setOnClickListener(NewsDetailsActivity.this);

                    // show main view
                    showMain();
                } else {
                    showError(R.string.unexpected_error);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // show error
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
        request.setTag(Constants.VOLLEY_REQ_NEWS_ITEM_DETAILS);
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.CON_TIMEOUT_NEWS_ITEM_DETAILS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_NEWS_ITEM_DETAILS);
        mNetworkController.addToRequestQueue(request);
    }

    /**
     * overridden method, used to handle click listeners
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_icon:
                onBackPressed();
                break;

            case R.id.fab_share:
                // show share sheet
                mBottomSheet.showWithSheetView(mIntentPickerSheet);
                break;
        }
    }

    /**
     * overridden method, used to refresh content
     */
    @Override
    protected void onRefresh() {
        loadDetails();
    }

    /*
     * overridden method
     */
    @Override
    public void onDestroy() {
        // cancel all appmsgs
        AppMsg.cancelAll(this);
        // cancel running requests
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_NEWS);

        super.onDestroy();
    }
}
