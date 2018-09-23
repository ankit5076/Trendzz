package com.trends.trending.interfaces;

/**
 * Created by USER on 5/12/2018.
 */

public interface ItemClickListener<T> {
//     void onShareClick(T item);
//     void onDownloadClick(T item);
    void onChannelTitleClick(T item);
    void onPlayClick(T item);
}
