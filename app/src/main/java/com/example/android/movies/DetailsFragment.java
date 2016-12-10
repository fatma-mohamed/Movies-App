package com.example.android.movies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.android.movies.adapters.ImageAdapter;
import com.example.android.movies.data.DatabaseHelper;
import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;


import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment{
    private String movieStr;
    private HashMap<String, String> movieData;
    private boolean btnClicked = false;
    private FragmentTabHost host;
    private DatabaseHelper db;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        final View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        if(intent != null && intent.hasExtra("movie_name")) {
            db = PostersFragment.db;
            movieStr = intent.getStringExtra("movie_name");
            movieData = getData(movieStr);


            host = (FragmentTabHost) rootView.findViewById(R.id.tab_host);
            assert host != null;
            host.setup(getContext(),getFragmentManager(),R.id.realtabcontent);

            Bundle args = new Bundle();
            args.putString("movie_id",movieData.get("id"));
            //Reviews
            host.addTab(host.newTabSpec("Reviews").setIndicator("Reviews"),ReviewsFragment.class,args);
            //Trailers
            host.addTab(host.newTabSpec("Trailers").setIndicator("Trailers"),TrailersFragment.class,args);

            ImageView image = (ImageView) rootView.findViewById(R.id.poster);
            String url = MoviesContract.PICASSO_URL+movieData.get("poster_path");
            Picasso.with(container.getContext())
                    .load(url)
                    .into(image);

            TextView title = (TextView) rootView.findViewById(R.id.title);
            title.setText(movieData.get("original_title"));
            TextView release_date = (TextView) rootView.findViewById(R.id.release_date);
            release_date.setText(movieData.get("release_date"));
            TextView plot = (TextView) rootView.findViewById(R.id.plot);
            plot.setMovementMethod(new ScrollingMovementMethod());
            plot.setText(movieData.get("overview"));

            final ImageButton fav_btn = (ImageButton)rootView.findViewById(R.id.fav_btn);
            if(db.exists(movieData.get("original_title")))
                fav_btn.setImageResource(R.mipmap.favourite);
            else
                fav_btn.setImageResource(R.mipmap.unfavourite);
            fav_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!btnClicked)
                    {
                        fav_btn.setImageResource(R.mipmap.favourite);
                        db.addFavourite(movieData.get("original_title"));
                        btnClicked = true;
                    }
                    else
                    {
                        fav_btn.setImageResource(R.mipmap.unfavourite);
                        db.deleteFavourite(movieData.get("original_title"));
                        btnClicked = false;
                    }
                }
            });
        }
        return rootView;
    }

    public HashMap<String, String> getData(String position)
    {
        HashMap<String, String> m = (HashMap<String, String>)(new ImageAdapter()).getItem(Integer.parseInt(position));
        return m;
    }
}

