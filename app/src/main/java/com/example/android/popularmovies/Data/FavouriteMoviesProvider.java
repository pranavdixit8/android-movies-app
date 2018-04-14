package com.example.android.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavouriteMoviesProvider extends ContentProvider {


    public static final int ALL_MOVIES = 200;
    public static final int MOVIE_WITH_ID = 201;

    private static final UriMatcher mUriMatcher = buildUriMatcher();

    private FavouriteMoviesDBHelper mMoviesDBHelper;


    private static UriMatcher buildUriMatcher(){

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = FavouriteMoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FavouriteMoviesContract.PATH_FAVOURITES,ALL_MOVIES);
        matcher.addURI(authority,FavouriteMoviesContract.PATH_FAVOURITES + "/#" , MOVIE_WITH_ID);

        return matcher;


    }


    @Override
    public boolean onCreate() {

        mMoviesDBHelper = new FavouriteMoviesDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        SQLiteDatabase db = mMoviesDBHelper.getReadableDatabase();

        switch (mUriMatcher.match(uri)){


            case ALL_MOVIES:
                cursor = db.query(FavouriteMoviesContract.MovieItem.TABLE_NAME,
                                    projection,
                                    selection,
                                    selectionArgs,
                                    null,
                                    null,
                                    sortOrder
                        );
                break;


            case MOVIE_WITH_ID:
                String id = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{id};

                cursor = db.query(FavouriteMoviesContract.MovieItem.TABLE_NAME,
                        projection,
                        FavouriteMoviesContract.MovieItem._ID + " =? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;

            default:

                throw new UnsupportedOperationException("Unknown URI: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase db =  mMoviesDBHelper.getWritableDatabase();

        Uri returnUri;

        switch (mUriMatcher.match(uri)){

            case ALL_MOVIES:

                long id = db.insert(FavouriteMoviesContract.MovieItem.TABLE_NAME,null, values);
                if(id>0){
                    returnUri = ContentUris.withAppendedId(FavouriteMoviesContract.MovieItem.CONTENT_URI,id);
                }
                else throw new SQLException("Failed to insert row into: " + uri);

                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mMoviesDBHelper.getWritableDatabase();

        int numDeleted;

        switch (mUriMatcher.match(uri)){
            case MOVIE_WITH_ID:
                String id = uri.getLastPathSegment();
                String [] selectionArgument = new String[]{id};

                numDeleted = db.delete(FavouriteMoviesContract.MovieItem.TABLE_NAME, "_id = ?", selectionArgument);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: "+ uri);

        }

        if(numDeleted!=0){
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
