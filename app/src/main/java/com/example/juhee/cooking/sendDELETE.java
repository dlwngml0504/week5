package com.example.juhee.cooking;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by juhee on 2016. 7. 27..
 */
public class sendDELETE {
    protected void send(final String DELETE_URL, final String param1, final String param2) throws IOException {
        //String mURL = GET_URL+ URLEncoder.encode(param1, "UTF-8") + "&ingredient=" + URLEncoder.encode(param2,"UTF-8");

        new Thread(new Runnable() {
            @Override
            public void run() {

                URL obj = null;
                try {
                    obj = new URL(DELETE_URL);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("DELETE");
                    con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
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
                    Log.e("sendDELTE","POST Response Code :: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.e("SendPOST","Success");
                    } else {
                        Log.e("SendPOST","POST request not worked");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
