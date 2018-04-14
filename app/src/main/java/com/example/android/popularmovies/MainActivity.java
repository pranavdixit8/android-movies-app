package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.Data.FavouriteMoviesContract;
import com.example.android.popularmovies.Utilities.JSONUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieOnClickListener {

    private static final String POPULAR_QUERY = "popular";
    private static final String RATED_QUERY = "top_rated";
    private static final String FAVOURITE_QUERY = "favourite";

    private RecyclerView mRecylerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;



    String mSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading);
        mRecylerView = (RecyclerView) findViewById(R.id.movies_recyclerview);
        mMovieAdapter = new MovieAdapter(this);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);

        mRecylerView.setLayoutManager(layoutManager);

        mRecylerView.setAdapter(mMovieAdapter);

        showLoading();

        loadMovieImages(POPULAR_QUERY);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new MovieTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_popular){
            loadMovieImages(POPULAR_QUERY);
            return true;
        }else if(id == R.id.action_rated){
            loadMovieImages(RATED_QUERY);
            return true;
        }else if (id == R.id.action_favourites){
            loadMovieImages(FAVOURITE_QUERY);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovieImages(String selection){

        mSelection = selection;
        new MovieTask().execute();
    }

    private void showLoading() {

        mRecylerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showMovieImages(){

        mRecylerView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

    }

    @Override
    public void movieOnclick(String[] movieDetail) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Intent.EXTRA_TEXT,movieDetail);
        startActivity(intent);
    }


    class MovieTask extends AsyncTask<Void, Void,String[][]>{


        @Override
        protected String[][] doInBackground(Void... params) {

            String[][] movieDetails;

            try {

                switch (mSelection){

                    case POPULAR_QUERY:
                    case RATED_QUERY:
                        URL url = NetworkUtils.buildURL(mSelection);
                        String apiResponse = NetworkUtils.getResponseFromAPI(url);
                        movieDetails = JSONUtils.getMovieDetailsFromJSON(apiResponse);
                        return movieDetails;
                    case FAVOURITE_QUERY:
                        Cursor cursor = getContentResolver().query(FavouriteMoviesContract.MovieItem.CONTENT_URI,
                                null,
                                null,
                                null,
                                FavouriteMoviesContract.MovieItem.MOVIE_RATING);

                        movieDetails = JSONUtils.getMovieDetailsFromCursor(cursor);
                        return movieDetails;
                    default:
                        throw new UnsupportedOperationException("Selection not supported");

                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[][] strings) {
                mMovieAdapter.mMovieImages= strings;
                mMovieAdapter.notifyDataSetChanged();
                if(strings.length !=0 && strings!=null){
                    showMovieImages();
                }

        }
    }
}
