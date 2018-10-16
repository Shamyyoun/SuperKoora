package datamodels;

import java.io.Serializable;

/**
 * Created by Dahman on 9/21/2015.
 */
public class Match implements Serializable {
    private Team1 team1;
    private Team1 team2;
    private int leagueLogo;
    private String date;

    public Match(Team1 team1, Team1 team2, int leagueLogo, String date) {
        this.team1 = team1;
        this.team2 = team2;
        this.leagueLogo = leagueLogo;
        this.date = date;
    }

    public Match() {
    }

    public Team1 getTeam1() {
        return team1;
    }

    public void setTeam1(Team1 team1) {
        this.team1 = team1;
    }

    public Team1 getTeam2() {
        return team2;
    }

    public void setTeam2(Team1 team2) {
        this.team2 = team2;
    }

    public int getLeagueLogo() {
        return leagueLogo;
    }

    public void setLeagueLogo(int leagueLogo) {
        this.leagueLogo = leagueLogo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
