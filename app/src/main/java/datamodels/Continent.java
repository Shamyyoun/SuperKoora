package datamodels;

import java.io.Serializable;

/**
 * Created by Shamyyoun on 10/13/2015.
 */
public class Continent implements Serializable {
    private int id;
    private int backgroundResId;
    private int logoResId;

    public Continent(int id, int backgroundResId, int logoResId) {
        this.id = id;
        this.backgroundResId = backgroundResId;
        this.logoResId = logoResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public void setBackgroundResId(int backgroundResId) {
        this.backgroundResId = backgroundResId;
    }

    public int getLogoResId() {
        return logoResId;
    }

    public void setLogoResId(int logoResId) {
        this.logoResId = logoResId;
    }
}
