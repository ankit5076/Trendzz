package com.trends.trending.model.youtube;

import com.google.gson.annotations.SerializedName;

public class ChannelTitle {

    @SerializedName("searchTitle")
    private String searchChannelTitle;
    @SerializedName("title")
    private String channelTitle;

    public ChannelTitle(String searchChannelTitle, String channelTitle) {
        this.searchChannelTitle = searchChannelTitle;
        this.channelTitle = channelTitle;
    }

    public String getSearchChannelTitle() {
        return searchChannelTitle;
    }

    public void setSearchChannelTitle(String searchChannelTitle) {
        this.searchChannelTitle = searchChannelTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
