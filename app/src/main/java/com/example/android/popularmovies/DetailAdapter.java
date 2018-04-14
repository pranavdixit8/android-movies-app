package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder>{
    String[] data;
    private final String mType;
    private final DetailAdapterOnCLickHandler mClickHandler;

    DetailAdapter(String type, DetailAdapterOnCLickHandler onCLickHandler){
        this.mType =type;
        mClickHandler = onCLickHandler;
    }

    public interface DetailAdapterOnCLickHandler{

        void onClick(String str);
    }



    @Override
    public DetailAdapter.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdforItem = R.layout.detail_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdforItem,parent,shouldAttachToParent);

        DetailAdapter.DetailViewHolder viewHolder = new DetailAdapter.DetailViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailAdapter.DetailViewHolder holder, int position) {



        if(data==null || data.length ==0){

            holder.mErrorTextView.setText("No data, please check your connection");

            return;
        }
        String detail = data[position];

        Log.d(TAG, "onBindViewHolder: " + detail);
        if(mType.equals("review")){

            String author = detail.split("####")[0];
            String content = detail.split("####")[1];
            holder.mVideoLinearLayout.setVisibility(View.INVISIBLE);
            holder.mReviewLinearLayout.setVisibility(View.VISIBLE);
            holder.mAuthorTextView.setText(author);
            holder.mReviewTextView.setText(content);

        }
        else if( mType.equals("video")){

            String name = detail.split("####")[0];
            holder.mReviewLinearLayout.setVisibility(View.INVISIBLE);
            holder.mVideoLinearLayout.setVisibility(View.VISIBLE);
            holder.mVideoTextView.setText(name);


        }

    }

    @Override
    public int getItemCount() {
        if(data==null)return 0;
        Log.d(TAG, "getItemCount: " +mType + " Count:" + data.length);
        return data.length;
    }

    class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mVideoTextView;
        final LinearLayout mReviewLinearLayout;
        final LinearLayout mVideoLinearLayout;
        final TextView mAuthorTextView;
        final TextView mReviewTextView;
        final ImageButton mPlayImageView;
        final TextView mErrorTextView;


        DetailViewHolder(View itemView) {
            super(itemView);
            mVideoTextView = (TextView) itemView.findViewById(R.id.tv_videos);
            mVideoLinearLayout = (LinearLayout) itemView.findViewById(R.id.ll_videos);
            mReviewLinearLayout = (LinearLayout) itemView.findViewById(R.id.ll_reviews);
            mAuthorTextView= (TextView) itemView.findViewById(R.id.tv_author);
            mReviewTextView = (TextView) itemView.findViewById(R.id.tv_review_content);
            mErrorTextView = (TextView) itemView.findViewById(R.id.tv_detail_error_message);
            mPlayImageView = (ImageButton) itemView.findViewById(R.id.ib_play_button);

            mPlayImageView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int ind = getAdapterPosition();
            String key = data[ind].split("####")[1];

            mClickHandler.onClick(key);

        }
    }


}

