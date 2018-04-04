package com.trends.trending;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trends.trending.model.youtube.Playlist;

import java.util.ArrayList;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder> {

    ArrayList<Playlist> planetList;

    public PlanetAdapter(ArrayList<Playlist> planetList, Context context) {
        this.planetList = planetList;
    }

    @Override
    public PlanetAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlanetAdapter.PlanetViewHolder holder, int position) {
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