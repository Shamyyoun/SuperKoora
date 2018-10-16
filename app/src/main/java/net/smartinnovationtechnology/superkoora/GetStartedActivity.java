package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import utils.ViewUtil;

public class GetStartedActivity extends MultiFragmentActivity {
    private static GetStartedActivity currentInstance;
    private ImageView mImageBackground;
    private View mViewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        currentInstance = this;
        mImageBackground = (ImageView) findViewById(R.id.image_background);
        mViewProgress = findViewById(R.id.view_progress);

        // load main fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new GetStartedFragment());
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
     * static method, used to return current instance of this activity
     */
    public static GetStartedActivity getCurrentInstance() {
        return currentInstance;
    }
}
