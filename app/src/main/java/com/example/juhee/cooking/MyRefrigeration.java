package com.example.juhee.cooking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MyRefrigeration extends AppCompatActivity {

    public static final String mPath = "material.txt";
    private TextReader mTextReader;
    private List<String> mLines;
    private List<String> Menu_List = new ArrayList<>();
    static RefrigerListViewAdapter m_Adapter;
    static ListView m_ListView;
    private String userID;
    private String POST_URL = "http://143.248.47.69:10900/storage";
    private String GET_URL = "http://143.248.47.69:10900/storage?fid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_refrigeration_activity);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userid");
        mTextReader = new TextReader(this);
        mLines = mTextReader.readLine(mPath);
        for (String string : mLines){
            Menu_List.add(string);
        }

        new PostRecommandMenu().execute();


        m_Adapter = new RefrigerListViewAdapter(MyRefrigeration.this,userID);
        m_ListView = (ListView)findViewById(R.id.refrigeration_list);
        m_ListView.setAdapter(m_Adapter);

        final AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.foodname);
        edit.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, Menu_List));

        Button add_btn = (Button)findViewById(R.id.add);
        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                m_Adapter.add(edit.getText().toString());
                m_Adapter.notifyDataSetChanged();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendPOST SENDPOST = new sendPOST();
                        try {
                            SENDPOST.send(POST_URL,userID,edit.getText().toString());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }}
                }).start();
                edit.setText("");
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backintent = new Intent(MyRefrigeration.this,MainActivity.class);
        backintent.putExtra("userid",userID);
        startActivity(backintent);
    }
    private class PostRecommandMenu extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                sendGET SENDGET = new sendGET();
                String userIngre = SENDGET.send(GET_URL, userID);
                JSONObject result = new JSONObject(userIngre);
                JSONObject Result_JO = result.getJSONArray("results").getJSONObject(0);
                String user_Ingre = Result_JO.getString("ingredients");

                String[] Ingre_List = user_Ingre.substring(1,user_Ingre.length()-1).split(",");

                if (Ingre_List[0].isEmpty()==false){
                    for (int i=0;i<Ingre_List.length;i++ ) {
                        m_Adapter.add(Ingre_List[i].substring(1,Ingre_List[i].length()-1));
                    }
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
            m_Adapter.notifyDataSetChanged();
        }
    }
}


/*
package com.example.juhee.cooking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyRefrigeration extends AppCompatActivity {

    public static final String mPath = "material.txt";
    private TextReader mTextReader;
    private List<String> mLines;
    private List<String> Menu_List = new ArrayList<>();
    private RefrigerListViewAdapter m_Adapter;
    private ListView m_ListView;
    private String userID;
    private String POST_URL = "http://143.248.47.69:10900/storage";
    private String GET_URL = "http://143.248.47.69:10900/storage?fid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_refrigeration_activity);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userid");
        mTextReader = new TextReader(this);
        mLines = mTextReader.readLine(mPath);
        for (String string : mLines){
            Menu_List.add(string);
        }

        m_Adapter = new RefrigerListViewAdapter(MyRefrigeration.this,userID);
        m_ListView = (ListView)findViewById(R.id.refrigeration_list);
        m_ListView.setAdapter(m_Adapter);
        final AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.foodname);
        edit.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, Menu_List));

        new PostRecommandMenu().execute();

        Button add_btn = (Button)findViewById(R.id.add);
        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                m_Adapter.add(edit.getText().toString());
                m_Adapter.notifyDataSetChanged();
                edit.setText("");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendPOST SENDPOST = new sendPOST();
                        try {
                            SENDPOST.send(POST_URL,userID,edit.getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }}
                }).start();
            }
        });
    }

    private class PostRecommandMenu extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                sendGET SENDGET = new sendGET();
                String userIngre = SENDGET.send(GET_URL, userID);
                JSONObject result = new JSONObject(userIngre);
                JSONObject Result_JO = result.getJSONArray("results").getJSONObject(0);
                String user_Ingre = Result_JO.getString("ingredients");
                String[] Ingre_List = user_Ingre.substring(1,user_Ingre.length()-1).split(",");
                Log.e("Ingre_List.length", String.valueOf(Ingre_List.length));
                for (int i=0;i<Ingre_List.length;i++ ) {
                    m_Adapter.add(Ingre_List[i].substring(1,Ingre_List[i].length()-1));
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
            m_Adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backintent = new Intent(MyRefrigeration.this,MainActivity.class);
        backintent.putExtra("userid",userID);
        startActivity(backintent);
    }
}
*/
