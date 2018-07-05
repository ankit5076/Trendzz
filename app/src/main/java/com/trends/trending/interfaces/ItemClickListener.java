package com.trends.trending.interfaces;

import android.view.View;

import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.SearchItem;

/**
 * Created by USER on 5/12/2018.
 */

public interface ItemClickListener<T> {
     void onShareClick(T item);
     void onDownloadClick(T item);
     void onPlayClick(T item);
}
