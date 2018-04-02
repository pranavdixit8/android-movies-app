package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView mTitleTextView;
    ImageView mImageView;
    TextView mOverviewTextView;
    TextView mRatingTextView;
    TextView mReleaseDate;

    String[] mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mImageView = (ImageView) findViewById(R.id.iv_movie_thumbnail);
        mOverviewTextView = (TextView) findViewById(R.id.tv_movie_overview);
        mRatingTextView = (TextView) findViewById(R.id.tv_movie_rating);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);

        Intent intent = getIntent();

        if(intent!=null){
            if(intent.hasExtra(Intent.EXTRA_TEXT)){
                mDetail = intent.getStringArrayExtra(Intent.EXTRA_TEXT);

                mTitleTextView.setText(mDetail[0]);
                Picasso.with(this).load(mDetail[1]).into(mImageView);
                mOverviewTextView.setText(mDetail[2]);

                String rating = mDetail[3];

                rating = rating+"/10";

                mRatingTextView.setText(rating);

                String date = mDetail[4];
                date = date.split("-")[0];
                mReleaseDate.setText(date);

            }
        }
    }
}
