package com.example.android.movies.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by Fatma on 21-Oct-16.
 */

public class MoviesContract {
    public static final String MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/";
    public static final String MOVIE_DB_POPULAR = "popular";
    public static final String MOVIE_DB_REVIEWS = "/reviews";
    public static final String MOVIE_DB_TRAILERS = "/videos";
    public static final String API_KEY_PARAM = "api_key";

    public static final String  PICASSO_URL = "http://image.tmdb.org/t/p/w185/";

    /**************************************/

    public static final String CONTENT_AUTHORITY = "com.example.android.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public  static final String PATH_FAV = "favourites";
    public  static final String PATH_MOV = "movies";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    public static final class FavouriteEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(PATH_FAV).build();

        public static final String TABLE_NAME = "favourites";

        public static final String _ID = "fav_movie_id";
        public static final String NAME = "fav_movie_name";

        public static Uri buildFavouriteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(PATH_MOV).build();

        public static final String TABLE_NAME = "movies";

        public static final String _ID = "id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String POSTER_PATH = "poster_path";
        public static final String PLOT = "overview";
        public static final String USER_RATING = "vote_average";
        public static final String RELEASE_DATE = "release_date";
        public static final String POPULARITY = "popularity";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
