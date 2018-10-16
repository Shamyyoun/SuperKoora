package datamodels;

import java.io.Serializable;

/**
 * Created by Dahman on 9/20/2015.
 */
public class Team1 implements Serializable {
    private String title;
    private int logo;
    private String color; // stored as hash color

    public Team1(String title, int logo) {
        this.title = title;
        this.logo = logo;
    }

    public Team1() {
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

    public void setLogo(int imageResId) {
        this.logo = imageResId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return title;
    }
}
