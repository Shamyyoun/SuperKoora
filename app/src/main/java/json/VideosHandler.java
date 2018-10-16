package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import datamodels.Video;

public class VideosHandler {
    private JSONArray jsonArray;

    public VideosHandler(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public List<Video> handle() {
        List<Video> videos = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                // parse video
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("Video_Id");
                String title = jsonObject.getString("Video_Name");
                String image = jsonObject.getString("Video_Picture");
                String youtubeId = jsonObject.getString("Video_Link");
                boolean favorite = jsonObject.getInt("Favourite") == 0 ? false : true;

                // add to news items list
                Video video = new Video(id);
                video.setTitle(title);
                video.setImage(image);
                video.setYoutubeId(youtubeId);
                video.setFavorite(favorite);
                videos.add(video);
            }
        } catch (JSONException e) {
            videos = null;
            e.printStackTrace();
        }

        return videos;
    }
}
