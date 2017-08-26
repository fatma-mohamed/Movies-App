package com.example.android.movies.helpers;

import com.example.android.movies.fragments.PostersFragment;
import com.example.android.movies.interfaces.DataParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Fatma on 19-Nov-16.
 */

public class MovieDetailsParser implements DataParser {
    @Override
    public HashMap<String, String>[] parse(String moviesJsonStr) throws JSONException {
        final String MOVIES_LIST = "results";

        final String POSTER_PATH = "poster_path";
        final String ORIGINAL_TITLE = "original_title";
        final String PLOT = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String POPULARITY = "popularity";
        final String ID = "id";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(MOVIES_LIST);

        int arraySize = moviesArray.length();
        HashMap<String,String> [] results = new HashMap[arraySize];
        for(int i=0;i<arraySize;++i)
        {
            String poster_path;
            String original_title;
            String plot;
            String user_rating;
            String release_date;
            String popularity;
            String id;

            JSONObject movie = moviesArray.getJSONObject(i);
            poster_path = movie.getString(POSTER_PATH);
            original_title = movie.getString(ORIGINAL_TITLE);
            plot = movie.getString(PLOT);
            user_rating = movie.getString(USER_RATING);
            release_date = movie.getString(RELEASE_DATE);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = df.parse(release_date);
                release_date = new SimpleDateFormat("dd-MM-yyyy").format(d);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            popularity = movie.getString(POPULARITY);
            id = movie.getString(ID);

            HashMap<String,String> movieData = new HashMap<>();
            movieData.put(POSTER_PATH,poster_path);
            movieData.put(ORIGINAL_TITLE,original_title);
            movieData.put(PLOT,plot);
            movieData.put(USER_RATING,user_rating);
            movieData.put(RELEASE_DATE,release_date);
            movieData.put(POPULARITY,popularity);
            movieData.put(ID,id);

            results[i]=movieData;
            PostersFragment.db.addMovie(movieData);
        }

//        for(HashMap<String,String> m:results)
//            Log.v("DATA",m.toString());
        return results;
    }
}
