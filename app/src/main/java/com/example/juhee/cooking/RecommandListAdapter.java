package com.example.juhee.cooking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by juhee on 2016. 7. 27..
 */
public class RecommandListAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<String> m_List;

    public RecommandListAdapter(Context mContext) {
        m_List = new ArrayList<String>();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return m_List.size();
    }
    public void add(String _msg) {
        m_List.add(_msg);
    }
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recommand_listview, parent, false);

            final String iteminfo =  m_List.get(position);

            /*음식 이름 추가*/
            final TextView material = (TextView)convertView.findViewById(R.id.recommand_name);
            JSONObject jo = null;
            try {
                jo = new JSONObject(iteminfo.toString());
                material.setText(jo.getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /* 사진 업로드*/
            ImageView _image = (ImageView)convertView.findViewById(R.id.MENU_IMAGE);
            final Bitmap[] bitmap = new Bitmap[1];

            final JSONObject finalJo1 = jo;
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(finalJo1.getString("img_url"));
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap[0] = BitmapFactory.decodeStream(is);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            mThread.start();
            try {
                mThread.join();
                _image.setImageBitmap(bitmap[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Button playBtn = (Button) convertView.findViewById(R.id.selectbtn);
            final JSONObject finalJo = jo;
            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                    //alertbox.setMessage(finalJo.toString());
                    alertbox.setTitle(material.getText());


                    try {

                         /* 재료 파싱 */
                        String[] mainIng_List = finalJo.getString("main_ingredients").split("],");
                        String[] subIng_List = finalJo.getString("sub_ingredients").split("],");
                        String _ingredient = "";
                        Log.e("mainIng_List.length", String.valueOf(mainIng_List.length));
                        if (mainIng_List.length!=1) {
                            String[] lst1 = mainIng_List[0].split("\",\"");
                            _ingredient += lst1[0].substring(3) + "[" +lst1[1].substring(0,lst1[1].length()-1)+"]";
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
                            if (mainIng_List.length!=1){
                                _ingredient += ", ";
                            }
                            _ingredient += lst3[0].substring(3) + "[" +lst3[1].substring(0,lst3[1].length()-1)+"]";
                            for (int i=1;i<subIng_List.length-1;i++) {
                                String str = subIng_List[i];
                                String[] lst = str.split("\",\"");
                                _ingredient += ", "+lst[0].substring(2) + "[" +lst[1].substring(0,lst[1].length()-1)+"]";
                            }
                            String[] lst4 = subIng_List[subIng_List.length-1].split("\",\"");
                            _ingredient += ", "+lst4[0].substring(2) + "[" +lst4[1].substring(0,lst4[1].length()-3)+"]";

                        }

                        /* 레시피 parsing */
                        String[] reciple_List = finalJo.getString("recipe").split("\",\"");
                        String _recipe = "1. " + reciple_List[0].substring(2) + "\n";
                        for (int i = 2; i < reciple_List.length; i++) {
                            _recipe += "\n"+i + ". " + reciple_List[i - 1] + "\n";
                        }
                        _recipe += "\n"+reciple_List.length + ". " + reciple_List[reciple_List.length - 1].split("\"]")[0];
                        alertbox.setMessage("==========<<재료>>==========\n"+_ingredient+"\n\n==========<<조리법>>==========\n"+_recipe);


                        /*사진 upload*/


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertbox.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,int arg1) {

                                }
                            });
                    alertbox.show();
                }

            });
        }
        return convertView;
    }

}
