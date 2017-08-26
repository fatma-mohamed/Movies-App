package com.example.android.movies.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.movies.BuildConfig;
import com.example.android.movies.data.MoviesContract;
import com.example.android.movies.interfaces.DataDisplay;
import com.example.android.movies.interfaces.DataParser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Fatma on 19-Nov-16.
 */

public class FetchData extends AsyncTask<Void, Void, HashMap<String,String>[]>
{
    private final String LOG_TAG = this.getClass().getSimpleName();
    private final DataParser parser;
    private final DataDisplay fragment;
    private final String url;
    private final Context mContext;

    public FetchData(Context context, DataParser p, DataDisplay f, String url)
    {
        mContext = context;
        parser = p;
        fragment = f;
        this.url = url;
    }


    @Override
    protected HashMap<String,String>[] doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr = null;
        try
        {
            Uri builtUri = Uri.parse(url).buildUpon()
                    .appendQueryParameter(MoviesContract.API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.e(LOG_TAG,"URL built!");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            Log.e(LOG_TAG,inputStream.toString());

            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,e.toString());
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e(LOG_TAG,e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG,e.toString());
            e.printStackTrace();
        } finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return parser.parse(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG,e.getLocalizedMessage(),e);
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(HashMap<String,String>[] results) {
       fragment.display(results);
    }

}
