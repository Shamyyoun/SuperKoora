package net.smartinnovationtechnology.superkoora;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.FavoriteTeamsRecyclerAdapter;
import adapters.TeamsAutoComAdapter;
import database.UserDAO;
import datamodels.Constants;
import datamodels.Team;
import datamodels.User;
import json.TeamsHandler;
import utils.InternetUtil;

/**
 * Created by Dahman on 9/15/2015.
 */
public class FavoritesFragment extends Fragment implements View.OnClickListener {
    private MultiFragmentActivity mActivity;
    private AutoCompleteTextView mTextSearch;
    private ImageButton mButtonAdd;
    private Button mButtonSelect;
    private RecyclerView mRecyclerFavorites;
    private FavoriteTeamsRecyclerAdapter mFavoriteAdapter;
    private List<Team> mFavorites;

    private NetworkController mNetworkController;
    private Team selectedTeam; // used to hold selected team from auto complete text view

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (MultiFragmentActivity) getActivity();
        mTextSearch = (AutoCompleteTextView) rootView.findViewById(R.id.text_search);
        mButtonAdd = (ImageButton) rootView.findViewById(R.id.button_add);
        mButtonSelect = (Button) rootView.findViewById(R.id.button_select);
        mRecyclerFavorites = (RecyclerView) rootView.findViewById(R.id.recycler_favorites);
        mNetworkController = NetworkController.getInstance(mActivity);

        // customize hints
        String color = String.format("#%06X", 0xFFFFFF & getResources().getColor(R.color.gray_hint));
        mTextSearch.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.enter_favorites) + "</font>"));

        // customize auto complete section
        mTextSearch.setImeActionLabel(getString(R.string.add), KeyEvent.KEYCODE_ENTER);
        mTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // check key code
                if (actionId == KeyEvent.KEYCODE_ENTER) {
                    addFavoriteTeam();
                }
                return true;
            }
        });
        mTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // check key code
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mActivity.onBackPressed();
                } else {
                    searchTeams(mTextSearch.getText().toString());
                }
                return true;
            }
        });
        mTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // null selected team
                selectedTeam = null;
            }
        });
        mTextSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTeam = (Team) mTextSearch.getAdapter().getItem(position);
            }
        });

        // customize recycler view
        mRecyclerFavorites.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRecyclerFavorites.setItemAnimator(new DefaultItemAnimator());
        mFavorites = new ArrayList<>();
        mFavoriteAdapter = new FavoriteTeamsRecyclerAdapter(mActivity, mFavorites, R.layout.recycler_favorites_item);
        mRecyclerFavorites.setAdapter(mFavoriteAdapter);

        // check parent activity type
        if (mActivity instanceof GetStartedActivity) {
            // add click listener to handle back press
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        // finish activity
                        mActivity.finish();
                        return true;
                    }
                    return true;
                }
            });
        }

        // add click listeners
        mButtonAdd.setOnClickListener(this);
        mButtonSelect.setOnClickListener(this);
    }

    /**
     * overridden method, used to handle click listeners
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                addFavoriteTeam();
                break;

            case R.id.button_select:
                updateFavorites();
                break;
        }
    }

    /**
     * method, used to add team to favorites recycler if possible
     */
    private void addFavoriteTeam() {
        // check selected item
        if (selectedTeam == null) {
            // show error msg
            mTextSearch.setError(getString(R.string.choose_team_from_list));
        } else {
            // add it to recycler view
            mFavorites.add(0, selectedTeam);
            mFavoriteAdapter.notifyItemInserted(0);

            // scroll recycler view to top
            mRecyclerFavorites.scrollToPosition(0);

            // update search text
            mTextSearch.setText("");
            mTextSearch.setError(null);

            // hide keyboard
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mTextSearch.getWindowToken(), 0);
        }
    }

    /**
     * method, used to get teams from server and handle it auto complete text view
     */
    private void searchTeams(String keyword) {
        // cancel all search requests first
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_SEARCH_TEAMS);

        // keyword encode
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        // validate it
        if ((keyword.trim()).isEmpty()) {
            return;
        }

        // check internet connection
        if (!InternetUtil.isConnected(mActivity)) {
            return;
        }

        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/Search-In-Teams/" + keyword;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // handle teams
                TeamsHandler handler = new TeamsHandler(jsonArray);
                List<Team> teams = handler.handle();

                // check teams
                if (teams != null) {
                    // set teams adapter
                    TeamsAutoComAdapter adapter = new TeamsAutoComAdapter(mActivity, R.layout.autocom_teams_item, teams);
                    mTextSearch.setAdapter(adapter);
                    mTextSearch.showDropDown();
                }
            }
        }, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // add headers to request
                Map<String, String> headers = new HashMap();
                headers.put("Superkoora-Api-Key", AppController.SUPER_KOORA_API_KEY);
                return headers;
            }
        };

        // add request to request queue
        request.setTag(Constants.VOLLEY_REQ_SEARCH_TEAMS);
        request.setRetryPolicy(new DefaultRetryPolicy(Constants.CON_TIMEOUT_SEARCH_TEAMS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mNetworkController.addToRequestQueue(request);
    }

    /**
     * method, used to send request to server to update favorite teams
     */
    private void updateFavorites() {
        // check favorites length
        if (mFavorites.size() == 0) {
            // show error toast
            Toast.makeText(mActivity, R.string.choose_your_favorite_teams, Toast.LENGTH_SHORT).show();
            return;
        }

        // hide keyboard
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTextSearch.getWindowToken(), 0);

        // check internet connection
        if (!InternetUtil.isConnected(mActivity)) {
            Toast.makeText(mActivity, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/Register-Favourite-teams";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // get code
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    // check it
                    if (code == 200) {
                        // update user in runtime
                        AppController.getActiveUser(mActivity).setFavTeams(mFavorites);

                        // update it in db
                        UserDAO userDAO = new UserDAO(mActivity);
                        userDAO.open();
                        userDAO.update(AppController.getInstance(mActivity).getActiveUser());
                        userDAO.close();

                        // goto main activity
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        startActivity(intent);
                        mActivity.finish();
                    }
                } catch (Exception e) {
                    // show error toast
                    Toast.makeText(mActivity, R.string.unexpected_error, Toast.LENGTH_LONG).show();
                    // hide progress
                    mActivity.showProgress(false);
                    e.printStackTrace();
                }

                // hide progress
                mActivity.showProgress(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // show error toast
                Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_LONG).show();
                // hide progress
                mActivity.showProgress(false);
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // get fav teams ids
                String favTeamsIds = "";
                for (int i = 0; i < mFavorites.size(); i++) {
                    if (i != 0)
                        favTeamsIds += ",";
                    favTeamsIds += mFavorites.get(i).getId();
                }

                // add parameters to request
                Map<String, String> params = new HashMap();
                params.put("user_id", "" + AppController.getActiveUser(mActivity).getId());
                params.put("fav_teams", favTeamsIds);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // add headers to request
                Map<String, String> headers = new HashMap();
                headers.put("Superkoora-Api-Key", AppController.SUPER_KOORA_API_KEY);
                return headers;
            }
        };

        // add request to request queue
        request.setTag(Constants.VOLLEY_REQ_UPDATE_FAVORITES);
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_UPDATE_FAVORITES);
        mNetworkController.addToRequestQueue(request);
        // show progress
        mActivity.showProgress(true);
    }
}
