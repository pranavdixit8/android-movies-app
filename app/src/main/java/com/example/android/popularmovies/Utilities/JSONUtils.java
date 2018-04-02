package com.example.android.popularmovies.Utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pranav on 02-04-2018.
 */

public class JSONUtils {

    public static String[] getImagePathsFromJSON(Context context, String movieJsonString) throws JSONException {

        String[] movieImages = null;

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray movieArray = movieJson.getJSONArray("results");
        movieImages = new String[movieArray.length()];

        for(int i =0 ; i< movieArray.length();i++){

            JSONObject movie = movieArray.getJSONObject(i);

            String imagePath = movie.getString("poster_path");

            imagePath = imagePath.split("/")[1];

            movieImages[i] = NetworkUtils.buildImagePath(imagePath);
        }
        return movieImages;

    }
}
