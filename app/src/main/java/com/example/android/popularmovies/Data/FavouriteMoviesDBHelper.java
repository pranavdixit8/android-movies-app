package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovies.Data.FavouriteMoviesContract.*;

public class FavouriteMoviesDBHelper extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "favourites.db";

    private static final int DATABASE_VERSION = 1;

    public FavouriteMoviesDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVOURITE_MOVIES_TABLE =  "CREATE TABLE " + MovieItem.TABLE_NAME +

        " (" + MovieItem._ID + " INTEGER PRIMARY KEY, " +
                MovieItem.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieItem.MOVIE_POSTER + " TEXT, " +
                MovieItem.MOVIE_SYNOPSIS + " TEXT, " +
                MovieItem.MOVIE_RATING + " REAL, " +
                MovieItem.MOVIE_RELEASE_DATE + " TEXT); ";

        db.execSQL(SQL_CREATE_FAVOURITE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ MovieItem.TABLE_NAME);
        onCreate(db);

    }
}
