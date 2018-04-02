package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.Utilities.JSONUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecylerView;
    MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecylerView = (RecyclerView) findViewById(R.id.movies_recyclerview);
        mMovieAdapter = new MovieAdapter();

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);

        mRecylerView.setLayoutManager(layoutManager);

        mRecylerView.setAdapter(mMovieAdapter);

        loadMovieImages("popular");

    }
    void loadMovieImages( String selection){
        URL url = NetworkUtils.buildURL(selection);
        new MovieTask().execute(url);
    }


    class MovieTask extends AsyncTask<URL, Void,String[]>{

        @Override
        protected String[] doInBackground(URL... params) {
            URL url = params[0];


            try {
                String apiResponse = NetworkUtils.getResponseFromAPI(url);
                String[] imagePaths = JSONUtils.getImagePathsFromJSON(MainActivity.this,apiResponse);
                return imagePaths;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if(strings !=null){
                mMovieAdapter.mMovieImages= strings;
                mMovieAdapter.notifyDataSetChanged();

            }
        }
    }
}
