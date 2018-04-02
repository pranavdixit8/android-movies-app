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

    public static String[][] getMovieDetailsFromJSON(Context context, String movieJsonString) throws JSONException {

        String[][] movieDetails = null;

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray movieArray = movieJson.getJSONArray("results");
        movieDetails = new String[movieArray.length()][5];

        for(int i =0 ; i< movieArray.length();i++){

            JSONObject movie = movieArray.getJSONObject(i);

            String[] movieDetail = new String[5];
            movieDetail[0] = movie.getString("title");

            String image = movie.getString("poster_path");
            image = image.split("/")[1];
            String imagePath = NetworkUtils.buildImagePath(image);
            movieDetail[1] = imagePath;

            movieDetail[2] = movie.getString("overview");
            movieDetail[3] = String.valueOf(movie.getLong("vote_average"));
            movieDetail[4] = movie.getString("release_date");

            movieDetails[i] = movieDetail;

        }
        return movieDetails;

    }
}
