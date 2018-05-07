package com.trends.trending.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trends.trending.DummyBottom;
import com.trends.trending.R;
import com.trends.trending.fragment.TrailerFragment;
import com.trends.trending.model.youtube.Playlist;

import java.util.ArrayList;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlanetViewHolder> {

    ArrayList<Playlist> planetList;
    Context mContext;

    public PlaylistAdapter(ArrayList<Playlist> planetList, Context context) {
        this.planetList = planetList;
    }

    @Override
    public PlaylistAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlaylistAdapter.PlanetViewHolder holder, int position) {
        holder.image.setImageResource(planetList.get(position).getPlaylistImage());
        holder.channel.setText(planetList.get(position).getPlaylistTitle());
       // FragmentManager fragmentManager = ((DummyBottom)mContext).getSupportFragmentManager();
        //VideoFragment videoFragment = new VideoFragment();
//        fragmentManager.beginTransaction()
//                .replace(R.id.frag, new TrailerFragment())
//                .commit();
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView channel;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.playlist_image);
            channel= (TextView) itemView.findViewById(R.id.playlist_channel);
        }
    }
}