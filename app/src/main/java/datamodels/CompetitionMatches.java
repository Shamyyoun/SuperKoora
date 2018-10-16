package datamodels;

import java.util.List;

/**
 * Created by Shamyyoun on 10/7/2015.
 */
public class CompetitionMatches {
    private String title;
    private int logo;
    private int round;
    private List<Match> matches;

    public CompetitionMatches(String title, int logo, int round, List<Match> matches) {
        this.title = title;
        this.logo = logo;
        this.round = round;
        this.matches = matches;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
