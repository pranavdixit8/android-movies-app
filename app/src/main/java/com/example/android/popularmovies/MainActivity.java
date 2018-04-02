package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecylerView;
        MovieAdapter mMovieAdapter;

        mRecylerView = (RecyclerView) findViewById(R.id.movies_recyclerview);
        mMovieAdapter = new MovieAdapter();

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);

        mRecylerView.setLayoutManager(layoutManager);

        mRecylerView.setAdapter(mMovieAdapter);

    }
}
