package net.smartinnovationtechnology.superkoora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Dahman on 9/15/2015.
 */
public class GetStartedFragment extends Fragment implements View.OnClickListener {
    private GetStartedActivity mActivity;
    private Button mButtonGetStarted;
    private TextView mTextLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_started, container, false);
        initComponents(rootView);

        return rootView;
    }

    /**
     * method, used to initialize components
     */
    private void initComponents(View rootView) {
        mActivity = (GetStartedActivity) getActivity();
        mButtonGetStarted = (Button) rootView.findViewById(R.id.button_getStarted);
        mTextLogin = (TextView) rootView.findViewById(R.id.text_login);

        // add click listeners
        mButtonGetStarted.setOnClickListener(this);
        mTextLogin.setOnClickListener(this);
    }

    /**
     * method, used to handle click actions
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_getStarted:
                // open slider activity
                Intent intent = new Intent(mActivity, SliderActivity.class);
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.activity_scale_fade_enter, R.anim.no_anim);
                break;

            case R.id.text_login:
                // change activity bg
                mActivity.changeBackground(2);

                // open login fragment
                FragmentManager fm = mActivity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_slide_up_in, R.anim.fragment_slide_up_out, R.anim.fragment_slide_down_in, R.anim.fragment_slide_down_out);
                ft.replace(R.id.container, new LoginFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }
}
