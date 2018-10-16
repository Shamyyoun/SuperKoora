package net.smartinnovationtechnology.superkoora;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.facebook.appevents.AppEventsLogger;

import datamodels.Constants;
import datamodels.User;
import utils.AnimationUtil;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2500;
    private static final int BALL_ANIM_DURATION = 1500;

    private ImageView mImageBackground;
    private ImageView mImageBall;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // define objects
        mImageBackground = (ImageView) findViewById(R.id.image_background);
        mImageBall = (ImageView) findViewById(R.id.image_ball);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // start get started activity
                Intent intent = new Intent(SplashActivity.this, GetStartedActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };

        // check cached user
        User activeUser = AppController.getInstance(this).getActiveUser();
        if (activeUser == null) {
            // no cached user
            // start splash
            mHandler.postDelayed(mRunnable, SPLASH_DURATION);
        } else {
            // there is a cached user
            // check its fav teams count
            if (activeUser.getFavTeams().size() == 0) {
                // open sign up activity with favorites fragment
                Intent intent = new Intent(this, SignUpActivity.class);
                intent.putExtra(Constants.KEY_LOAD_FAVORITES_FRAGMENT, true);
                startActivity(intent);
            } else {
                // open main activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * overridden method
     */
    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        // animate background
        AnimationUtil.animateBackground(mImageBackground);
    }

    /**
     * overridden method
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            // create and start ball animation
            float fromY = 0 - mImageBall.getHeight();
            float toY = mImageBall.getY();
            ObjectAnimator animator = ObjectAnimator.ofFloat(mImageBall, "y", fromY, toY);
            animator.setDuration(BALL_ANIM_DURATION);
            animator.setInterpolator(new BounceInterpolator());
            animator.start();
        }
    }

    /**
     * overridden method
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    /**
     * overridden method
     */
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    /**
     * overridden method
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // cancel splash runnable
        mHandler.removeCallbacks(mRunnable);
    }
}
