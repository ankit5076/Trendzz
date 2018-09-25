package com.trends.trending.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trends.trending.R;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.ui.PlaylistVideo;
import com.trends.trending.utils.NetworkHelper;

import java.util.ArrayList;

import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_ID;
import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_OR_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.CURRENT_TITLE;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_TITLE;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlanetViewHolder> {

    private ArrayList<Playlist> planetList;
    private String mTitle;
    private Context mContext;
    private String[] playlistIds;
    private String[] channelIds;


    public PlaylistAdapter(ArrayList<Playlist> planetList, String title, Context context, String[] id, String[] cId) {
        this.planetList = planetList;
        this.mTitle = title;
        this.mContext = context;
        this.playlistIds = id;
        this.channelIds = cId;
    }

    @NonNull
    @Override
    public PlaylistAdapter.PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist,parent,false);
        return new PlanetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistAdapter.PlanetViewHolder holder, int position) {
        holder.image.setImageResource(planetList.get(position).getPlaylistImage());
        holder.channel.setText(planetList.get(position).getPlaylistTitle());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkHelper.hasNetworkAccess(mContext)) {
                    Intent intent = new Intent(mContext, PlaylistVideo.class);
                    intent.putExtra(CURRENT_TITLE, mTitle);
                    intent.putExtra(PLAYLIST_TITLE, planetList.get(holder.getAdapterPosition()).getPlaylistTitle());
                    intent.putExtra(CHANNEL_OR_PLAYLIST_ID, playlistIds[holder.getAdapterPosition()]);
                    intent.putExtra(CHANNEL_ID, channelIds[holder.getAdapterPosition()]);
                    mContext.startActivity(intent);
                }
                else
                {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView channel;
        RelativeLayout mParent;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.playlist_image);
            channel = itemView.findViewById(R.id.playlist_channel);
            mParent = itemView.findViewById(R.id.parent_item_playlist);
        }
    }
}