package com.example.android.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.movies.adapters.TrailerAdapter;
import com.example.android.movies.data.MoviesContract;
import com.example.android.movies.helpers.DataDisplay;
import com.example.android.movies.helpers.FetchData;
import com.example.android.movies.helpers.MovieTrailersParser;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by fatma on 12/2/2016.
 */

public class TrailersFragment extends Fragment implements DataDisplay{
    private ListView trailers_list;
    private TrailerAdapter trailerAdapter;
    private String movie_id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        movie_id = getArguments().getString("movie_id");
        final View rootView = inflater.inflate(R.layout.fragment_trailers, container, false);
        trailers_list = (ListView)rootView.findViewById(R.id.trailers_list);
        trailers_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = trailerAdapter.getKey(position);
                Intent playIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+key));
                playIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                if (playIntent.resolveActivity(getContext().getPackageManager()) != null)
                    startActivity(Intent.createChooser(playIntent,null));
            }
        });
        HashMap<String,String>[] temp = new HashMap[0];
        trailerAdapter = new TrailerAdapter(getActivity(),temp);
        trailers_list.setAdapter(trailerAdapter);
        return rootView;
    }

    @Override
    public void display(HashMap<String, String>[] results) {
        trailerAdapter.add(results);
        trailerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        updateTrailers();
    }

    public void updateTrailers() {
        FetchData trailersTask = new FetchData(getContext(), new MovieTrailersParser(), this, MoviesContract.MOVIE_DB_URL + movie_id + MoviesContract.MOVIE_DB_TRAILERS);
        trailersTask.execute();
    }
}
