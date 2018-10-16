package datamodels;

import java.io.Serializable;

/**
 * Created by Shamyyoun on 10/13/2015.
 */
public class Competition implements Serializable{
    private int backgroundResId;
    private int logo;
    private String title;

    public Competition(int backgroundResId, int logo, String title) {
        this.backgroundResId = backgroundResId;
        this.logo = logo;
        this.title = title;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public void setBackgroundResId(int backgroundResId) {
        this.backgroundResId = backgroundResId;
    }
}
