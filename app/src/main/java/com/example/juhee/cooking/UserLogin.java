package com.example.juhee.cooking;

/**
 * Created by juhee on 2016. 7. 27..
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserLogin extends AsyncTask<String,Void,String>{
    private Context mContext;
    private String userID;
    private String ingredient;
    public UserLogin (Context context) {
        mContext = context;
    }
    private static String GET_URL = "http://143.248.47.69:10900/storage?fid=";

    @Override
    protected String doInBackground(String... params) {
        Log.e("UserLogin",params[0]);
        userID = params[0];
        ingredient = "참외,사과,낫또";

        /*try {
            sendGET SENDGET = new sendGET();
            ingredient = SENDGET.send(GET_URL,userID);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Log.e("UserLogin",ingredient);
        return ingredient;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userid",userID);
        intent.putExtra("useringredient",result);
        mContext.startActivity(intent);
    }
}
