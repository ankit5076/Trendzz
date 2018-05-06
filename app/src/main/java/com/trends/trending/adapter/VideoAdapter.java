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
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.model.youtube.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public abstract class VideoAdapter<T> extends RecyclerView.Adapter<VideoAdapter.PlanetViewHolder> {

//    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(VideoAdapter.PlanetViewHolder holder, T val);

    List<T> videoList;

    public VideoAdapter(List<T> videoList, Context context) {
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
//        holder.image.setImageResource(R.drawable.akon_vevo);
        onBindData(holder, videoList.get(position));
//        Picasso.get()
//                .load(videoList.get(position).getSnippet().getThumbnails().getMedium().getUrl())
//                .resize(90, 70)
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.amit_bhadana)
//                .into(holder.image);
//        holder.title.setText(videoList.get(position).getSnippet().getTitle());
//        holder.channelTtile.setText(videoList.get(position).getSnippet().getChannelTitle());
//        Log.d("height", "onBindViewHolder: "+videoList.get(position).getSnippet().getThumbnails().getMedium().getHeight());
//        Log.d("width", "onBindViewHolder: "+videoList.get(position).getSnippet().getThumbnails().getMedium().getWidth());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView title;
        public TextView channelTtile;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.video_image);
            title= (TextView) itemView.findViewById(R.id.video_title);
            channelTtile= (TextView) itemView.findViewById(R.id.video_channel_title);
        }
    }
}