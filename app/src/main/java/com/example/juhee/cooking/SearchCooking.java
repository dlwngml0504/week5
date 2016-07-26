package com.example.juhee.cooking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchCooking extends AppCompatActivity {
    String Query_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&key=AIzaSyDXgwnlZ35SmzwN1NA2GZsPKl3NUkEGeX0&q=";
    public static final String mPath = "menu.txt";
    private TextReader mTextReader;
    private List<String> mLines;
    private List<String> Menu_List = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchmenu_activity);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mTextReader = new TextReader(this);
        mLines = mTextReader.readLine(mPath);
        for (String string : mLines){
            Menu_List.add(string);
        }

        final AutoCompleteTextView cooking = (AutoCompleteTextView) findViewById(R.id.searchFood);
        cooking.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, Menu_List));

        Button YOUTUBE = (Button)findViewById(R.id.search_by_youtube);
        YOUTUBE.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String url_String = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&key=AIzaSyDXgwnlZ35SmzwN1NA2GZsPKl3NUkEGeX0&q=";
                url_String += cooking.getText();
                url_String += "%20만들기";
                Log.e("SearchCookgin",Query_URL);
                Query_URL = url_String;
                new ConnectURL().execute(Query_URL);

            }
        });
    }


    private class ConnectURL extends AsyncTask<String, String, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(String... urls) {
            JSONObject json = null;
            Log.e("Doinbackground",urls[0]);
            try {
                // call API by using HTTPURLConnection
                URL url = new URL(Query_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.e("SearchCooking",in.toString());
                json = new JSONObject(getStringFromInputStream(in));
                Log.e("JSON",json.getJSONArray("items").getJSONObject(0).toString());
                return json.getJSONArray("items");
            }catch(MalformedURLException e){
                System.err.println("Malformed URL");
                e.printStackTrace();
            }catch(JSONException e) {
                System.err.println("JSON parsing error");
                e.printStackTrace();
            }catch(IOException e){
                System.err.println("URL Connection failed");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            Intent intent = new Intent(SearchCooking.this,YoutubeActivity.class);
            JSONArray ja = (JSONArray) result;
            try {
                intent.putExtra("videoId",ja.getJSONObject(0).getJSONObject("id").getString("videoId"));
                intent.putExtra("title",ja.getJSONObject(0).getJSONObject("snippet").getString("title"));
                intent.putExtra("playInfo",ja.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    }
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
