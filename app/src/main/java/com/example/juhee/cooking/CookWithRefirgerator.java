package com.example.juhee.cooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class CookWithRefirgerator extends AppCompatActivity {
    private RecommandListAdapter m_Adapter;
    private ListView m_ListView;
    private String GET_URL = "http://143.248.47.69:10900/good?fid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_with_refirgerator_activity);
        Intent intent =  getIntent();
        String userID = intent.getStringExtra("userid");
        String iserIngredient = intent.getStringExtra("useringredient");

        m_Adapter = new RecommandListAdapter(CookWithRefirgerator.this);
        m_ListView = (ListView)findViewById(R.id.recommendList);
        m_ListView.setAdapter(m_Adapter);

       /*try {
            sendGET SENDGET = new sendGET();
            String mMenuStr = SENDGET.send(GET_URL,userID);
            JSONArray ja = new JSONArray(mMenuStr);
            for (int i=0;i<ja.length();i++) {
                m_Adapter.add(ja.getString(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        m_Adapter.add("고추참치");
        m_Adapter.add("사과");
        m_Adapter.notifyDataSetChanged();

    }
}
