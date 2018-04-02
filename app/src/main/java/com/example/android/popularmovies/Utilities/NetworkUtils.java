package com.example.android.popularmovies.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by pranav on 02-04-2018.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = "YOUR API KEY";
    private  static final String IMAGE_PATH_BASE = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE_STRING = "w185";

    private static final String MOVIEDB_BASE= "http://api.themoviedb.org/3";
    private  static final String MOVIE_TOKEN = "movie";
    private static final String API_QUERY = "api_key";

    public static String buildImagePath(String imageName){

        Uri uri = Uri.parse(IMAGE_PATH_BASE).buildUpon()
                .appendPath(IMAGE_SIZE_STRING)
                .appendPath(imageName)
                .build();
        return uri.toString();
    }

    public static URL buildURL(String menuSelection){
        Uri uri = Uri.parse(MOVIEDB_BASE).buildUpon()
                .appendPath(MOVIE_TOKEN)
                .appendPath(menuSelection)
                .appendQueryParameter(API_QUERY, API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public static String getResponseFromAPI(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");

            boolean hasData = sc.hasNext();
            if (hasData) {
                return sc.next();
            } else {
                return null;
            }
        }
        finally{
            urlConnection.disconnect();
        }
    }
}
