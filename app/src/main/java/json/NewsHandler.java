package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import datamodels.NewsItem;

public class NewsHandler {
    private JSONArray jsonArray;

    public NewsHandler(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public List<NewsItem> handle() {
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                // parse item
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("News_Id");
                String title = jsonObject.getString("News_Title");
                String image = jsonObject.getString("News_Picture");
                String date = jsonObject.getString("News_Date_Time");
                boolean favorite = jsonObject.getInt("Favourite") == 0 ? false : true;

                // add to news items list
                NewsItem item = new NewsItem(id);
                item.setTitle(title);
                item.setImage(image);
                item.setDate(date);
                item.setFavorite(favorite);
                newsItems.add(item);
            }
        } catch (JSONException e) {
            newsItems = null;
            e.printStackTrace();
        }

        return newsItems;
    }
}
