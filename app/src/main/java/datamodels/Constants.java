package datamodels;

/**
 * Created by Dahman on 9/16/2015.
 */
public class Constants {
    // keys
    public static final String KEY_INDEX = "index";
    public static final String KEY_MATCH = "match";
    public static final String KEY_NEWS_ITEM = "news_item";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_CONTINENT = "continent";
    public static final String KEY_COMPETITION = "competition";
    public static final String KEY_LOAD_FAVORITES_FRAGMENT = "load_favorites_fragment";
    public static final String KEY_VIDEO_ID = "video_id";

    // menu drawer static items IDs
    public static final int MENU_ITEM_HOME = -1;
    public static final int MENU_ITEM_NEWS = -2;
    public static final int MENU_ITEM_VIDEOS = -3;
    public static final int MENU_ITEM_H2H = -4;
    public static final int MENU_ITEM_TRANSFERS = -5;
    public static final int MENU_ITEM_LIVE_SCORES = -6;
    public static final int MENU_ITEM_COMPETITIONS = -7;

    // continents IDs
    public static final int CONTINENT_FIFA = 1;
    public static final int CONTINENT_CAF = 2;
    public static final int CONTINENT_AFC = 3;
    public static final int CONTINENT_CONCACAF = 4;
    public static final int CONTINENT_CONEMBOL = 5;
    public static final int CONTINENT_OFC = 6;
    public static final int CONTINENT_UEFA = 7;

    // activity requests
    public static final int REQ_GOOGLE_PLUS_SIGNIN = 1;

    // volley requests
    public static final String VOLLEY_REQ_SIGNUP = "signup";
    public static final String VOLLEY_REQ_LOGIN = "login";
    public static final String VOLLEY_REQ_SOCIAL_LOGIN = "social_login";
    public static final String VOLLEY_REQ_SEARCH_TEAMS = "search_teams";
    public static final String VOLLEY_REQ_UPDATE_FAVORITES = "update_favorites";
    public static final String VOLLEY_REQ_NEWS = "news";
    public static final String VOLLEY_REQ_NEWS_ITEM_DETAILS = "news_item_details";
    public static final String VOLLEY_REQ_VIDEOS = "videos";

    // locals
    public static final String LANG_AR = "ar";
    public static final String LANG_EN = "en";

    // volley requests timeout
    public static final int CON_TIMEOUT_SEARCH_TEAMS = 30 * 1000;
    public static final int CON_TIMEOUT_NEWS = 60 * 1000;
    public static final int CON_TIMEOUT_VIDEOS = 60 * 1000;
    public static final int CON_TIMEOUT_NEWS_ITEM_DETAILS = 30 * 1000;

    // server data limits
    public static final int SERVER_LIMIT_NEWS = 10;
    public static final int SERVER_LIMIT_VIDEOS = 10;

    // SP constants
    public static final String SP_RESPONSES = "responses";
    public static final String SP_KEY_NEWS = "news";
    public static final String SP_KEY_VIDEOS = "videos";
}
