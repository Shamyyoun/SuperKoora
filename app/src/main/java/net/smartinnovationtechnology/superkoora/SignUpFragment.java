package net.smartinnovationtechnology.superkoora;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import datamodels.Constants;
import utils.InternetUtil;

/**
 * Created by Dahman on 9/15/2015.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {
    private SignUpActivity mActivity;
    private EditText mTextName;
    private EditText mTextEmail;
    private EditText mTextUsername;
    private EditText mTextPassword;
    private EditText mTextRePassword;
    private TextView mTextLogin;
    private View mViewSignUp;
    private NetworkController mNetworkController;
    private SweetAlertDialog mMsgDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (SignUpActivity) getActivity();
        mTextName = (EditText) rootView.findViewById(R.id.text_name);
        mTextEmail = (EditText) rootView.findViewById(R.id.text_email);
        mTextUsername = (EditText) rootView.findViewById(R.id.text_username);
        mTextPassword = (EditText) rootView.findViewById(R.id.text_password);
        mTextRePassword = (EditText) rootView.findViewById(R.id.text_rePassword);
        mTextLogin = (TextView) rootView.findViewById(R.id.text_login);
        mViewSignUp = rootView.findViewById(R.id.view_signUp);
        mNetworkController = NetworkController.getInstance(mActivity);

        // customize hints
        String color = String.format("#%06X", 0xFFFFFF & getResources().getColor(R.color.gray_hint));
        mTextName.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.your_name) + "</font>"));
        mTextEmail.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.email_address) + "</font>"));
        mTextUsername.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.username) + "</font>"));
        mTextPassword.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.password) + "</font>"));
        mTextRePassword.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.re_password) + "</font>"));

        // add listeners
        mTextLogin.setOnClickListener(this);
        mViewSignUp.setOnClickListener(this);
        mTextRePassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    signUp();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * method, used to handle click actions
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_login:
                gotoLogin(true);
                break;

            case R.id.view_signUp:
                signUp();
                break;
        }
    }

    /**
     * method, used to goto login fragment
     */
    private void gotoLogin(boolean addToBackStack) {
        // change activity bg
        mActivity.changeBackground(2);

        // open login fragment
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up_in, R.anim.fragment_slide_up_out, R.anim.fragment_slide_down_in, R.anim.fragment_slide_down_out);
        ft.replace(R.id.container, new LoginFragment());

        // check addToBackStack
        if (addToBackStack) {
            // add it
            ft.addToBackStack(null);
        } else {
            // finish get started activity
            GetStartedActivity.getCurrentInstance().finish();
        }

        ft.commit();
    }

    /**
     * method, used to validate, send sign up req and handle it
     */
    private void signUp() {
        final String name = mTextName.getText().toString().trim();
        final String email = mTextEmail.getText().toString().trim();
        final String username = mTextUsername.getText().toString().trim();
        final String password = mTextPassword.getText().toString().trim();
        final String rePassword = mTextRePassword.getText().toString().trim();

        // validate inputs
        if (name.isEmpty()) {
            mTextName.setText("");
            mTextName.setError(getString(R.string.required));
            return;
        }
        if (email.isEmpty()) {
            mTextEmail.setText("");
            mTextEmail.setError(getString(R.string.required));
            return;
        }
        String pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        if (!m.find()) {
            mTextEmail.setError(getString(R.string.enter_valid_email));
            return;
        }
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
        if (!password.equals(rePassword)) {
            mTextRePassword.selectAll();
            mTextRePassword.setError(getString(R.string.passwords_does_not_match));
            return;
        }

        // hide keyboard
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTextEmail.getWindowToken(), 0);

        // check internet connection
        if (!InternetUtil.isConnected(mActivity)) {
            Toast.makeText(mActivity, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        // create new msg dialog
        mMsgDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
        mMsgDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.primary));
        mMsgDialog.setCancelable(false);
        mMsgDialog.setTitleText(getString(R.string.please_wait));

        // make & send request
        String url = AppController.getInstance(mActivity).getEndPoint() + "/Registeration";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // get code
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");

                    // check code
                    if (code == 200) {
                        // --registered successfully--
                        // dismiss dialog
                        mMsgDialog.dismissWithAnimation();
                        // show toast mag
                        Toast.makeText(mActivity, R.string.registered_successfully, Toast.LENGTH_LONG).show();
                        // goto login fragment
                        gotoLogin(false);
                    } else {
                        // unexpected error
                        showError(R.string.unexpected_error);
                    }
                } catch (Exception e) {
                    showError(R.string.unexpected_error);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    // get status code and string data
                    int statusCode = volleyError.networkResponse.statusCode;
                    String data = new String(volleyError.networkResponse.data);

                    // check status code
                    if (statusCode == 406) {
                        // username not available
                        showError(R.string.username_isnt_available);
                    } else if (statusCode == 400) {
                        // --invalid parameters--
                        // check invalid params
                        JSONObject jsonObject = new JSONObject(data);
                        JSONObject messageObject = jsonObject.getJSONObject("message");
                        if (messageObject.has("email")) {
                            // invalid email
                            showError(R.string.invalid_email_address);
                        } else {
                            // invalid parameter
                            showError(R.string.invalid_parameter);
                        }
                    } else {
                        // show error toast
                        showError(R.string.connection_error);
                    }
                } catch (Exception e) {
                    showError(R.string.connection_error);
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // add parameters to request
                Map<String, String> params = new HashMap();
                params.put("name", name);
                params.put("email", email);
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
        request.setTag(Constants.VOLLEY_REQ_SIGNUP);
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_SIGNUP);
        mNetworkController.addToRequestQueue(request);
        // show progress
        mMsgDialog.show();
    }

    /**
     * method, used to show error in msg dialog
     */
    private void showError(int errorMsgId) {
        mMsgDialog.setTitleText(getString(errorMsgId))
                .setConfirmText(getString(R.string.close))
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        mMsgDialog.setCancelable(true);
    }

    /**
     * overridden method
     */
    @Override
    public void onDestroy() {
        // cancel all sign up requests
        mNetworkController.getRequestQueue().cancelAll(Constants.VOLLEY_REQ_SIGNUP);
        super.onDestroy();
    }
}
