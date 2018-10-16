package datamodels;

/**
 * Created by Dahman on 9/21/2015.
 */
public class MenuItem {
    private int id;
    private String title;

    public MenuItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public MenuItem() {
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
}
