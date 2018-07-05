package com.trends.trending.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.trends.trending.R;
import com.trends.trending.utils.Tag;

/**
 * Created by ankit.a.vishwakarma on 5/8/2018.
 */

public class MainChipViewAdapter extends ChipViewAdapter {
    public MainChipViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutRes(int position) {
        return 0;
    }

    @Override
    public int getBackgroundColor(int position) {
        Tag tag = (Tag) getChip(position);

        switch (tag.getType()) {
            default:
                return 0;

            case 1:
                return Color.parseColor("#f44336");
            case 2:
                return Color.parseColor("#009688");
            case 3:
                return Color.parseColor("#3f51b5");

            case 4:
                return Color.parseColor("#4caf50");

            case 5:
                return Color.parseColor("#00bcd4");

            case 6:
                return Color.parseColor("#4caf50");

            case 7:
                return Color.parseColor("#3f51b5");

            case 8:
                return Color.parseColor("#009688");

        }
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return Color.parseColor("#ffffff");
    }

    @Override
    public int getBackgroundRes(int position) {
        return 0;
    }

    @Override
    public void onLayout(View view, int position) {
        Tag tag = (Tag) getChip(position);
        ((TextView) view.findViewById(android.R.id.text1)).setTextSize(20);
        view.findViewById(android.R.id.text1).setPadding(10,10,10,10);
        if (tag.getType() == 2)
            //((TextView) view.findViewById(android.R.id.text1)).setTextColor(getColor(R.color.blue));
        {}
        if (tag.getType() == 1){}
            //((TextView) view.findViewById(android.R.id.text1)).setTextColor(getColor(R.color.blue));
    }
}
