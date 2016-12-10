package com.example.android.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.movies.adapters.ReviewAdapter;
import com.example.android.movies.data.MoviesContract;
import com.example.android.movies.helpers.DataDisplay;
import com.example.android.movies.helpers.FetchData;
import com.example.android.movies.helpers.MovieReviewsParser;

import java.util.HashMap;

/**
 * Created by fatma on 12/2/2016.
 */

public class ReviewsFragment extends Fragment implements DataDisplay{
    private ListView reviews_list;
    private ReviewAdapter reviewAdapter;
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
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviews_list = (ListView)rootView.findViewById(R.id.reviews_list);
        HashMap<String,String>[] temp = new HashMap[0];
        reviewAdapter = new ReviewAdapter(getActivity(),temp);
        reviews_list.setAdapter(reviewAdapter);
        return rootView;
    }

    @Override
    public void display(HashMap<String, String>[] results) {
        reviewAdapter.add(results);
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        updateReviews();
    }

    public void updateReviews() {
        FetchData reviewsTask = new FetchData(getContext(), new MovieReviewsParser(), this, MoviesContract.MOVIE_DB_URL + movie_id + MoviesContract.MOVIE_DB_REVIEWS);
        reviewsTask.execute();
    }
}
