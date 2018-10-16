package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLiteHelper extends SQLiteOpenHelper {
    private Context context;

    // database info
    private static final String DATABASE_NAME = "super_koora.db";
    private static final int DATABASE_VERSION = 1;

    // table users
    public static final String TABLE_USERS = "users";
    public static final String USERS_ID = "_id";
    public static final String USERS_NAME = "name";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String TABLE_TEAMS = "fav_teams";
    public static final String TEAMS_ID = "_id";
    public static final String TEAMS_NAME_AR = "name_ar";
    public static final String TEAMS_NAME_EN = "name_en";
    public static final String TEAMS_LOGO = "logo";
    public static final String TEAMS_COLOR = "color";
    public static final String TEAMS_USER_ID = "user_id";

    // tables creation
    private static final String USERS_CREATE = "CREATE TABLE " + TABLE_USERS
            + "("
            + USERS_ID + " INTEGER PRIMARY KEY, "
            + USERS_NAME + " TEXT, "
            + USERS_EMAIL + " TEXT, "
            + USERS_USERNAME + " TEXT, "
            + USERS_PASSWORD + " TEXT"
            + ");";
    private static final String TEAMS_CREATE = "CREATE TABLE " + TABLE_TEAMS
            + "("
            + TEAMS_ID + " INTEGER PRIMARY KEY, "
            + TEAMS_NAME_AR + " TEXT, "
            + TEAMS_NAME_EN + " TEXT, "
            + TEAMS_LOGO + " TEXT, "
            + TEAMS_COLOR + " TEXT, "
            + TEAMS_USER_ID + " INTEGER"
            + ");";

    public DatabaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create tables
        database.execSQL(USERS_CREATE);
        database.execSQL(TEAMS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

}