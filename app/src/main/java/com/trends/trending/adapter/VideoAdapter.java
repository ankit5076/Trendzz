package com.trends.trending.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trends.trending.R;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.model.youtube.Video;

import java.util.ArrayList;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.PlanetViewHolder> {

    ArrayList<Video> videoList;

    public VideoAdapter(ArrayList<Video> videoList, Context context) {
        this.videoList = videoList;
    }

    @Override
    public VideoAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.PlanetViewHolder holder, int position) {
        holder.image.setImageResource(videoList.get(position).getVideoImage());
        holder.title.setText(videoList.get(position).getVideoTitle());
        holder.desc.setText(videoList.get(position).getVideoDesc());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView title;
        protected TextView desc;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.video_image);
            title= (TextView) itemView.findViewById(R.id.video_channel);
            desc= (TextView) itemView.findViewById(R.id.video_desc);
        }
    }
}