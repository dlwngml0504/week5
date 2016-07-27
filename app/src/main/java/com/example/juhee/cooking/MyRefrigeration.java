package com.example.juhee.cooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendGET SENDGET = new sendGET();
                    String userIngre = SENDGET.send(GET_URL, userID);
                    Log.e("MyRefrigerator~!!!", userIngre);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


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
}
