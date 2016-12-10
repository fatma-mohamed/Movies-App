package com.example.android.movies.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Fatma on 25-Nov-16.
 */

public class MovieReviewsParser implements DataParser {
    @Override
    public HashMap<String, String>[] parse(String jsonStr) throws JSONException {
        final String REVIEWS_LIST = "results";
        final String ID = "id";
        final String AUTHOR = "author";
        final String CONTENT = "content";


        JSONObject reviewsJson = new JSONObject(jsonStr);
        JSONArray reviewsArray = reviewsJson.getJSONArray(REVIEWS_LIST);

        int arraySize = reviewsArray.length();
        HashMap<String,String> [] results = new HashMap[arraySize];
        for(int i=0;i<arraySize;++i)
        {
            String id;
            String author;
            String content;

            JSONObject review = reviewsArray.getJSONObject(i);
            id = review.getString(ID);
            author = review.getString(AUTHOR);
            content = review.getString(CONTENT);

            HashMap<String,String> reviewData = new HashMap<>();
            reviewData.put(ID,id);
            reviewData.put(AUTHOR,author);
            reviewData.put(CONTENT,content);

            results[i]=reviewData;
        }
        return results;
    }
}
