package com.example.android.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Data.FavouriteMoviesContract;
import com.example.android.popularmovies.Utilities.JSONUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;

import static com.example.android.popularmovies.Data.FavouriteMoviesContract.MovieItem.CONTENT_URI;

import static com.example.android.popularmovies.Data.FavouriteMoviesContract.MovieItem.MOVIE_POSTER;
import static com.example.android.popularmovies.Data.FavouriteMoviesContract.MovieItem.MOVIE_RATING;
import static com.example.android.popularmovies.Data.FavouriteMoviesContract.MovieItem.MOVIE_RELEASE_DATE;
import static com.example.android.popularmovies.Data.FavouriteMoviesContract.MovieItem.MOVIE_SYNOPSIS;
import static com.example.android.popularmovies.Data.FavouriteMoviesContract.MovieItem.MOVIE_TITLE;

public class DetailActivity extends AppCompatActivity implements DetailAdapter.DetailAdapterOnCLickHandler {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final String REVIEW_ADAPTER_TYPE = "review";
    private static final String VIDEO_ADAPTER_TYPE = "video";

    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mOverviewTextView;
    private TextView mRatingTextView;
    private TextView mReleaseDate;
    private CheckBox mCheckBox;

    private TextView mTrailerHeadTextView;
    private TextView mReviewHeadTextView;

    private String[] mDetail;
    private int mMovieId;
    private String videoKey;
    private boolean isFavourite = false;

    private RecyclerView mReviewRecyclerView;
    private DetailAdapter mReviewAdapter;
    private RecyclerView mVideoRecyclerView;
    private DetailAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mImageView = (ImageView) findViewById(R.id.iv_movie_thumbnail);
        mOverviewTextView = (TextView) findViewById(R.id.tv_movie_overview);
        mRatingTextView = (TextView) findViewById(R.id.tv_movie_rating);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mCheckBox = (CheckBox)findViewById(R.id.cb_favourite);

        mTrailerHeadTextView = (TextView) findViewById(R.id.tv_trailers_head);
        mReviewHeadTextView = (TextView) findViewById(R.id.tv_reviews_head);

        mReviewRecyclerView = (RecyclerView) findViewById(R.id.reviews_recyclerview);
        mVideoRecyclerView = (RecyclerView) findViewById(R.id.videos_recyclerview);

        mReviewAdapter = new DetailAdapter(REVIEW_ADAPTER_TYPE, this);
        mVideoAdapter = new DetailAdapter(VIDEO_ADAPTER_TYPE, this);

        Intent intent = getIntent();

        if(intent!=null){
            if(intent.hasExtra(Intent.EXTRA_TEXT)){
                mDetail = intent.getStringArrayExtra(Intent.EXTRA_TEXT);

                mMovieId = Integer.parseInt(mDetail[0]);

                new ReviewVideoTask().execute();

                Uri uri = ContentUris.withAppendedId(CONTENT_URI,mMovieId);
                Cursor cursor = getContentResolver().query(uri,
                        null,
                        null,
                        null,
                        null);

                if(cursor !=null && cursor.getCount()!=0){
                    isFavourite = true;
                }

                mTitleTextView.setText(mDetail[1]);
                Picasso.with(this).load(mDetail[2]).into(mImageView);
                mOverviewTextView.setText(mDetail[3]);
//rating
                String rating = mDetail[4];
                rating = rating+"/10";
                mRatingTextView.setText(rating);
//date
                String date = mDetail[5];
                date = date.split("-")[0];
                mReleaseDate.setText(date);

                mCheckBox.setChecked(isFavourite);


            }
        }

        mReviewHeadTextView.setText("Reviews: ");
        mTrailerHeadTextView.setText("Trailers: ");

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mReviewRecyclerView.setLayoutManager(layoutManager1);
        mReviewRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setLayoutManager(layoutManager2);
        mVideoRecyclerView.setHasFixedSize(true);


        mReviewRecyclerView.setAdapter(mReviewAdapter);
        mVideoRecyclerView.setAdapter(mVideoAdapter);

    }



    public void onClickAddFavouriteMovie(View view){


        if( mCheckBox.isChecked()){
            mCheckBox.setChecked(true);

            ContentValues contentValues = new ContentValues();
            Log.d(TAG, "I AM HERE 1:  " );

            contentValues.put(FavouriteMoviesContract.MovieItem._ID, mMovieId);
            contentValues.put(MOVIE_TITLE, mDetail[1]);
            contentValues.put(MOVIE_POSTER,mDetail[2]);
            contentValues.put(MOVIE_SYNOPSIS, mDetail[3]);
            contentValues.put(MOVIE_RATING,Long.parseLong(mDetail[4]));
            contentValues.put(MOVIE_RELEASE_DATE,mDetail[5]);

            Log.d(TAG, "I AM HERE 1:  " );

            Uri uri = getContentResolver().insert(FavouriteMoviesContract.MovieItem.CONTENT_URI, contentValues);
            Log.d(TAG, "onClickAddFavouriteMovie:  " + uri);
            if(uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }

        }
        else{
            mCheckBox.setChecked(false);
            String id = Integer.toString(mMovieId);
            Log.d(TAG, "onClickAddFavouriteMovie: I AM HERE  ");
            Uri uri = FavouriteMoviesContract.MovieItem.CONTENT_URI.buildUpon().appendPath(id).build();
            getBaseContext().getContentResolver().delete(uri,null,null);

        }
    }


    @Override
    public void onClick(String str) {
        Uri youtubeAppUri = Uri.parse("vnd.youtube:" + str);
        Uri webPageUri =  Uri.parse("http://www.youtube.com/watch?v=" + str);

        Intent appIntent = new Intent(Intent.ACTION_VIEW, youtubeAppUri);
        Intent webIntent = new Intent(Intent.ACTION_VIEW,webPageUri);

        if (appIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(appIntent);
        } else {
            startActivity(webIntent);
        }
    }


    class ReviewVideoTask extends AsyncTask<Void, Void, String[][]> {


        @Override
        protected String[][] doInBackground(Void... params) {

                String[][] reviewVideos = new String[2][];
            try {
                        URL reviewURL = NetworkUtils.buildReviewVideoURL(mMovieId,NetworkUtils.REVIEW_TOKEN);
                        String reviewResponse = NetworkUtils.getResponseFromAPI(reviewURL);
                        String[] reviews = JSONUtils.getMovieDetailsFromReviewJSON(reviewResponse);

                        if(reviews!=null) {
                            Log.d(TAG, "doInBackground: data " + mReviewAdapter.data);
                            Log.d(TAG, "doInBackground: reviewscount " + reviews.length);
                            reviewVideos[0] = reviews;
                        }
                        URL videoURL = NetworkUtils.buildReviewVideoURL(mMovieId,NetworkUtils.VIDEOS_TOKEN);
                        String videoResponse = NetworkUtils.getResponseFromAPI(videoURL);
                        String[] videos = JSONUtils.getMovieDetailsFromVideoJSON(videoResponse);

                        if(videos!=null) {
                            Log.d(TAG, "doInBackground: data " + mVideoAdapter.data);
                            Log.d(TAG, "doInBackground: videoscount" + videos.length);
                            reviewVideos[1] = videos;
                        }

                        return reviewVideos;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[][] strings) {
            if(strings != null) {
                mReviewAdapter.data = strings[0];
                mReviewAdapter.notifyDataSetChanged();

                mVideoAdapter.data = strings[1];
                mVideoAdapter.notifyDataSetChanged();
            }
        }
    }
}

