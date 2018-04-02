package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.Utilities.JSONUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieOnClickListener {

    private static final String POPULAR_QUERY = "popular";
    private static final String RATED_QUERY = "top_rated";

    RecyclerView mRecylerView;
    MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecylerView = (RecyclerView) findViewById(R.id.movies_recyclerview);
        mMovieAdapter = new MovieAdapter(this);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);

        mRecylerView.setLayoutManager(layoutManager);

        mRecylerView.setAdapter(mMovieAdapter);

        loadMovieImages("popular");

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
        }
        return super.onOptionsItemSelected(item);
    }

    void loadMovieImages(String selection){
        URL url = NetworkUtils.buildURL(selection);
        new MovieTask().execute(url);
    }

    @Override
    public void movieOnclick(String[] movieDetail) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(Intent.EXTRA_TEXT,movieDetail);
        startActivity(intent);
    }


    class MovieTask extends AsyncTask<URL, Void,String[][]>{

        @Override
        protected String[][] doInBackground(URL... params) {
            URL url = params[0];


            try {
                String apiResponse = NetworkUtils.getResponseFromAPI(url);
                String[][] imagePaths = JSONUtils.getMovieDetailsFromJSON(MainActivity.this,apiResponse);
                return imagePaths;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[][] strings) {
            if(strings !=null){
                mMovieAdapter.mMovieImages= strings;
                mMovieAdapter.notifyDataSetChanged();

            }
        }
    }
}
