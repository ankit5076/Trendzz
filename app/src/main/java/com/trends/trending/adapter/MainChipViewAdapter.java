package com.trends.trending.adapter;

import android.content.Context;
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
        return 0;
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return 0;
    }

    @Override
    public int getBackgroundRes(int position) {
        return 0;
    }

    @Override
    public void onLayout(View view, int position) {
        Tag tag = (Tag) getChip(position);

        if (tag.getType() == 2)
            ((TextView) view.findViewById(android.R.id.text1)).setTextColor(getColor(R.color.blue));
    }
}
