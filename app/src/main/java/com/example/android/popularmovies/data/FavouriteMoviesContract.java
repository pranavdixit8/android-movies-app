package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavouriteMoviesContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVOURITES = "favouriteMovies";

    public static final class MovieItem implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        public static final String TABLE_NAME ="favouriteMovies";

        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_POSTER = "poster";
        public static final String MOVIE_RATING = "rating";
        public static final String MOVIE_RELEASE_DATE = "release_date";
        public static final String MOVIE_SYNOPSIS = "overview";


    }
}
