package datamodels;

/**
 * Created by Shamyyoun on 10/29/2015.
 */
public class Scorer {
    private String title;
    private int goals;
    private Team1 team;
    private int position;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public Team1 getTeam() {
        return team;
    }

    public void setTeam(Team1 team) {
        this.team = team;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
