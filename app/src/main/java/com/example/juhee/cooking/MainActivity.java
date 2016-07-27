package com.example.juhee.cooking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userid");
        Log.e("MainActivity-userid",userID);

        Button btn1 = (Button)findViewById(R.id.btn1);

        btn1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,CookWithRefirgerator.class);
                intent1.putExtra("userid",userID);
                startActivity(intent1);
            }
        });

        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,SearchCooking.class);
                intent2.putExtra("userid",userID);
                startActivity(intent2);
            }
        });
        Button btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this,MyRefrigeration.class);
                intent3.putExtra("userid",userID);
                startActivity(intent3);
            }
        });
    }

}
