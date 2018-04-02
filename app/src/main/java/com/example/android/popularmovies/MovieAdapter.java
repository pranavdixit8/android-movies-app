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

    String[][] mMovieImages;

    MovieOnClickListener movieOnClickListener;

    interface MovieOnClickListener{

        void movieOnclick(String[] movieDetail);
    }

    MovieAdapter(MovieOnClickListener clickListener){
        movieOnClickListener = clickListener;
    }


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

        String movieImage = mMovieImages[position][1];
        Picasso.with(holder.mMovieImageView.getContext()).load(movieImage).into(holder.mMovieImageView);


    }

    @Override
    public int getItemCount() {
        if(mMovieImages==null)return 0;
        return mMovieImages.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mMovieImageView;


        public MovieViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.iv_movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            String[] movieDetail = mMovieImages[position];

            movieOnClickListener.movieOnclick(movieDetail);

        }
    }
}
