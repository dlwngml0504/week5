package com.example.juhee.cooking;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by juhee on 2016. 7. 27..
 */
public class sendGET {
    protected String send(String GET_URL,String param) throws IOException {
        String mURL = GET_URL+ URLEncoder.encode(param, "UTF-8");
        URL obj = new URL(mURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        Log.e("sendGET","GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Log.e("SearchCooking",response.toString());
            return response.toString();
        } else {
            Log.e("SearchCooking","GET request not worked");
        }
        return "";
    }
}
