package com.example.juhee.cooking;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by juhee on 2016. 7. 27..
 */
public class sendPOST{
    protected void send(String GET_URL,String param1, String param2) throws IOException {
        //String mURL = GET_URL+"?fid="+ URLEncoder.encode(param1, "UTF-8") + "&ingredient=" + URLEncoder.encode(param2,"UTF-8");
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoInput(true);
        con.setDoOutput(true);



        JSONObject jo = new JSONObject();
        try {
            jo.put("fid",param1);
            jo.put("ingredient",param2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OutputStream os = con.getOutputStream();
        os.write(jo.toString().getBytes("UTF-8"));
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        Log.e("snedPOST","POST Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Log.e("SendPOST","Success");
        } else {
            Log.e("SendPOST","POST request not worked");
        }

    }
}
