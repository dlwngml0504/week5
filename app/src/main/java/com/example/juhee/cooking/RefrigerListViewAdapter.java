package com.example.juhee.cooking;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by juhee on 2016. 7. 25..
 */
public class RefrigerListViewAdapter extends BaseAdapter{
    private Context mContext = null;
    private ArrayList<String> mListData = new ArrayList<String>();

    public RefrigerListViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        return null;
    }
}
