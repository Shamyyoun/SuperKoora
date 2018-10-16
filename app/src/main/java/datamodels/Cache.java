package datamodels;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shamyyoun on 3/29/2015.
 */
public class Cache {
    /**
     * method used to update news response in SP
     */
    public static void updateNewsResponse(Context context, String value) {
        updateCachedString(context, Constants.SP_RESPONSES, Constants.SP_KEY_NEWS, value);
    }

    /**
     * method used to get news response from SP
     */
    public static String getNewsResponse(Context context) {
        return getCachedString(context, Constants.SP_RESPONSES, Constants.SP_KEY_NEWS);
    }

    /**
     * method used to update videos response in SP
     */
    public static void updateVideosResponse(Context context, String value) {
        updateCachedString(context, Constants.SP_RESPONSES, Constants.SP_KEY_VIDEOS, value);
    }

    /**
     * method used to get videos response from SP
     */
    public static String getVideosResponse(Context context) {
        return getCachedString(context, Constants.SP_RESPONSES, Constants.SP_KEY_VIDEOS);
    }


    /*
     * method, used to update string value in SP
     */
    private static void updateCachedString(Context context, String spName, String valueName, String value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(valueName, value);
        editor.commit();
    }

    /*
     * method, used to get cached String from SP
     */
    private static String getCachedString(Context context, String spName, String valueName) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String value = sp.getString(valueName, null);

        return value;
    }
}
