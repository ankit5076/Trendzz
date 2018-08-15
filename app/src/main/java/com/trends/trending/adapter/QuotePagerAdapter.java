package com.trends.trending.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.trends.trending.R;
import com.trends.trending.interfaces.QuoteRule;
import com.trends.trending.model.QuoteModel;

import java.util.ArrayList;
import java.util.List;

import static com.trends.trending.utils.Keys.Common.SHARE_INTENT_TITLE;

/**
 * Created by USER on 3/7/2018.
 */

public class QuotePagerAdapter extends PagerAdapter implements QuoteRule {

    private List<CardView> mViews;
    private List<QuoteModel> mData;
    private float mBaseElevation;
    private Context mContext;

    public QuotePagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public QuotePagerAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        this.mViews = new ArrayList<>();
    }

    public void addCardItem(QuoteModel item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_quote, container, false);
        container.addView(view);
        final QuoteModel quote = mData.get(position);
        bind(quote, view);
        CardView cardView =  view.findViewById(R.id.quoteCard);
        MaterialFancyButton fancyShare = view.findViewById(R.id.btn_share);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);

        fancyShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Quote");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(quote.getFamousQuote()+"\n<b>- By "+quote.getAuthorName()+"</b>"));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, quote.getFamousQuote()+"\n\t\t\t\t- By "+quote.getAuthorName());
                mContext.startActivity(Intent.createChooser(sharingIntent,SHARE_INTENT_TITLE));
                //Toast.makeText(mContext, quote.getAuthorName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final QuoteModel item, View view) {
        TextView authorName =  view.findViewById(R.id.authorName);
        final TextView quote = view.findViewById(R.id.quoteText);
        TextView uploadedBy = view.findViewById(R.id.uploadedBy);
        FrameLayout quoteFrame = view.findViewById(R.id.quoteFrame);
        AppCompatImageView mCopyBtn = view.findViewById(R.id.copy_image);

        MaterialFancyButton fancyShare = view.findViewById(R.id.btn_share);
        quoteFrame.setBackgroundColor(getRandomMaterialColor());
        ColorDrawable frameColor = (ColorDrawable) quoteFrame.getBackground();
        fancyShare.setBackgroundColor(frameColor.getColor());
        Resources res = mContext.getResources();
        authorName.setText(item.getAuthorName());
        quote.setText(res.getString(R.string.quote_msg, item.getFamousQuote()));
        uploadedBy.setText(String.format("- Uploaded By %s", item.getUploadedBy()));

        mCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("quote", item.getFamousQuote());
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.msg_quote_copied), Toast.LENGTH_SHORT).show();
                }
            }
        });
     }

    private int getRandomMaterialColor() {
        int returnColor = Color.GRAY;
        int arrayId = mContext.getResources().getIdentifier("quote_color", "array", mContext.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = mContext.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }


}
