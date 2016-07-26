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

import java.util.ArrayList;

/**
 * Created by juhee on 2016. 7. 25..
 */
public class RefrigerListViewAdapter extends BaseAdapter{
    private Context mContext = null;
    private ArrayList<String> m_List;

    public RefrigerListViewAdapter(Context mContext) {
        m_List = new ArrayList<String>();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return m_List.size();
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
    public View getView(int position,View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.refrigerator_listview, parent, false);

            final String iteminfo =  m_List.get(position);

            Button playBtn = (Button) convertView.findViewById(R.id.playbtn);
            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }



        return convertView;
    }


}
