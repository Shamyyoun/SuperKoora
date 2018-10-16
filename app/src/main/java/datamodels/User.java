package datamodels;

import java.util.List;

/**
 * Created by Shamyyoun on 11/1/2015.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private List<Team> favTeams;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFavTeams(List<Team> favTeams) {
        this.favTeams = favTeams;
    }

    public List<Team> getFavTeams() {
        return favTeams;
    }
}
