package com.trends.trending.repository;

import com.google.gson.annotations.SerializedName;
import com.trends.trending.model.youtube.TopTenModel;

import java.util.List;

import static com.trends.trending.utils.Keys.VideoInfo.BOLLYWOOD_TOP_SONGS;

public class BollywoodSongsResponseList {
    @SerializedName(BOLLYWOOD_TOP_SONGS)
    private List<TopTenModel> bollywoodTopSongs;

    public List<TopTenModel> getBollywoodTopSongs() {
        return bollywoodTopSongs;
    }

    public void setBollywoodTopSongs(List<TopTenModel> bollywoodTopSongs) {
        this.bollywoodTopSongs = bollywoodTopSongs;
    }
}
