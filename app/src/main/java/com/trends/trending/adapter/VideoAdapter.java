package com.trends.trending.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trends.trending.R;
import com.trends.trending.interfaces.ItemClickListener;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.utils.RoundCornersTransformation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.PlanetViewHolder> {


    private static final int IMAGE_WIDTH = 74;
    private static final int IMAGE_HEIGHT = 90;
    private static final int IMAGE_CORNER_RADIUS = 12;

    private ItemClickListener clickListener;
    private int itemPosition;
    private List<Item> videoList;
    private Context mContext;
    private ValueAnimator colorAnimation;


    public VideoAdapter(List<Item> videoList, ItemClickListener itemClickListener, Context context) {
        this.videoList = videoList;
        this.clickListener = itemClickListener;
        this.itemPosition = -1;
        this.mContext = context;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    @NonNull
    @Override
    public VideoAdapter.PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new PlanetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoAdapter.PlanetViewHolder holder, final int position) {

        Item item = videoList.get(position);
        Picasso.get()
                .load(item.getSnippet().getThumbnails().getHigh().getUrl())
                .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                .transform(new RoundCornersTransformation(IMAGE_CORNER_RADIUS, 0, true, true))
                .placeholder(R.drawable.loading)
                .error(R.drawable.amit_bhadana)
                .into(holder.image);
        holder.title.setText(item.getSnippet().getTitle());
        holder.title.setSelected(true);
        holder.channelTitle.setText(item.getSnippet().getChannelTitle());

        if (itemPosition == position) {

            if (colorAnimation != null && colorAnimation.isRunning()) {
                colorAnimation.end();
            }

            int colorFrom = mContext.getResources().getColor(R.color.red);
            int colorTo = mContext.getResources().getColor(R.color.blue);

            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
            colorAnimation.setDuration(800);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    holder.vDivider.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
            holder.mPlay.setVisibility(View.INVISIBLE);
        } else {
            if (colorAnimation != null && colorAnimation.isRunning()) {
                colorAnimation.cancel();
            }
            holder.vDivider.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.mPlay.setVisibility(View.VISIBLE);
        }

        /*
        // New feature share and download

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onShareClick(videoList.get(holder.getAdapterPosition()));
            }
        });
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onDownloadClick(videoList.get(holder.getAdapterPosition()));

            }
        });

        */

        holder.mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemPosition != holder.getAdapterPosition()) {
                    notifyItemChanged(itemPosition);
                    itemPosition = holder.getAdapterPosition();
                    notifyItemChanged(itemPosition);
                    if (clickListener != null)
                        clickListener.onPlayClick(videoList.get(itemPosition));

                }
            }
        });

        holder.channelTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null)
                    clickListener.onChannelTitleClick(videoList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public class PlanetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_image)
        public ImageView image;
        @BindView(R.id.video_title)
        public TextView title;
        @BindView(R.id.video_channel_title)
        public TextView channelTitle;
        @BindView(R.id.play_video)
        public ImageView mPlay;
        @BindView(R.id.vDivider)
        public View vDivider;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}