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

    public static String buildImagePath(String imageName){

        Uri uri = Uri.parse("http://image.tmdb.org/t/p/").buildUpon()
                .appendPath("w185")
                .appendPath(imageName)
                .build();
        return uri.toString();
    }

    public static URL buildURL(String menuSelection){
        Uri uri = Uri.parse("http://api.themoviedb.org/3").buildUpon()
                .appendPath("movie")
                .appendPath(menuSelection)
                .appendQueryParameter("api_key", API_KEY)
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
