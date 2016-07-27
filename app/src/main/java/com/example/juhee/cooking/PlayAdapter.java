package com.example.juhee.cooking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by juhee on 2016. 7. 26..
 */
public class PlayAdapter extends BaseAdapter {
    private ArrayList<String> m_List;
    private Context m_Context;
    String mVideoId;
    String mVideoTitle;

    public PlayAdapter(Context context) {
        m_List = new ArrayList<String>();
        m_Context = context;
        //listArray = (JSONArray)ja;
    }
    @Override
    public int getCount() {
        return m_List.size();
    }
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void add(String _msg) {
        m_List.add(_msg);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.youtube_listview, parent, false);

            TextView mTitle = (TextView) convertView.findViewById(R.id.youtube_title);

            final String iteminfo =  m_List.get(position);
            try {
                JSONObject jo = new JSONObject(iteminfo);
                mVideoTitle = jo.getJSONObject("snippet").getString("title");
                mVideoId = jo.getJSONObject("id").getString("videoId");
                mTitle.setText(mVideoTitle);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Button playBtn = (Button) convertView.findViewById(R.id.playbtn);
            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("PlayAdapter",iteminfo);
                    Log.e("mVideoId",mVideoId);
                    Log.e("mVideoTitle",mVideoTitle);
                    Intent intent = new Intent(m_Context,ExtraYoutubePlay.class);
                    intent.putExtra("videoId",mVideoId);
                    intent.putExtra("videoTitle",mVideoTitle);
                    m_Context.startActivity(intent);
                }
            });
        }



        return convertView;
    }
}