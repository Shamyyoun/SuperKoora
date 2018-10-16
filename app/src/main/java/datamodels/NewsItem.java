package datamodels;

import java.io.Serializable;
import java.util.Date;

import utils.StringUtil;

/**
 * Created by Dahman on 9/21/2015.
 */
public class NewsItem implements Serializable {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private int id;
    private String title;
    private String image;
    private String details;
    private Date date;
    private String tags;
    private String source;
    private String url;
    private boolean favorite;

    public NewsItem(int id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = StringUtil.convertToDate(date, DATE_FORMAT);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
