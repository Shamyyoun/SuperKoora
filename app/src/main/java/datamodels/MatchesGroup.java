package datamodels;

import java.util.List;

/**
 * Created by Shamyyoun on 10/26/2015.
 */
public class MatchesGroup {
    private String date;
    private List<Match> matches;

    public MatchesGroup(String date, List<Match> matches) {
        this.date = date;
        this.matches = matches;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
