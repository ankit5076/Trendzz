package com.trends.trending.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.trends.trending.R;
import com.trends.trending.model.PlaceToVisitModel;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<PlaceToVisitModel> modelList;

    private OnItemClickListener mItemClickListener;


    public PlaceAdapter(Context context, ArrayList<PlaceToVisitModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<PlaceToVisitModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final PlaceToVisitModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
//            GradientDrawable bgShape = (GradientDrawable) genericViewHolder.rating.getBackground();
//            bgShape.setColor(getRandomMaterialColor("400"));
            genericViewHolder.itemTxtTitle.setText(model.getPlaceName());
            genericViewHolder.itemTxtMessage.setText(model.getPlaceState());
            genericViewHolder.rating.setText(model.getPlaceRating());
            genericViewHolder.placeRatingBar.setRating(Float.parseFloat(model.getPlaceRating()));
            genericViewHolder.placeRatingBar.setStepSize(0.1f);

            Picasso.get()
                    .load(model.getPlaceImageUrl())
                    .fit()
                    .centerCrop()
                    //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.sad_no_connection)
                    .into(genericViewHolder.placeImage);
        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private PlaceToVisitModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, PlaceToVisitModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.place_rating)
        TextView rating;
        @BindView(R.id.item_txt_title)
        TextView itemTxtTitle;
        @BindView(R.id.item_txt_message)
        TextView itemTxtMessage;
        @BindView(R.id.place_image)
        ImageView placeImage;
        @BindView(R.id.placeRatingBar)
        RatingBar placeRatingBar;


        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

        }
    }

}

