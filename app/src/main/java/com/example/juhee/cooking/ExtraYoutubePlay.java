package com.example.juhee.cooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ExtraYoutubePlay extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String videoid =null;
    String videoTitle =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_youtube_play_activity);

        Intent intent = getIntent();
        videoid = intent.getStringExtra("videoId");
        videoTitle = intent.getStringExtra("videoTitle");

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view2);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(videoid); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            videoid =null;
        }
    }



    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(ExtraYoutubePlay.this,RECOVERY_REQUEST).show();
        }
        else {
            String error = String.format(getString(R.string.player_error),youTubeInitializationResult.toString());
            Toast.makeText(this,error,Toast.LENGTH_LONG).show();
        }
    }

}