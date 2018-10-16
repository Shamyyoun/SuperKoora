package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import datamodels.Team;

public class TeamDAO {
    private SQLiteDatabase database;
    private DatabaseSQLiteHelper dbHelper;

    public TeamDAO(Context context) {
        dbHelper = new DatabaseSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * method, used to add team to database
     */
    public void add(Team team, int userId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSQLiteHelper.TEAMS_ID, team.getId());
        values.put(DatabaseSQLiteHelper.TEAMS_NAME_AR, team.getNameAr());
        values.put(DatabaseSQLiteHelper.TEAMS_NAME_EN, team.getNameEn());
        values.put(DatabaseSQLiteHelper.TEAMS_LOGO, team.getLogo());
        values.put(DatabaseSQLiteHelper.TEAMS_COLOR, team.getColor());
        values.put(DatabaseSQLiteHelper.TEAMS_USER_ID, userId);

        database.insert(DatabaseSQLiteHelper.TABLE_TEAMS, null, values);
    }

    /**
     * method, used to add list of teams to database
     */
    public void add(List<Team> teams, int userId) {
        for (Team team : teams) {
            add(team, userId);
        }
    }

    /**
     * method, used to get fav teams of a user
     */
    public List<Team> get(int userId) {
        List<Team> teams = new ArrayList();
        Cursor cursor = database.query(DatabaseSQLiteHelper.TABLE_TEAMS, null,
                DatabaseSQLiteHelper.TEAMS_USER_ID + " = " + userId, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Team team = cursorToItem(cursor);
            teams.add(team);
            cursor.moveToNext();
        }
        cursor.close();
        return teams;
    }

    /**
     * method, used to get item values from cursor row
     */
    private Team cursorToItem(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseSQLiteHelper.TEAMS_ID));
        String nameAr = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.TEAMS_NAME_AR));
        String nameEn = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.TEAMS_NAME_EN));
        String logo = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.TEAMS_LOGO));
        String color = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.TEAMS_COLOR));

        Team team = new Team(id, nameAr, nameEn, logo, color);
        return team;
    }

    /**
     * method, used to delete sign log from database
     */
    public void deleteAll(int userId) {
        database.delete(DatabaseSQLiteHelper.TABLE_TEAMS, DatabaseSQLiteHelper.TEAMS_USER_ID + " = " + userId, null);
    }
}
