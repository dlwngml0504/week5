package com.example.juhee.cooking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchCooking extends AppCompatActivity {
    String Query_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&key=AIzaSyDXgwnlZ35SmzwN1NA2GZsPKl3NUkEGeX0&q=";
    public static final String mPath = "menu.txt";
    private TextReader mTextReader;
    private List<String> mLines;
    private String USER_ID;
    private List<String> Menu_List = new ArrayList<>();
    private static String GET_URL = "http://143.248.47.69:10900/food?title=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchmenu_activity);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent intent = getIntent();
        USER_ID = intent.getStringExtra("userid");

        mTextReader = new TextReader(this);
        mLines = mTextReader.readLine(mPath);
        for (String string : mLines) {
            Menu_List.add(string);
        }

        final AutoCompleteTextView cooking = (AutoCompleteTextView) findViewById(R.id.searchFood);
        cooking.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Menu_List));

        Button btn = (Button) findViewById(R.id.searchBtn);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.e("SearchCooking", "btnClick");
                SearchMenu searchmenu = new SearchMenu(getApplicationContext());
                searchmenu.execute(cooking.getText().toString());
            }
        });


        Button YOUTUBE = (Button) findViewById(R.id.search_by_youtube);
        YOUTUBE.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String url_String = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&key=AIzaSyDXgwnlZ35SmzwN1NA2GZsPKl3NUkEGeX0&q=";
                try {
                    url_String += URLEncoder.encode(String.valueOf(cooking.getText()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url_String += "%20만들기";
                Log.e("SearchCookgin", Query_URL);
                Query_URL = url_String;
                new ConnectURL().execute(Query_URL);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backintent = new Intent(SearchCooking.this,MainActivity.class);
        backintent.putExtra("userid",USER_ID);
        startActivity(backintent);
    }


    private class ConnectURL extends AsyncTask<String, String, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(String... urls) {
            JSONObject json = null;
            Log.e("Doinbackground", urls[0]);
            try {
                // call API by using HTTPURLConnection
                URL url = new URL(Query_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                json = new JSONObject(getStringFromInputStream(in));
                Log.e("JSON", json.getJSONArray("items").getJSONObject(0).toString());
                return json.getJSONArray("items");
            } catch (MalformedURLException e) {
                System.err.println("Malformed URL");
                e.printStackTrace();
            } catch (JSONException e) {
                System.err.println("JSON parsing error");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("URL Connection failed");
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(Object result) {

            Intent intent = new Intent(SearchCooking.this, YoutubeActivity.class);
            JSONArray ja = (JSONArray) result;
            try {
                intent.putExtra("uerid",USER_ID);
                intent.putExtra("videoId", ja.getJSONObject(0).getJSONObject("id").getString("videoId"));
                intent.putExtra("title", ja.getJSONObject(0).getJSONObject("snippet").getString("title"));
                intent.putExtra("playInfo", ja.toString());
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

    private class SearchMenu extends AsyncTask<String, Void, String> {
        Context mContext;
        String mMenu;
        String MenuResult;

        public SearchMenu(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                sendGET SENDGET = new sendGET();
                MenuResult = SENDGET.send(GET_URL, params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return MenuResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("onPostExecute", result);
            TextView main = (TextView) findViewById(R.id.main_ingredients);
            TextView mTitle = (TextView) findViewById(R.id.mMenu);
            TextView recipe = (TextView) findViewById(R.id.recipe);
            try {
                JSONObject Result_jo = new JSONObject(result);
                JSONArray ja = Result_jo.getJSONArray("results");
                JSONObject jo = ja.getJSONObject(0);

                /*요리 이름 등록*/
                mTitle.setText(jo.getString("title"));

                /* 재료 파싱 */
                String[] mainIng_List = jo.getString("main_ingredients").split("],");
                String[] subIng_List = jo.getString("sub_ingredients").split("],");
                String _ingredient = "";

                Log.e("mainIng_List.length", String.valueOf(mainIng_List.length));
                if (mainIng_List.length!=1) {
                    String[] lst1 = mainIng_List[0].split("\",\"");
                    _ingredient += lst1[0].substring(3) + "[" +lst1[1].substring(0,lst1[1].length()-2)+"]";
                    for (int i=1;i<mainIng_List.length-1;i++) {
                        String str = mainIng_List[i];
                        String[] lst = str.split("\",\"");
                        _ingredient += ", "+lst[0].substring(2) + "[" +lst[1].substring(0,lst[1].length()-1)+"]";
                    }
                    String[] lst2 = mainIng_List[mainIng_List.length-1].split("\",\"");
                    _ingredient += ", "+lst2[0].substring(3) + "[" +lst2[1].substring(0,lst2[1].length()-3)+"]";

                }
                if (subIng_List.length!=1) {
                    String[] lst3 = subIng_List[0].split("\",\"");
                    _ingredient += ", "+lst3[0].substring(3) + "[" +lst3[1].substring(0,lst3[1].length()-2)+"]";
                    for (int i=1;i<subIng_List.length-1;i++) {
                        String str = subIng_List[i];
                        String[] lst = str.split("\",\"");
                        _ingredient += ", "+lst[0].substring(2) + "[" +lst[1].substring(0,lst[1].length()-1)+"]";
                    }
                    String[] lst4 = subIng_List[subIng_List.length-1].split("\",\"");
                    _ingredient += ", "+lst4[0].substring(3) + "[" +lst4[1].substring(0,lst4[1].length()-3)+"]";

                }

                main.setText(_ingredient);

                /* 레시피 parsing */
                String[] reciple_List = jo.getString("recipe").split("\",\"");
                String _recipe = "1. " + reciple_List[0].substring(2) + "\n";
                for (int i = 2; i < reciple_List.length; i++) {
                    _recipe += i + ". " + reciple_List[i - 1] + "\n";
                }
                _recipe += reciple_List.length + ". " + reciple_List[reciple_List.length - 1].split("\"]")[0];
                recipe.setText(_recipe);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
