package com.example.android.popularmovies.utilities;

import android.database.Cursor;

import com.example.android.popularmovies.data.FavouriteMoviesContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pranav on 02-04-2018.
 */

public class JSONUtils {

    private static final String MOVIE_ID = "id";
    private static final String MOVIES_ARRAY = "results";
    private static final String MOVIE_IMAGE_KEY = "poster_path";
    private static final String MOVIE_TITLE_KEY = "title";
    private static final String MOVIE_OVERVIEW_KEY = "overview";
    private static final String MOVIE_DATE_KEY = "release_date";
    private static final String MOVIE_RATING_KEY = "vote_average";

    private static final String MOVIE_REVIEW_AUTHOR = "author";
    private static final String MOVIE_REVEIW_CONTENT = "content";

    private static final String MOVIE_VIDEO_NAME = "name";
    private static final String MOVIE_VIDEO_KEY = "key" ;


//    public static String[] getImagePathsFromJSON(String movieJsonString) throws JSONException {
//
////        String[] movieImages = null;
//
//        JSONObject movieJson = new JSONObject(movieJsonString);
//        JSONArray movieArray = movieJson.getJSONArray(MOVIES_ARRAY);
//        String[] movieImages = new String[movieArray.length()];
//
//        for(int i =0 ; i< movieArray.length();i++){
//
//            JSONObject movie = movieArray.getJSONObject(i);
//
//            String imagePath = movie.getString(MOVIE_IMAGE_KEY);
//
//            imagePath = imagePath.split("/")[1];
//
//            movieImages[i] = NetworkUtils.buildImagePath(imagePath);
//        }
//        return movieImages;
//
//    }

    public static String[][] getMovieDetailsFromJSON(String movieJsonString) throws JSONException {

//        String[][] movieDetails = null;

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray movieArray = movieJson.getJSONArray(MOVIES_ARRAY);
        String[][] movieDetails = new String[movieArray.length()][6];

        for(int i =0 ; i< movieArray.length();i++){

            JSONObject movie = movieArray.getJSONObject(i);

            String[] movieDetail = new String[6];
            movieDetail[0] = String.valueOf(movie.getInt(MOVIE_ID));
            movieDetail[1] = movie.getString(MOVIE_TITLE_KEY);

            String image = movie.getString(MOVIE_IMAGE_KEY);
            image = image.split("/")[1];
            String imagePath = NetworkUtils.buildImagePath(image);
            movieDetail[2] = imagePath;

            movieDetail[3] = movie.getString(MOVIE_OVERVIEW_KEY);
            movieDetail[4] = String.valueOf(movie.getLong(MOVIE_RATING_KEY));
            movieDetail[5] = movie.getString(MOVIE_DATE_KEY);

            movieDetails[i] = movieDetail;

        }
        return movieDetails;

    }

    public static String[][] getMovieDetailsFromCursor(Cursor cursor) {

        if(cursor ==null){
            return null;
        }

        String[][] movieDetails = new String[cursor.getCount()][6];

        cursor.moveToFirst();
        int i=0;

        while(!cursor.isAfterLast()){

            String[] movieDetail = new String[6];
            movieDetail[0] = String.valueOf(cursor.getInt(cursor.getColumnIndex(FavouriteMoviesContract.MovieItem._ID)));
            movieDetail[1] = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.MovieItem.MOVIE_TITLE));

            movieDetail[2] = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.MovieItem.MOVIE_POSTER));

            movieDetail[3] = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.MovieItem.MOVIE_SYNOPSIS));
            movieDetail[4] = String.valueOf(cursor.getLong(cursor.getColumnIndex(FavouriteMoviesContract.MovieItem.MOVIE_RATING)));
            movieDetail[5] = cursor.getString(cursor.getColumnIndex(FavouriteMoviesContract.MovieItem.MOVIE_RELEASE_DATE));



            movieDetails[i] = movieDetail;
            i++;

            cursor.moveToNext();
        }
        cursor.close();

        return movieDetails;


    }

    public static String[] getMovieDetailsFromReviewJSON(String reviewResponse) throws JSONException {
        JSONObject reviewJson = new JSONObject(reviewResponse);
        JSONArray reviewArray = reviewJson.getJSONArray(MOVIES_ARRAY);

        String[] reviews = new String[reviewArray.length()] ;

        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);

            String author = review.getString(MOVIE_REVIEW_AUTHOR);
            String content = review.getString(MOVIE_REVEIW_CONTENT);

            String reviewContent = author + "####" + content;

            reviews[i] = reviewContent;

        }
        return reviews;

    }


    public static String[] getMovieDetailsFromVideoJSON(String videoResponse) throws JSONException {
        JSONObject videoJson = new JSONObject(videoResponse);
        JSONArray videoArray = videoJson.getJSONArray(MOVIES_ARRAY);

        String[] videos = new String[videoArray.length()] ;

        for (int i = 0; i < videoArray.length(); i++) {
            JSONObject video = videoArray.getJSONObject(i);

            String name = video.getString(MOVIE_VIDEO_NAME);
            String key = video.getString(MOVIE_VIDEO_KEY);

            String nameKey = name + "####" +key;


            videos[i] = nameKey;

        }
        return videos;

    }
}
