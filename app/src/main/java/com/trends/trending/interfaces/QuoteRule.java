package com.trends.trending.interfaces;

import android.support.v7.widget.CardView;

/**
 * Created by USER on 3/7/2018.
 */

public interface QuoteRule {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
