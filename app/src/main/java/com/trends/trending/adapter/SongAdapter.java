package com.trends.trending.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.trends.trending.R;
import com.trends.trending.model.PlaceToVisitModel;
import com.trends.trending.model.SongModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongAdapter extends
        RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private static final String TAG = SongAdapter.class.getSimpleName();

    private Context context;
    private List<SongModel> list;
    private OnItemClickListener mItemClickListener;


    public SongAdapter(Context context, List<SongModel> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, SongModel model);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.vertical_divider)
        View divider;
        @BindView(R.id.song_name)
        TextView songName;
        @BindView(R.id.movie_name)
        TextView movieName;
        @BindView(R.id.views)
        TextView songViews;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_top_ten, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SongModel song = list.get(position);
        //GradientDrawable bgShape = (GradientDrawable) holder..getBackground();
        holder.divider.setBackgroundColor(getRandomMaterialColor("400"));
        holder.songName.setText(song.getSongName());
        holder.movieName.setText(song.getMovieName());
        holder.songViews.setText(song.getSongViews()+" Million");

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }
}