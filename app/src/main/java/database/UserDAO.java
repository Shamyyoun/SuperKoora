package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import datamodels.Team;
import datamodels.User;

public class UserDAO {
    private Context context;
    private SQLiteDatabase database;
    private DatabaseSQLiteHelper dbHelper;

    public UserDAO(Context context) {
        this.context = context;
        dbHelper = new DatabaseSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * method, used to add user to database
     */
    public void add(User user) {
        // add row to users table
        ContentValues values = new ContentValues();
        values.put(DatabaseSQLiteHelper.USERS_ID, user.getId());
        values.put(DatabaseSQLiteHelper.USERS_NAME, user.getName());
        values.put(DatabaseSQLiteHelper.USERS_EMAIL, user.getEmail());
        values.put(DatabaseSQLiteHelper.USERS_USERNAME, user.getUsername());
        values.put(DatabaseSQLiteHelper.USERS_PASSWORD, user.getPassword());
        database.insert(DatabaseSQLiteHelper.TABLE_USERS, null, values);

        // add favorite teams to teams table
        TeamDAO teamDAO = new TeamDAO(context);
        teamDAO.open();
        teamDAO.add(user.getFavTeams(), user.getId());
        teamDAO.close();
    }

    /**
     * method, used to update user in database
     */
    public void update(User user) {
        // update users table
        ContentValues values = new ContentValues();
        values.put(DatabaseSQLiteHelper.USERS_NAME, user.getName());
        values.put(DatabaseSQLiteHelper.USERS_EMAIL, user.getEmail());
        values.put(DatabaseSQLiteHelper.USERS_USERNAME, user.getUsername());
        values.put(DatabaseSQLiteHelper.USERS_PASSWORD, user.getPassword());
        database.update(DatabaseSQLiteHelper.TABLE_USERS, values, DatabaseSQLiteHelper.USERS_ID + " = " + user.getId(), null);

        // update teams table
        TeamDAO teamDAO = new TeamDAO(context);
        teamDAO.open();
        teamDAO.deleteAll(user.getId());
        teamDAO.add(user.getFavTeams(), user.getId());
        teamDAO.close();
    }

    /**
     * method, used to delete all users from database
     */
    public void deleteAll() {
        database.delete(DatabaseSQLiteHelper.TABLE_USERS, null, null);
    }

    /**
     * method, used to get user from db
     */
    public User get() {
        User user = null;
        Cursor cursor = database.query(DatabaseSQLiteHelper.TABLE_USERS, null, null, null, null, null, null);

        // get user values
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseSQLiteHelper.USERS_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.USERS_NAME));
            String email = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.USERS_EMAIL));
            String username = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.USERS_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.USERS_PASSWORD));

            user = new User(id, name, email);
            user.setUsername(username);
            user.setPassword(password);
            cursor.moveToNext();
        }
        cursor.close();

        // check if found user
        if (user != null) {
            // get fav teams from db
            TeamDAO teamDAO = new TeamDAO(context);
            teamDAO.open();
            List<Team> favTeams = teamDAO.get(user.getId());
            teamDAO.close();

            user.setFavTeams(favTeams);
        }

        return user;
    }
}