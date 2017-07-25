package com.example.android.movies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.R;
import com.example.android.movies.data.DatabaseHelper;
import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment{
    private Bundle movieData;
    private boolean btnClicked = false;
    private DatabaseHelper db;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        db = PostersFragment.db;
        movieData = getArguments();

        ImageView image = (ImageView) rootView.findViewById(R.id.poster);
        String url = MoviesContract.PICASSO_URL+movieData.getString("poster_path");
        Picasso.with(container.getContext())
                .load(url)
                .into(image);

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(movieData.getString("original_title"));
        TextView release_date = (TextView) rootView.findViewById(R.id.release_date);
        release_date.setText(movieData.getString("release_date"));
        TextView plot = (TextView) rootView.findViewById(R.id.plot);
        plot.setMovementMethod(new ScrollingMovementMethod());
        plot.setText(movieData.getString("overview"));

        final ImageButton fav_btn = (ImageButton)rootView.findViewById(R.id.fav_btn);
        if(db.exists(movieData.getString("original_title")))
            fav_btn.setImageResource(R.mipmap.favourite);
        else
            fav_btn.setImageResource(R.mipmap.unfavourite);
        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!btnClicked)
                {
                    fav_btn.setImageResource(R.mipmap.favourite);
                    db.addFavourite(movieData.getString("original_title"));
                    btnClicked = true;
                }
                else
                {
                    fav_btn.setImageResource(R.mipmap.unfavourite);
                    db.deleteFavourite(movieData.getString("original_title"));
                    btnClicked = false;
                }
            }
        });
        return rootView;
    }

//    public HashMap<String, String> getData(String position)
//    {
//        HashMap<String, String> m = (HashMap<String, String>)(new ImageAdapter()).getItem(Integer.parseInt(position));
//        return m;
//    }
}

