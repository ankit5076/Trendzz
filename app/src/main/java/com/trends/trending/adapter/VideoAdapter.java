package com.trends.trending.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.trends.trending.R;
import com.trends.trending.interfaces.ItemClickListener;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.model.youtube.Video;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public abstract class VideoAdapter<T> extends RecyclerView.Adapter<VideoAdapter.PlanetViewHolder> {

    //    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);
    private ItemClickListener<T> clickListener;

    public abstract void onBindData(VideoAdapter.PlanetViewHolder holder, T val);

    private List<T> videoList;


    public VideoAdapter(List<T> videoList, ItemClickListener<T> itemClickListener) {
        this.videoList = videoList;
        this.clickListener = itemClickListener;
    }

    @Override
    public VideoAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.PlanetViewHolder holder, final int position) {
        onBindData(holder, videoList.get(position));
        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onShareClick(videoList.get(position));
            }
        });
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onDownloadClick(videoList.get(position));

            }
        });
        holder.mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onPlayClick(videoList.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView channelTtile;
        public ImageView mShare, mDownload, mPlay;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.video_image);
            title = itemView.findViewById(R.id.video_title);
            channelTtile = itemView.findViewById(R.id.video_channel_title);
            mShare = itemView.findViewById(R.id.share_video);
            mDownload = itemView.findViewById(R.id.download_video);
            mPlay = itemView.findViewById(R.id.play_video);

        }

    }
}