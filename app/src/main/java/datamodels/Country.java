package datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shamyyoun on 10/1/2015.
 */
public class Country {
    private int id;
    private String title;
    private List<Team1> teams;

    public Country(int id, String title, List<Team1> teams) {
        this.id = id;
        this.title = title;
        this.teams = teams;
    }

    public Country(int id, String title, Team1 team) {
        this.id = id;
        this.title = title;
        List<Team1> teams = new ArrayList<>();
        teams.add(team);
        this.teams = teams;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Team1> getTeams() {
        return teams;
    }

    @Override
    public String toString() {
        return title;
    }
}
