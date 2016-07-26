package com.example.juhee.cooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String videoid;
    private PlayAdapter m_Adapter;
    private ListView m_ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity);
        Intent intent = getIntent();
        videoid = intent.getStringExtra("videoId");
        String title = intent.getStringExtra("title");
        String playList = intent.getStringExtra("playInfo");
        Log.e("TITLE",title);
        Log.e("platList",playList);
        m_Adapter = new PlayAdapter(YoutubeActivity.this);
        m_ListView = (ListView)findViewById(R.id.youtubeList);
        m_ListView.setAdapter(m_Adapter);
        try {
            JSONArray ja = new JSONArray(playList);
            for (int i =0; i<5 ;i++) {
                m_Adapter.add(ja.getJSONObject(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(videoid); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(YoutubeActivity.this,RECOVERY_REQUEST).show();
        }
        else {
            String error = String.format(getString(R.string.player_error),youTubeInitializationResult.toString());
            Toast.makeText(this,error,Toast.LENGTH_LONG).show();
        }
    }
}