package com.trends.trending.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.trends.trending.R;
import com.trends.trending.model.OldSongModel;
import com.trends.trending.model.SongModel;
import com.trends.trending.model.youtube.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.VideoInfo.AJAY_DEVGN;
import static com.trends.trending.utils.Keys.VideoInfo.MADDOCK_FILM;
import static com.trends.trending.utils.Keys.VideoInfo.MADDOCK_FILM_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.SALMAN_KHAN_FILM;
import static com.trends.trending.utils.Keys.VideoInfo.SAREGAMA_MUSIC;
import static com.trends.trending.utils.Keys.VideoInfo.SAREGAMA_MUSIC_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.TIPS_OFFICIAL;
import static com.trends.trending.utils.Keys.VideoInfo.TIPS_OFFICIAL_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.T_SERIES;
import static com.trends.trending.utils.Keys.VideoInfo.VEVO;
import static com.trends.trending.utils.Keys.VideoInfo.VEVO_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.YASH_RAJ_FILM;
import static com.trends.trending.utils.Keys.VideoInfo.ZEE_STUDIO;

public class NewVideoAdapter extends
        RecyclerView.Adapter {

    private static final String TAG = NewVideoAdapter.class.getSimpleName();
    private static final int DATA_VIEW_TYPE = 0;
    private static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;

    private Context context;
    private List<Object> list = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private int spaceBetweenAds;
    private boolean isTrending;

    public NewVideoAdapter(Context context, ArrayList<Object> list, int spaceBetweenAds, boolean trending) {
        this.context = context;
        this.list.addAll(list);
        this.spaceBetweenAds = spaceBetweenAds;
        this.isTrending = trending;
    }

    public interface OnItemClickListener {

        void onOldItemClick(View view, int position, Item model);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onOldItemClick(itemView, getAdapterPosition(), (Item) list.get(getAdapterPosition()));
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
                ViewHolder dataViewHolder = (ViewHolder) holder;

                Item song = (Item) list.get(position);
                dataViewHolder.divider.setBackgroundColor(getRandomMaterialColor("400"));
                if (isTrending) {
                    dataViewHolder.title.setText(song.getSnippet().getTitle());
                    dataViewHolder.channelName.setText(song.getSnippet().getChannelTitle());
                } else {
                    String titleText = simplyfyTitle(song.getSnippet().getTitle());
                    dataViewHolder.title.setText(titleText);
                    String cnlText = checkTitle(song.getSnippet().getDescription());
                    if (cnlText == null) {
                        dataViewHolder.channelName.setText(song.getSnippet().getDescription());
                        dataViewHolder.channelName.setSelected(true);
                    } else
                        dataViewHolder.channelName.setText(cnlText);
                }
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

        if (description.contains(ZEE_STUDIO))
            return ZEE_STUDIO;
        else if (description.contains(T_SERIES))
            return T_SERIES;
        else if (description.contains(VEVO))
            return VEVO_NAME;
        else if (description.contains(YASH_RAJ_FILM))
            return YASH_RAJ_FILM;
        else if (description.contains(TIPS_OFFICIAL))
            return TIPS_OFFICIAL_NAME;
        else if (description.contains(SALMAN_KHAN_FILM))
            return SALMAN_KHAN_FILM;
        else if (description.contains(MADDOCK_FILM))
            return MADDOCK_FILM_NAME;
        else if (description.contains(SAREGAMA_MUSIC))
            return SAREGAMA_MUSIC_NAME;
        else if (description.contains(AJAY_DEVGN))
            return AJAY_DEVGN;
        else
            return null;
    }


    @Override
    public int getItemViewType(int position) {
        // Logic for returning view type based on spaceBetweenAds variable
        // Here if remainder after dividing the position with (spaceBetweenAds + 1) comes equal to spaceBetweenAds,
        // then return NATIVE_EXPRESS_AD_VIEW_TYPE otherwise DATA_VIEW_TYPE
        // By the logic defined below, an ad unit will be showed after every spaceBetweenAds numbers of data items
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