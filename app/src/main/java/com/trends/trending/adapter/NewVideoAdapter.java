package com.trends.trending.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
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
import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.model.youtube.ChannelTitle;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.repository.ChannelTitleResponseList;
import com.trends.trending.utils.ExtraHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.VideoInfo.JSON_CHANNEL_TITLE;

public class NewVideoAdapter extends
        RecyclerView.Adapter {

    private static final String TAG = NewVideoAdapter.class.getSimpleName();
    private static final int DATA_VIEW_TYPE = 0;
    private static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;

    private Context context;
    private List<Object> list = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private int spaceBetweenAds;
    private int itemPosition;
    private boolean isTrending;
    private ValueAnimator colorAnimation;


    public NewVideoAdapter(Context context, ArrayList<Object> list, int spaceBetweenAds, boolean trending) {
        this.context = context;
        this.list.addAll(list);
        this.spaceBetweenAds = spaceBetweenAds;
        this.isTrending = trending;
        this.itemPosition = -1;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public interface OnItemClickListener {

        void onOldItemClick(View view, int position, Item model);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vertical_divider)
        View divider;
        @BindView(R.id.horizontal_divider)
        View hDivider;
        @BindView(R.id.song_name)
        TextView title;
        @BindView(R.id.movie_name)
        TextView channelName;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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
                        R.layout.item_video_new, parent, false);
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
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case DATA_VIEW_TYPE:
                final ViewHolder dataViewHolder = (ViewHolder) holder;
                Item song = (Item) list.get(position);
                dataViewHolder.divider.setBackgroundColor(getRandomMaterialColor("400"));
                if (isTrending) {
                    dataViewHolder.title.setText(song.getSnippet().getTitle());
                    dataViewHolder.channelName.setText(song.getSnippet().getChannelTitle());
                } else {
                    String titleText = simplyfyTitle(song.getSnippet().getTitle());
                    dataViewHolder.title.setText(titleText);
                    String cnlText = checkTitle(song.getSnippet().getDescription().toLowerCase());
                    if (cnlText == null) {
                        dataViewHolder.channelName.setText(song.getSnippet().getDescription());
                        dataViewHolder.channelName.setSelected(true);
                    } else
                        dataViewHolder.channelName.setText(cnlText);
                }
                if(itemPosition == position){

                    if (colorAnimation!=null && colorAnimation.isRunning()) {
                        colorAnimation.end();
                    }

                    int colorFrom = context.getResources().getColor(R.color.red);
                    int colorTo = context.getResources().getColor(R.color.blue);

                    colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
                    colorAnimation.setDuration(800);
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            dataViewHolder.hDivider.setBackgroundColor((int) animator.getAnimatedValue());
                        }

                    });
                    colorAnimation.start();
                }
                else {
                    if (colorAnimation!=null && colorAnimation.isRunning()) {
                        colorAnimation.cancel();
                    }
                    dataViewHolder.hDivider.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                }

                dataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemPosition = dataViewHolder.getAdapterPosition();
                        notifyItemChanged(itemPosition);
                        mItemClickListener.onOldItemClick(dataViewHolder.itemView, itemPosition
                                , (Item) list.get(itemPosition));
                    }
                });
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
    }

    private String simplyfyTitle(String title) {

        title = title.replace("Offical Trailer: ", "");
        title = title.replace(" - Official Trailer", "");
        title = title.replace(" Official Trailer", "");
        title = title.replace(" Trailer", "");


        return title;
    }

    private String checkTitle(String description) {

        Gson gson = new Gson();
        String jsonString = ExtraHelper.parseJson(context, JSON_CHANNEL_TITLE);
        if (jsonString != null) {
            ChannelTitleResponseList channelTitleResponseList = gson.fromJson(jsonString, ChannelTitleResponseList.class);

            for (ChannelTitle channelTitle : channelTitleResponseList.getChannelTitles()) {

                if (description.contains(channelTitle.getSearchChannelTitle().toLowerCase()))
                    return channelTitle.getChannelTitle();

            }
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        return (position % (spaceBetweenAds + 1) == spaceBetweenAds) ? NATIVE_EXPRESS_AD_VIEW_TYPE : DATA_VIEW_TYPE;
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