package com.example.juhee.cooking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by juhee on 2016. 7. 25..
 */
public class RefrigerListViewAdapter extends BaseAdapter{
    private Context mContext = null;
    private ArrayList<String> m_List;
    private String USER_ID;
    private String DELETE_URL = "http://143.248.47.69:10900/storage";

    public RefrigerListViewAdapter(Context mContext,String userid) {
        m_List = new ArrayList<String>();
        this.mContext = mContext;
        USER_ID = userid;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.refrigerator_listview, parent, false);

            final String iteminfo =  m_List.get(position);
            Log.e("RefrigerAdapter",iteminfo.toString());

            TextView material = (TextView)convertView.findViewById(R.id.materialname);
            material.setText(iteminfo.toString());

            Button deleteBtn = (Button) convertView.findViewById(R.id.deletebtn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    sendDELETE SENDDELETE = new sendDELETE();
                    try {
                        SENDDELETE.send(DELETE_URL,USER_ID,iteminfo.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.e("Delete",iteminfo.toString());
                    Log.e("position", String.valueOf(position));
                    Log.e("m_List",m_List.toString());
                    //m_List.remove(position);
                    Log.e("m_List",m_List.toString());
                    //RefrigerListViewAdapter.this.m_List.remove(position);
                    RefrigerListViewAdapter.this.notifyDataSetChanged();
                    setArrayList(m_List);


                }
            });
        }
        return convertView;
    }
    public void setArrayList(ArrayList<String> arrays){
        this.m_List = arrays;
    }

}
