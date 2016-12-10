package com.example.android.movies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.R;

import java.util.HashMap;

/**
 * Created by Fatma on 25-Nov-16.
 */

public class TrailerAdapter extends BaseAdapter {
    private Context mContext;
    private static HashMap<String,String>[] values;

    public TrailerAdapter(Context c, HashMap<String,String> [] v)
    {
        mContext = c;
        values = v;
    }

    public TrailerAdapter()
    {

    }

    @Override
    public int getCount() {
        return values.length;
    }

    public void add(HashMap<String,String> [] r)
    {
        values = r;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        HashMap<String,String> trailer = values[position];
        return trailer;
    }


    public String getKey(int position) {
        HashMap<String,String> trailer = values[position];
        return trailer.get("key");
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater
                inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.trailer_item,null);
        TextView name = (TextView)itemView.findViewById(R.id.trailer_name);
        ImageView play = (ImageView) itemView.findViewById(R.id.icon_play);
        name.setText(values[position].get("name"));
        play.setImageResource(R.mipmap.ic_play);
        return itemView;
    }
}
