package utils;

import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import net.smartinnovationtechnology.superkoora.R;

import java.util.Random;

/**
 * Created by Dahman on 9/16/2015.
 */
public class AnimationUtil {
    /**
     * method used to make beautiful scale & translate animation to image view
     */
    public static void animateBackground(ImageView imageBackground) {
        boolean isTablet = imageBackground.getResources().getBoolean(R.bool.isTablet);
        Random random = new Random();
        imageBackground.animate()
                .setStartDelay(200)
                .scaleX(1.1F).scaleY(1.1F)
                .translationXBy(getNextTranslation(random, isTablet)).translationYBy(getNextTranslation(random, isTablet))
                .setInterpolator(new DecelerateInterpolator()).setDuration(5000)
                .start();
    }

    /**
     * method, used to return new translation value for animateBackground method
     */
    private static float getNextTranslation(Random random, boolean isTablet) {
        boolean bool = random.nextBoolean();
        for (float f1 = 0.0F; ; f1 = -50.0F) {
            float f2 = 0.0F;
            if (bool) {
                f2 = 50.0F;
            }
            float f3 = f1 + random.nextFloat() * (f2 - f1);
            if (isTablet) {
                f3 *= 1.5F;
            }
            return f3;
        }
    }
}
