package com.trends.trending.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trends.trending.R;
import com.trends.trending.interfaces.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class VideoAdapter<T> extends RecyclerView.Adapter<VideoAdapter.PlanetViewHolder> {

    private ItemClickListener<T> clickListener;
    private int itemPosition;

    public abstract void onBindData(VideoAdapter.PlanetViewHolder holder, T val);

    private List<T> videoList;


    protected VideoAdapter(List<T> videoList, ItemClickListener<T> itemClickListener) {
        this.videoList = videoList;
        this.clickListener = itemClickListener;
        this.itemPosition = -1;
    }

    @NonNull
    @Override
    public VideoAdapter.PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoAdapter.PlanetViewHolder holder, int position) {
        onBindData(holder, videoList.get(position));

        /*
        // New feature share and download

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onShareClick(videoList.get(holder.getAdapterPosition()));
            }
        });
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onDownloadClick(videoList.get(holder.getAdapterPosition()));

            }
        });

        */

        holder.mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPosition = holder.getAdapterPosition();
                if (clickListener != null) clickListener.onPlayClick(videoList.get(itemPosition));
            }
        });

        holder.channelTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) clickListener.onChannelTitleClick(videoList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_image)
        public ImageView image;
        @BindView(R.id.video_title)
        public TextView title;
        @BindView(R.id.video_channel_title)
        public TextView channelTitle;
        @BindView(R.id.play_video)
        public ImageView mPlay;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}