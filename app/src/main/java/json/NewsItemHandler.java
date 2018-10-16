package json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import datamodels.NewsItem;
import datamodels.Team;
import datamodels.User;

public class NewsItemHandler {
    private NewsItem newsItem;
    private JSONObject jsonObject;

    public NewsItemHandler(NewsItem newsItem, JSONObject jsonObject) {
        this.newsItem = newsItem;
        this.jsonObject = jsonObject;
    }

    public NewsItem handle() {
        try {
            // parse json object
            String title = jsonObject.getString("News_Title");
            String details = jsonObject.getString("News_Detail");
            String tags = jsonObject.getString("News_Tags");
            String source = jsonObject.getString("News_Source");
            String url = jsonObject.getString("News_Link");

            // set values
            newsItem.setTitle(title);
            newsItem.setDetails(details);
            newsItem.setTags(tags);
            newsItem.setSource(source);
            newsItem.setUrl(url);
        } catch (Exception e) {
            newsItem = null;
            e.printStackTrace();
        }

        return newsItem;
    }
}
