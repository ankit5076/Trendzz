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

import java.util.ArrayList;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.PlanetViewHolder> {

    ArrayList<Playlist> planetList;

    public VideosAdapter(ArrayList<Playlist> planetList, Context context) {
        this.planetList = planetList;
    }

    @Override
    public VideosAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideosAdapter.PlanetViewHolder holder, int position) {
        holder.image.setImageResource(planetList.get(position).getPlaylistImage());
        holder.text.setText(planetList.get(position).getPlaylistTitle());
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.image_id);
            text= (TextView) itemView.findViewById(R.id.text_id);
        }
    }
}