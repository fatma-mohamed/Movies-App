package com.example.android.movies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.movies.fragments.PostersFragment;
import com.example.android.movies.R;
import com.example.android.movies.data.DatabaseHelper;
import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Fatma on 21-Oct-16.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private static HashMap<String,String> [] values;
    private DatabaseHelper dbHelper;

    public ImageAdapter(Context c, HashMap<String,String> [] v)
    {
        dbHelper = PostersFragment.db;
        mContext = c;
        values = v;
    }

    public ImageAdapter()
    {

    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent)
    {
        LayoutInflater
                inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.posters_grid_item,null);
        ImageView image = (ImageView) rowView.findViewById(R.id.grid_image);
        String url = MoviesContract.PICASSO_URL+ values[position].get("poster_path");
        Picasso.with(rowView.getContext())
                .load(url)
                .centerInside()
                .resize(400,400)
                .into(image);
        return rowView;
    }

    @Override
    public int getCount() {
        return values.length;
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

    public long getPosition(String name){
        int i=0;
        for (HashMap<String,String> m : values){
            if(m.get("original_title").equals(name))
                return i;
            ++i;
        }
        return -1;
    }

    public void add(HashMap<String,String> [] r)
    {
        values = r;
        notifyDataSetChanged();
    }

    public void sortBy(String wrt)
    {
        if(wrt.equals("popularity"))
        {
            this.add(values);
            Collections.sort(Arrays.asList(values), new Comparator<HashMap<String,String>>() {
                @Override
                public int compare(HashMap<String,String>a ,HashMap<String,String>b)
                {
                    return a.get("popularity").compareTo(b.get("popularity"));
                }
            });
        }
        else if(wrt.equals("top_rated"))
        {
            this.add(values);
            Collections.sort(Arrays.asList(values), new Comparator<HashMap<String,String>>() {
                @Override
                public int compare(HashMap<String,String>a ,HashMap<String,String>b)
                {
                    return a.get("vote_average").compareTo(b.get("vote_average"));
                }
            });
        }
        this.notifyDataSetChanged();
    }

    public void showFav()
    {
        ArrayList<String> fav = dbHelper.getFavourites();
        HashMap<String,String> [] favValues = new HashMap[fav.size()];
        int index = 0;
        for(int i = 0; i< values.length; ++i)
        {
            if(fav.contains(values[i].get("original_title")))
            {
                favValues[index] = values[i];
                index++;
            }

        }
        if(fav.size()==0)
        {
            Toast.makeText(mContext,"You have no favourites",Toast.LENGTH_LONG).show();
        }
        else
        {
            this.add(favValues);
            this.notifyDataSetChanged();
        }
    }
}
