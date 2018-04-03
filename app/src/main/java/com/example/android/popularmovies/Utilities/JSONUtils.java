package com.example.android.popularmovies.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pranav on 02-04-2018.
 */

public class JSONUtils {

    private static final String MOVIES_ARRAY = "results";
    private static final String MOVIE_IMAGE_KEY = "poster_path";
    private static final String MOVIE_TITLE_KEY = "title";
    private static final String MOVIE_OVERVIEW_KEY = "overview";
    private static final String MOVIE_DATE_KEY = "release_date";
    private static final String MOVIE_RATING_KEY = "vote_average";

    public static String[] getImagePathsFromJSON(String movieJsonString) throws JSONException {

//        String[] movieImages = null;

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray movieArray = movieJson.getJSONArray(MOVIES_ARRAY);
        String[] movieImages = new String[movieArray.length()];

        for(int i =0 ; i< movieArray.length();i++){

            JSONObject movie = movieArray.getJSONObject(i);

            String imagePath = movie.getString(MOVIE_IMAGE_KEY);

            imagePath = imagePath.split("/")[1];

            movieImages[i] = NetworkUtils.buildImagePath(imagePath);
        }
        return movieImages;

    }

    public static String[][] getMovieDetailsFromJSON(String movieJsonString) throws JSONException {

//        String[][] movieDetails = null;

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray movieArray = movieJson.getJSONArray(MOVIES_ARRAY);
        String[][] movieDetails = new String[movieArray.length()][5];

        for(int i =0 ; i< movieArray.length();i++){

            JSONObject movie = movieArray.getJSONObject(i);

            String[] movieDetail = new String[5];
            movieDetail[0] = movie.getString(MOVIE_TITLE_KEY);

            String image = movie.getString(MOVIE_IMAGE_KEY);
            image = image.split("/")[1];
            String imagePath = NetworkUtils.buildImagePath(image);
            movieDetail[1] = imagePath;

            movieDetail[2] = movie.getString(MOVIE_OVERVIEW_KEY);
            movieDetail[3] = String.valueOf(movie.getLong(MOVIE_RATING_KEY));
            movieDetail[4] = movie.getString(MOVIE_DATE_KEY);

            movieDetails[i] = movieDetail;

        }
        return movieDetails;

    }
}
