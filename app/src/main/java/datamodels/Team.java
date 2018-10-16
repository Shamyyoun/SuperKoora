package datamodels;

/**
 * Created by Shamyyoun on 11/3/2015.
 */
public class Team {
    private int id;
    private String nameAr;
    private String nameEn;
    private String logo;
    private String color;

    public Team(int id, String nameAr, String nameEn, String logo, String color) {
        this.id = id;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.logo = logo;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
