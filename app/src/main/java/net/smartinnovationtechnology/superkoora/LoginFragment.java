package net.smartinnovationtechnology.superkoora;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.UserDAO;
import datamodels.Constants;
import datamodels.User;
import json.UserHandler;
import utils.InternetUtil;
import utils.ViewUtil;

/**
 * Created by Dahman on 9/15/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "Super Koora";
    private MultiFragmentActivity mActivity;
    private EditText mTextUsername;
    private EditText mTextPassword;
    private View mViewSuperKooraLogin;
    private View mViewFacebookLogin;
    private View mViewTwitterLogin;
    private View mViewGooglePlusLogin;
    private NetworkController mNetworkController;

    // social login objects
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsGooglePlusResolving = false;
    private boolean mShouldGooglePlusResolve = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (MultiFragmentActivity) getActivity();
        mTextUsername = (EditText) rootView.findViewById(R.id.text_username);
        mTextPassword = (EditText) rootView.findViewById(R.id.text_password);
        mViewSuperKooraLogin = rootView.findViewById(R.id.view_superKooraLogin);
        mViewFacebookLogin = rootView.findViewById(R.id.view_facebookLogin);
        mViewTwitterLogin = rootView.findViewById(R.id.view_twitterLogin);
        mViewGooglePlusLogin = rootView.findViewById(R.id.view_googlePlusLogin);
        mNetworkController = NetworkController.getInstance(mActivity);

        // customize facebook login objects
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // get email
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                        try {
                                            String id = jsonObject.getString("id");
                                            String name = jsonObject.getString("name");
                                            String email = jsonObject.getString("email");

                                            Log.i(TAG, "ID: " + id);
                                            Log.i(TAG, "name: " + name);
                                            Log.i(TAG, "email: " + email);

                                            Toast.makeText(mActivity, "Email: " + email, Toast.LENGTH_LONG).show();

//                                            // send social login to server
//                                            facebookLogin(id, name, email);
                                        } catch (Exception e) {
                                            // show error toast
                                            Toast.makeText(mActivity, R.string.unexpected_error_try_again, Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email");
                        request.setParameters(parameters);
                        request.executeAsync();

                        // TODO
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(mActivity, "FB Error", Toast.LENGTH_LONG).show();
                        exception.printStackTrace();
                    }
                });

        // customize google+ login objects
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .build();

        // customize hints
        String color = String.format("#%06X", 0xFFFFFF & getResources().getColor(R.color.gray_hint));
        mTextUsername.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.username_or_email) + "</font>"));
        mTextPassword.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.password) + "</font>"));

        // add click listener to handle back press
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // change activity bg
                    mActivity.changeBackground(1);

                    // check back stack
                    FragmentManager fm = mActivity.getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        // pop get started fragment
                        fm.popBackStack();
                    } else {
                        mActivity.onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

        // add listeners
        mViewSuperKooraLogin.setOnClickListener(this);
        mViewFacebookLogin.setOnClickListener(this);
        mViewTwitterLogin.setOnClickListener(this);
        mViewGooglePlusLogin.setOnClickListener(this);
        mTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * overridden method, used to handle click listeners
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_facebookLogin:
                // init facebook sdk
                FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
                // login with permissions
                List<String> permissions = new ArrayList<>();
                permissions.add("email");
                LoginManager.getInstance().logInWithReadPermissions(mActivity, permissions);
                break;

            case R.id.view_twitterLogin:
                break;

            case R.id.view_googlePlusLogin:
                // connect to google api client to login
                mShouldGooglePlusResolve = true;
                mGoogleApiClient.connect();
                mActivity.showProgress(true);
                break;

            case R.id.view_superKooraLogin:
                login();
                break;
        }
    }

    /**
     * method, used to validate, send login req and handle it
     */
    private void login() {
        final String username = mTextUsername.getText().toString().trim();
        final String password = mTextPassword.getText().toString().trim();

        // validate inputs
        if (username.isEmpty()) {
            mTextUsername.setText("");
            mTextUsername.setError(getString(R.string.required));
            return;
        }
        if (password.isEmpty()) {
            mTextPassword.setText("");
            mTextPassword.setError(getString(R.string.required));
            return;
        }
        if (password.length() < 8) {
            mTextPassword.selectAll();
            mTextPassword.setError(getString(R.string.password_at_least_8));
            return;
        }

        // hide keyboard
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTextPassword.getWindowToken(), 0);

        // check internet connection
        if (!InternetUtil.isConnected(mActivity)) {
            Toast.makeText(mActivity, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/doLogin";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // handle user
                    JSONObject jsonObject = new JSONObject(response);
                    handleUser(jsonObject, password);
                } catch (Exception e) {
                    // show error toast
                    Toast.makeText(mActivity, R.string.unexpected_error, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                // hide progress
                mActivity.showProgress(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    // get status code and string data
                    int statusCode = volleyError.networkResponse.statusCode;

                    // check status code
                    if (statusCode == 404) {
                        // username doesn't exist
                        // show error toast
                        Toast.makeText(mActivity, R.string.username_doesnt_exist, Toast.LENGTH_LONG).show();
                    } else if (statusCode == 401) {
                        // invalid password
                        // show error toast
                        Toast.makeText(mActivity, R.string.invalid_password, Toast.LENGTH_LONG).show();
                    } else {
                        // show error toast
                        Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // show error toast
                    Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                // hide progress
                mActivity.showProgress(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // add parameters to request
                Map<String, String> params = new HashMap();
                params.put("username", username);
                params.put("password", password);
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
        request.setTag(Constants.VOLLEY_REQ_LOGIN);
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_LOGIN);
        mNetworkController.addToRequestQueue(request);
        // show progress
        mActivity.showProgress(true);
    }

    /**
     * method, used to send social login request to server
     */
    private void facebookLogin(final String id, final String name, final String email) {
        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/Social-Login";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // handle user
                    JSONObject jsonObject = new JSONObject(response);
                    handleUser(jsonObject, null);
                } catch (Exception e) {
                    // show error toast
                    Toast.makeText(mActivity, R.string.unexpected_error, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                // hide progress
                mActivity.showProgress(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    // get status code and string data
                    int statusCode = volleyError.networkResponse.statusCode;

                    // check status code
                    if (statusCode == 400) {
                        // email is invalid
                        // show error toast
                        Toast.makeText(mActivity, R.string.invalid_email_address, Toast.LENGTH_LONG).show();
                    } else {
                        // show error toast
                        Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // show error toast
                    Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                // hide progress
                mActivity.showProgress(false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // add parameters to request
                Map<String, String> params = new HashMap();
                params.put("social_media_code", id);
                params.put("name", name);
                params.put("email", email);
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
        request.setTag(Constants.VOLLEY_REQ_SOCIAL_LOGIN);
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_SOCIAL_LOGIN);
        mNetworkController.addToRequestQueue(request);
        // show progress
        mActivity.showProgress(true);
    }

    /**
     * method, used to handle user & save it
     */
    private void handleUser(JSONObject jsonObject, String password) {
        // handle user
        UserHandler handler = new UserHandler(jsonObject);
        User user = handler.handle();
        user.setPassword(password);

        // save user
        UserDAO userDAO = new UserDAO(mActivity);
        userDAO.open();
        userDAO.add(user);
        userDAO.close();
        AppController.getInstance(mActivity).setActiveUser(user);

        // check fav teams count
        if (user.getFavTeams().size() == 0) {
            // no fav teams yet
            // open favorites fragment
            FragmentManager fm = mActivity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_slide_up_in, R.anim.fragment_slide_up_out, R.anim.fragment_slide_down_in, R.anim.fragment_slide_down_out);
            ft.replace(R.id.container, new FavoritesFragment());
            ft.commit();
        } else {
            // there one or more fav team
            // open main activity
            Intent intent = new Intent(mActivity, MainActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
    }

    /**
     * overridden method, used to handle google plus connection success case
     */
    @Override
    public void onConnected(Bundle bundle) {
        mShouldGooglePlusResolve = false;
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String id = currentPerson.getId();
            String name = currentPerson.getDisplayName();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            Log.i(TAG, "ID: " + id);
            Log.i(TAG, "Name: " + name);
            Log.i(TAG, "Email: " + email);
        }

        mActivity.showProgress(false);
        Log.i(TAG, "CONNECTED");
    }

    /**
     * overridden method, used to handle google plus connection suspending case
     */
    @Override
    public void onConnectionSuspended(int i) {
        mActivity.showProgress(false);
        Log.i(TAG, "SUSPENDED");
    }

    /**
     * overridden method, used to handle google plus connection fail case
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIsGooglePlusResolving && mShouldGooglePlusResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(mActivity, Constants.REQ_GOOGLE_PLUS_SIGNIN);
                    mIsGooglePlusResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    mIsGooglePlusResolving = false;
                    mGoogleApiClient.connect();
                    e.printStackTrace();
                }
            } else {
                // could not resolve the connection result, show the user an error toast
                Toast.makeText(mActivity, R.string.unexpected_error_try_again, Toast.LENGTH_LONG).show();
            }
        }

        mActivity.showProgress(false);
        Log.i(TAG, "FAILED");
    }

    /**
     * overridden method
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQ_GOOGLE_PLUS_SIGNIN) {
            if (resultCode != Activity.RESULT_OK) {
                mShouldGooglePlusResolve = false;
            }

            mIsGooglePlusResolving = false;
            mGoogleApiClient.connect();
        }
    }

    /**
     * overridden method
     */
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    /**
     * overridden method
     */
    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    /**
     * overridden method
     */
    @Override
    public void onDestroy() {
        // cancel all login requests
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_LOGIN);
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_SOCIAL_LOGIN);
        super.onDestroy();
    }
}
