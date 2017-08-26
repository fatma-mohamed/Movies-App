package com.example.android.movies.interfaces;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by Fatma on 19-Nov-16.
 */

public interface DataParser {
     <T> T parse(String jsonStr) throws JSONException;
}
