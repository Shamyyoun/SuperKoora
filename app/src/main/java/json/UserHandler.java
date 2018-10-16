package json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import datamodels.Team;
import datamodels.User;

public class UserHandler {
    private JSONObject jsonObject;

    public UserHandler(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public User handle() {
        User user;
        try {
            int id = jsonObject.getInt("User_Id");
            String name = jsonObject.getString("Name");
            String email = jsonObject.getString("Email");

            // get username
            String username = null;
            if (jsonObject.has("Username"))
                username = jsonObject.getString("Username");

            // get fav teams
            JSONArray favTeamsArray = jsonObject.getJSONArray("Favourite_Teams");
            TeamsHandler teamsHandler = new TeamsHandler(favTeamsArray);
            List<Team> favTeams = teamsHandler.handle();

            // create user object
            user = new User(id, name, email);
            user.setUsername(username);
            user.setFavTeams(favTeams);
        } catch (Exception e) {
            user = null;
            e.printStackTrace();
        }

        return user;
    }
}
