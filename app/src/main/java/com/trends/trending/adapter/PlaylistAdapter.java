package com.trends.trending.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trends.trending.R;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.ui.PlaylistVideo;

import java.util.ArrayList;

import butterknife.BindView;

import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_OR_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.CURRENT_TITLE;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_FITNESS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_WEBSERIES;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlanetViewHolder> {

    private ArrayList<Playlist> planetList;
    private String mTitle;
    private Context mContext;
    private String[] ids;


    public PlaylistAdapter(ArrayList<Playlist> planetList, String title, Context context, String[] id) {
        this.planetList = planetList;
        this.mTitle = title;
        this.mContext = context;
        this.ids = id;
    }

    @Override
    public PlaylistAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist,parent,false);
        return new PlanetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlaylistAdapter.PlanetViewHolder holder, int position) {
        holder.image.setImageResource(planetList.get(position).getPlaylistImage());
        holder.channel.setText(planetList.get(position).getPlaylistTitle());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, PlaylistVideo.class);
                intent.putExtra(CURRENT_TITLE, mTitle);
                intent.putExtra(CHANNEL_OR_PLAYLIST_ID, ids[holder.getAdapterPosition()]);
                mContext.startActivity(intent);

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