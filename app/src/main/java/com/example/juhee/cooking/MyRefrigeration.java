package com.example.juhee.cooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MyRefrigeration extends AppCompatActivity {

    public static final String mPath = "material.txt";
    private TextReader mTextReader;
    private List<String> mLines;
    private List<String> Menu_List = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_refrigeration_activity);

        mTextReader = new TextReader(this);
        mLines = mTextReader.readLine(mPath);
        for (String string : mLines){
            Menu_List.add(string);
        }

        final AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.foodname);
        edit.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, Menu_List));

        Button add_btn = (Button)findViewById(R.id.add);
        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.e("text",edit.getText().toString());
            }
        });
    }
}
