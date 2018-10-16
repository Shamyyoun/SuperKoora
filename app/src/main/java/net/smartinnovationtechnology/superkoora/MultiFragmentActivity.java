package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.FacebookSdk;

import utils.ViewUtil;

public abstract class MultiFragmentActivity extends AppCompatActivity {
    /**
     * abstract method, to enforce dev to return background image
     */
    public abstract ImageView getBackgroundImage();

    /**
     * abstract method, used to show or hide progress view
     */
    public abstract void showProgress(boolean show);

    /**
     * method, used to change background with smooth alpha animation
     */
    public void changeBackground(int index) {
        int animDuration = 1000;
        switch (index) {
            case 2:
                ViewUtil.fadeView(getBackgroundImage(), false, animDuration);
                break;

            default:
                ViewUtil.fadeView(getBackgroundImage(), true, animDuration);
                break;
        }
    }
}
