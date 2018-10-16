package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Dahman on 9/16/2015.
 */
public class LatoSemiBoldButton extends Button {

    public LatoSemiBoldButton(Context context) {
        super(context);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_sb.ttf"));
    }

    public LatoSemiBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_sb.ttf"));
    }

    public LatoSemiBoldButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_sb.ttf"));
    }
}
