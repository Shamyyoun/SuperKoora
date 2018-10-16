package datamodels;

import java.util.List;

/**
 * Created by Shamyyoun on 10/26/2015.
 */
public class TeamGroup {
    private String title;
    private List<Standing> standings;

    public TeamGroup(String title, List<Standing> standings) {
        this.title = title;
        this.standings = standings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Standing> getStandings() {
        return standings;
    }

    public void setStandings(List<Standing> standings) {
        this.standings = standings;
    }
}
