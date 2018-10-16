package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Dahman on 9/16/2015.
 */
public class OpenSansLighEditText extends EditText {

    public OpenSansLighEditText(Context context) {
        super(context);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "open_sans_l.ttf"));
    }

    public OpenSansLighEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "open_sans_l.ttf"));
    }

    public OpenSansLighEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // set typeface
        setTypeface(Typeface.createFromAsset(context.getAssets(), "open_sans_l.ttf"));
    }
}
