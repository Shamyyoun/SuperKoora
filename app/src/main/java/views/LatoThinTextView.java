package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dahman on 9/16/2015.
 */
public class LatoThinTextView extends TextView {

    public LatoThinTextView(Context context) {
        super(context);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_th.ttf"));
    }

    public LatoThinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_th.ttf"));
    }

    public LatoThinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "lato_th.ttf"));
    }
}
