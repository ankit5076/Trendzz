package com.trends.trending.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.trends.trending.R;
import com.trends.trending.model.SongModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongAdapter extends
        RecyclerView.Adapter {

    private static final String TAG = SongAdapter.class.getSimpleName();
    private static final int DATA_VIEW_TYPE = 0;
    private static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;

    private Context context;
    private List<Object> list = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private int spaceBetweenAds;


    public SongAdapter(Context context, List<SongModel> list) {
        this.context = context;
        this.list.addAll(list);
    }
    public SongAdapter(Context context, ArrayList<Object> list, int spaceBetweenAds) {
        this.context = context;
        this.list.addAll(list);
        this.spaceBetweenAds = spaceBetweenAds;
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
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), (SongModel) list.get(getAdapterPosition()));
                }
            });

        }

    }

    // View Holder for Admob Native Express Ad Unit
    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case DATA_VIEW_TYPE:
                View dataLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_top_ten, parent, false);
                ButterKnife.bind(this, dataLayoutView);
                return new ViewHolder(dataLayoutView);
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                // fall through
            default:
                View nativeExpressLayoutView = LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.card_native_express_ad_container,
                        parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
        }




//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View view = inflater.inflate(R.layout.item_top_ten, parent, false);
//        ButterKnife.bind(this, view);
//
//        ViewHolder viewHolder = new ViewHolder(view);
//
//        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        switch (viewType) {
            case DATA_VIEW_TYPE:
                ViewHolder dataViewHolder = (ViewHolder) holder;
                SongModel song = (SongModel) list.get(position);
                dataViewHolder.divider.setBackgroundColor(getRandomMaterialColor("400"));
                dataViewHolder.songName.setText(song.getSongName());
                dataViewHolder.movieName.setText(song.getMovieName());
//              holder.songViews.setText(context.getResources().getString(R.string.total_views, song.getSongViews()));
                dataViewHolder.songViews.setText(song.getSongViews());
                break;
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                // fall through
            default:
                NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder) holder;
                AdView adView = (AdView) list.get(position);
                ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;

                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }
                adCardView.addView(adView);
        }

//        SongModel song = list.get(position);
//        holder.divider.setBackgroundColor(getRandomMaterialColor("400"));
//        holder.songName.setText(song.getSongName());
//        holder.movieName.setText(song.getMovieName());
////        holder.songViews.setText(context.getResources().getString(R.string.total_views, song.getSongViews()));
//        holder.songViews.setText(song.getSongViews());

    }

    @Override
    public int getItemViewType(int position) {
        // Logic for returning view type based on spaceBetweenAds variable
        // Here if remainder after dividing the position with (spaceBetweenAds + 1) comes equal to spaceBetweenAds,
        // then return NATIVE_EXPRESS_AD_VIEW_TYPE otherwise DATA_VIEW_TYPE
        // By the logic defined below, an ad unit will be showed after every spaceBetweenAds numbers of data items
        return (position % (spaceBetweenAds + 1) == spaceBetweenAds) ? NATIVE_EXPRESS_AD_VIEW_TYPE: DATA_VIEW_TYPE;
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