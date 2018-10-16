package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import datamodels.Constants;
import utils.ViewUtil;

public class SignUpActivity extends MultiFragmentActivity {
    private ImageView mImageBackground;
    private View mViewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        mImageBackground = (ImageView) findViewById(R.id.image_background);
        mViewProgress = findViewById(R.id.view_progress);

        // check passed parameter
        Fragment fragment;
        if (getIntent().getBooleanExtra(Constants.KEY_LOAD_FAVORITES_FRAGMENT, false)) {
            fragment = new FavoritesFragment();
        } else {
            fragment = new SignUpFragment();
        }

        // load the selected fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    /**
     * overridden method to return background image
     */
    @Override
    public ImageView getBackgroundImage() {
        return mImageBackground;
    }

    /**
     * method, used to show / hide progress view
     */
    @Override
    public void showProgress(boolean show) {
        ViewUtil.fadeView(mViewProgress, show);
        // enable / disable screen
        if (show) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    /**
     * overridden method
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_anim, R.anim.activity_scale_fade_exit);
    }
}
