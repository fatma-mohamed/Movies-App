package com.example.android.movies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.movies.R;

import java.util.HashMap;

/**
 * Created by Fatma on 25-Nov-16.
 */

public class ReviewAdapter extends BaseAdapter {
    private Context mContext;
    private static HashMap<String,String>[] values;

    public ReviewAdapter(Context c, HashMap<String,String> [] v)
    {
        mContext = c;
        values = v;
    }

    public ReviewAdapter()
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
        HashMap<String,String> movie = values[position];
        return movie;
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

        View itemView = inflater.inflate(R.layout.review_item,null);
        TextView author = (TextView)itemView.findViewById(R.id.author);
        TextView content = (TextView)itemView.findViewById(R.id.content);
        author.setText(values[position].get("author"));
        content.setText(values[position].get("content"));
        return itemView;
    }
}
