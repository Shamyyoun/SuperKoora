package net.smartinnovationtechnology.superkoora;

import android.app.Application;
import android.content.Context;

import database.UserDAO;
import datamodels.User;

/**
 * Created by Dahman on 9/20/2015.
 */
public class AppController extends Application {
    public static final String SUPER_KOORA_API_KEY = "ZywLPEKrRNc+Fs8RgYpA=G965AsMgtcLL";
    public static final String GOOGLE_API_KEY = "AIzaSyAJQJZRM0p3QhS9jPyOftyZIVjSUFOi1fE";
    private String endPoint;
    private User activeUser;
    private String language = "ar";

    @Override
    public void onCreate() {
        super.onCreate();
        endPoint = "http://api.superkoora.com/" + language + "/Mobile-App";
    }

    /**
     * method used to return current application instance
     */
    public static AppController getInstance(Context context) {
        return (AppController) context.getApplicationContext();
    }

    /**
     * method, used to get url end point
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * method, used to get active user from runtime or from cache
     */
    public User getActiveUser() {
        if (activeUser == null) {
            // get saved user if exists
            UserDAO userDAO = new UserDAO(getApplicationContext());
            userDAO.open();
            activeUser = userDAO.get();
            userDAO.close();
        }

        return activeUser;
    }

    /**
     * static method, used to return active user
     */
    public static User getActiveUser(Context context) {
        return getInstance(context).getActiveUser();
    }

    /**
     * method, used to set active user
     */
    public void setActiveUser(User user) {
        this.activeUser = user;
    }

    /**
     * static method, used to set active user
     */
    public static void setActiveUser(Context context, User user) {
        getInstance(context).setActiveUser(user);
    }

    /**
     * method, used to return current language
     */
    public String getLanguage() {
        return language;
    }
}
