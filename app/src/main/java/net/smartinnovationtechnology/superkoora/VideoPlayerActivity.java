package net.smartinnovationtechnology.superkoora;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import datamodels.Constants;

public class VideoPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private String mVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // get video id
        mVideoId = getIntent().getStringExtra(Constants.KEY_VIDEO_ID);

        // init youtube player view
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        playerView.initialize(AppController.GOOGLE_API_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        // show error toast
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        // start buffering
        if (!wasRestored) {
            player.cueVideo(mVideoId);
        }
    }
}
