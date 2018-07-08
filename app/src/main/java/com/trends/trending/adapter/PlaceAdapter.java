package com.trends.trending.adapter;

import android.content.Context;
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
import com.trends.trending.model.PlaceModel;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<PlaceModel> modelList;

    private OnItemClickListener mItemClickListener;


    public PlaceAdapter(Context context, ArrayList<PlaceModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<PlaceModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final PlaceModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
//            GradientDrawable bgShape = (GradientDrawable) genericViewHolder.rating.getBackground();
//            bgShape.setColor(getRandomMaterialColor("400"));
            genericViewHolder.itemTxtTitle.setText(model.getPlaceName());
            genericViewHolder.itemTxtMessage.setText(model.getPlaceInfo());
            genericViewHolder.rating.setText(model.getPlaceRating());
            genericViewHolder.placeRatingBar.setRating(Float.parseFloat(model.getPlaceRating()));
            genericViewHolder.placeRatingBar.setStepSize(0.1f);

            Picasso.get()
                    .load(model.getPlaceImage())
                    .fit()
                    .centerCrop()
                    //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.amit_bhadana)
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

    private PlaceModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, PlaceModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rating;
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;
        private ImageView placeImage;
        private RatingBar placeRatingBar;


        // @BindView(R.id.img_user)
        // ImageView imgUser;
        // @BindView(R.id.item_txt_title)
        // TextView itemTxtTitle;
        // @BindView(R.id.item_txt_message)
        // TextView itemTxtMessage;
        // @BindView(R.id.radio_list)
        // RadioButton itemTxtMessage;
        // @BindView(R.id.check_list)
        // CheckBox itemCheckList;
        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.rating =  itemView.findViewById(R.id.place_rating);
            this.itemTxtTitle = itemView.findViewById(R.id.item_txt_title);
            this.itemTxtMessage = itemView.findViewById(R.id.item_txt_message);
            this.placeImage = itemView.findViewById(R.id.place_image);
            this.placeRatingBar = itemView.findViewById(R.id.placeRatingBar);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

