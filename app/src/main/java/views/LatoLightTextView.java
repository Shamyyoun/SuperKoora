package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dahman on 9/16/2015.
 */
public class LatoLightTextView extends TextView {

    public LatoLightTextView(Context context) {
        super(context);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_l.ttf"));
    }

    public LatoLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_l.ttf"));
    }

    public LatoLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_l.ttf"));
    }
}
