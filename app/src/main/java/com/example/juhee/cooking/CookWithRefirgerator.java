package com.example.juhee.cooking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CookWithRefirgerator extends AppCompatActivity {
    private RecommandListAdapter m_Adapter;
    private ListView m_ListView;
    private String userID;
    private String GET_URL = "http://143.248.47.69:10900/food?fid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_with_refirgerator_activity);
        Intent intent =  getIntent();
        userID = intent.getStringExtra("userid");

        m_Adapter = new RecommandListAdapter(CookWithRefirgerator.this);
        m_ListView = (ListView)findViewById(R.id.recommendList);
        m_ListView.setAdapter(m_Adapter);

        new SHOWLIST().execute();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backintent = new Intent(CookWithRefirgerator.this,MainActivity.class);
        backintent.putExtra("userid",userID);
        startActivity(backintent);
    }

    private class SHOWLIST extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                sendGET SENDGET = new sendGET();
                String mMenuStr = SENDGET.send(GET_URL,userID);
                Log.e("COOKKINGKDG",mMenuStr);
                JSONArray ja =  new JSONObject(mMenuStr).getJSONArray("results");
                for (int i=0;i<ja.length();i++) {
                    m_Adapter.add(ja.getString(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            m_Adapter.notifyDataSetChanged();
        }
    }

}
