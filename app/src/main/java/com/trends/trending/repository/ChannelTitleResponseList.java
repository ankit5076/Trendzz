package com.trends.trending.repository;

import com.google.gson.annotations.SerializedName;
import com.trends.trending.model.youtube.ChannelTitle;

import java.util.List;

import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_TITLES;

public class ChannelTitleResponseList {

    @SerializedName(CHANNEL_TITLES)
    private List<ChannelTitle> mChannelTitles;

    public List<ChannelTitle> getChannelTitles() {
        return mChannelTitles;
    }

    public void setChannelTitles(List<ChannelTitle> channelTitles) {
        mChannelTitles = channelTitles;
    }
}
