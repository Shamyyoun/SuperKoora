package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by Dahman on 9/16/2015.
 */
public class OpenSansLighAutoCompleteTextView extends AutoCompleteTextView {

    public OpenSansLighAutoCompleteTextView(Context context) {
        super(context);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "open_sans_l.ttf"));
    }

    public OpenSansLighAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "open_sans_l.ttf"));
    }

    public OpenSansLighAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "open_sans_l.ttf"));
    }
}
