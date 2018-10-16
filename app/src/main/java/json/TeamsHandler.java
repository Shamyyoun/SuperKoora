package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import datamodels.Team;

public class TeamsHandler {
    private JSONArray jsonArray;

    public TeamsHandler(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public List<Team> handle() {
        List<Team> teams = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                // parse team
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("Team_Id");
                String nameAr = jsonObject.getString("Team_Ar_Name");
                String nameEn = jsonObject.getString("Team_En_Name");
                String logo = jsonObject.getString("Team_Logo");
                String color = jsonObject.getString("Team_Color");

                Team team = new Team(id, nameAr, nameEn, logo, color);
                teams.add(team);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            teams = null;
        }

        return teams;
    }
}
