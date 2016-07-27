package com.example.juhee.cooking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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
            Log.e("RecommandAdapter",iteminfo.toString());

            TextView material = (TextView)convertView.findViewById(R.id.recommand_name);
            material.setText(iteminfo.toString());

            Button playBtn = (Button) convertView.findViewById(R.id.selectbtn);
            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                    alertbox.setMessage("No Internet Connection");
                    alertbox.setTitle("Warning");

                    alertbox.setNeutralButton("OK",
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
