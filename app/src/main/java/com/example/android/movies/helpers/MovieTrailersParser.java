package com.example.android.movies.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Fatma on 25-Nov-16.
 */

public class MovieTrailersParser implements DataParser {
    @Override
    public HashMap<String, String>[] parse(String jsonStr) throws JSONException {
        final String TRAILERS_LIST = "results";
        final String ID = "id";
        final String NAME = "name";
        final String KEY = "key";


        JSONObject trailersJson = new JSONObject(jsonStr);
        JSONArray trailersArray = trailersJson.getJSONArray(TRAILERS_LIST);

        int arraySize = trailersArray.length();
        HashMap<String,String> [] results = new HashMap[arraySize];
        for(int i=0;i<arraySize;++i)
        {
            String id;
            String name;
            String key;

            JSONObject trailer = trailersArray.getJSONObject(i);
            id = trailer.getString(ID);
            name = trailer.getString(NAME);
            key = trailer.getString(KEY);

            HashMap<String,String> trailerData = new HashMap<>();
            trailerData.put(ID,id);
            trailerData.put(NAME,name);
            trailerData.put(KEY,key);

            results[i]=trailerData;
        }
        return results;
    }
}
