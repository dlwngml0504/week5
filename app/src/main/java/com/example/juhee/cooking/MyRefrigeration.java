package com.example.juhee.cooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyRefrigeration extends AppCompatActivity {
    String[] items = { "SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5","K7" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_refrigeration_activity);

        final AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.foodname);
        edit.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, items));

        Button add_btn = (Button)findViewById(R.id.add);
        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.e("text",edit.getText().toString());
            }
        });
    }
}
