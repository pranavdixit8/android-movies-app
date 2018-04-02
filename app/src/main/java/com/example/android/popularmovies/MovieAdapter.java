package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by pranav on 02-04-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    String[] mMovieImages = {"Movie1",
                                "Movie2",
                                "Movie3","Movie4", "Movie5", "Movie6", "Movie7", "Movie8",
            "Movie9","Movie10", "Movie11", "Movie12", "Movie13", "Movie14",
            "Movie15","Movie16", "Movie17", "Movie18", "Movie19", "Movie20"
                                };


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdforItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdforItem,parent,shouldAttachToParent);

        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        String movieImage = mMovieImages[position];
        Picasso.with(holder.mMovieImageView.getContext()).load(movieImage).into(holder.mMovieImageView);


    }

    @Override
    public int getItemCount() {
        if(mMovieImages==null)return 0;
        return mMovieImages.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView mMovieImageView;


        public MovieViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.iv_movie_image);
        }

    }
}
