package com.example.android.movies.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.android.movies.data.MoviesContract.FavouriteEntry;
import com.example.android.movies.data.MoviesContract.MovieEntry;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fatma on 25-Nov-16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context mContext;
    private static final int DATABASE_VERSION = 3;
    private static String LOG_TAG = DatabaseHelper.class.getSimpleName();

    static final String DATABASE_NAME = "movies.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " + FavouriteEntry.TABLE_NAME + " (" +
                FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouriteEntry.NAME + " STRING NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " STRING PRIMARY KEY," +
                MovieEntry.ORIGINAL_TITLE + " STRING NOT NULL," +
                MovieEntry.POSTER_PATH + " STRING NOT NULL," +
                MovieEntry.PLOT + " STRING NOT NULL," +
                MovieEntry.USER_RATING + " STRING NOT NULL," +
                MovieEntry.RELEASE_DATE + " STRING NOT NULL," +
                MovieEntry.POPULARITY + " STRING NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<String> getFavourites()
    {
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.FavouriteEntry.CONTENT_URI,
                new String[]{FavouriteEntry.NAME},
                null,
                null,
                null);
        ArrayList<String> arr = new ArrayList<>();
        if(!cursor.moveToFirst())
            return arr;
        do
        {
            arr.add(cursor.getString(cursor.getColumnIndex(FavouriteEntry.NAME)));
        }while (cursor.moveToNext());
        return arr;
    }

    public boolean exists(String movieName)
    {
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.FavouriteEntry.CONTENT_URI,
                new String[]{MoviesContract.FavouriteEntry._ID},
                MoviesContract.FavouriteEntry.NAME + " = ?",
                new String[]{movieName},
                null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;
    }

    public long addFavourite(String movieName)
    {
        long favId = -1;
        // First, check if movie is already in fav.
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.FavouriteEntry.CONTENT_URI,
                new String[]{MoviesContract.FavouriteEntry._ID},
                MoviesContract.FavouriteEntry.NAME + " = ?",
                new String[]{movieName},
                null);

        Log.d(LOG_TAG,"Cursor = " + cursor.moveToFirst());
        if(!cursor.moveToFirst())
        {
            Log.d(LOG_TAG,"Movie"+ movieName +" not in fav");
            ContentValues values = new ContentValues();
            values.put(MoviesContract.FavouriteEntry.NAME,movieName);

            Uri insertedUri = mContext.getContentResolver().insert(
                    MoviesContract.FavouriteEntry.CONTENT_URI,
                    values
            );
            favId = ContentUris.parseId(insertedUri);
            Log.d(LOG_TAG,"Movie inserted" + favId);
        }

        cursor.close();
        return favId;
    }

    public boolean deleteFavourite(String movieName)
    {
        int deletedRows = 0;
        // First, check if movie is already in fav.
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.FavouriteEntry.CONTENT_URI,
                new String[]{MoviesContract.FavouriteEntry._ID},
                MoviesContract.FavouriteEntry.NAME + " = ?",
                new String[]{movieName},
                null);

        Log.d(LOG_TAG,"Cursor = " + cursor.moveToFirst());
        if(cursor.moveToFirst())
        {
            Log.d(LOG_TAG,"Movie in fav");
            ContentValues values = new ContentValues();
            values.put(MoviesContract.FavouriteEntry.NAME,movieName);

            deletedRows = mContext.getContentResolver().delete(
                    MoviesContract.FavouriteEntry.CONTENT_URI,
                    MoviesContract.FavouriteEntry.NAME + " = ?",
                    new String[]{movieName}
            );
            Log.d(LOG_TAG,"Movie deleted" + deletedRows);
        }

        cursor.close();
        if(deletedRows>0)return true;
        else
            return false;
    }

    public long addMovie(HashMap<String,String> movie)
    {
        long movId = -1;
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                new String[]{MoviesContract.MovieEntry._ID},
                MovieEntry.ORIGINAL_TITLE + " = ?",
                new String[]{movie.get(MovieEntry.ORIGINAL_TITLE)},
                null);

        //Log.d(LOG_TAG,"Cursor = " + cursor.moveToFirst());
        if(!cursor.moveToFirst())
        {
            Log.d(LOG_TAG,"Movie"+ movie.get(MovieEntry.ORIGINAL_TITLE) +" not in db");
            ContentValues values = new ContentValues();
            values.put(MovieEntry._ID,movie.get("id"));
            values.put(MovieEntry.ORIGINAL_TITLE,movie.get("original_title"));
            values.put(MovieEntry.POSTER_PATH,movie.get("poster_path"));
            values.put(MovieEntry.PLOT,movie.get("overview"));
            values.put(MovieEntry.USER_RATING,movie.get("vote_average"));
            values.put(MovieEntry.RELEASE_DATE,movie.get("release_date"));
            values.put(MovieEntry.POPULARITY,movie.get("popularity"));

            Uri insertedUri = mContext.getContentResolver().insert(
                    MoviesContract.MovieEntry.CONTENT_URI,
                    values
            );
            movId = ContentUris.parseId(insertedUri);
            Log.d(LOG_TAG,"Movie inserted with ID = " + movId);
        }

        cursor.close();
        return movId;
    }

    public HashMap<String,String>[] getMovies()
    {
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                new String[]{MovieEntry._ID,MovieEntry.ORIGINAL_TITLE,MovieEntry.POSTER_PATH,
                        MovieEntry.PLOT,MovieEntry.USER_RATING,MovieEntry.RELEASE_DATE,MovieEntry.POPULARITY},
                null,
                null,
                null);
        HashMap<String,String>[] arr = new HashMap[cursor.getCount()];
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();++i){
            HashMap<String,String> m = new HashMap<>();
            m.put(MovieEntry._ID,cursor.getString(cursor.getColumnIndex("id")));
            m.put(MovieEntry.ORIGINAL_TITLE,cursor.getString(cursor.getColumnIndex("original_title")));
            m.put(MovieEntry.POSTER_PATH,cursor.getString(cursor.getColumnIndex("poster_path")));
            m.put(MovieEntry.PLOT,cursor.getString(cursor.getColumnIndex("overview")));
            m.put(MovieEntry.USER_RATING,cursor.getString(cursor.getColumnIndex("vote_average")));
            m.put(MovieEntry.RELEASE_DATE,cursor.getString(cursor.getColumnIndex("release_date")));
            m.put(MovieEntry.POPULARITY,cursor.getString(cursor.getColumnIndex("popularity")));
            arr[i] = m;
            cursor.moveToNext();
        }
        return arr;
    }


}
