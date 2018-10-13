package com.trends.trending.interfaces;

import com.trends.trending.model.youtube.Item;

/**
 * Created by USER on 5/12/2018.
 */

public interface ItemClickListener {
//     void onShareClick(T item);
//     void onDownloadClick(T item);
    void onChannelTitleClick(Item item);
    void onPlayClick(Item item);
}
