package com.trends.trending.repository;

import com.google.gson.annotations.SerializedName;
import com.trends.trending.model.OldSongModel;
import com.trends.trending.model.SongModel;

import java.util.List;

import static com.trends.trending.utils.Keys.TopTen.BOLLYWOOD_TOP_SONGS;
import static com.trends.trending.utils.Keys.TopTen.HOLLYWOOD_TOP_SONGS;
import static com.trends.trending.utils.Keys.TopTen.POPULAR_OLD_SONGS;

public class SongResponseList {
    @SerializedName(BOLLYWOOD_TOP_SONGS)
    private List<SongModel> bollywoodTopSongs;
    @SerializedName(HOLLYWOOD_TOP_SONGS)
    private List<SongModel> hollywoodTopSongs;
    @SerializedName(POPULAR_OLD_SONGS)
    private List<OldSongModel> popularOldSongs;

    public List<OldSongModel> getPopularOldSongs() {
        return popularOldSongs;
    }

    public void setPopularOldSongs(List<OldSongModel> popularOldSongs) {
        this.popularOldSongs = popularOldSongs;
    }

    public List<SongModel> getHollywoodTopSongs() {
        return hollywoodTopSongs;
    }

    public void setHollywoodTopSongs(List<SongModel> hollywoodTopSongs) {
        this.hollywoodTopSongs = hollywoodTopSongs;
    }

    public List<SongModel> getBollywoodTopSongs() {
        return bollywoodTopSongs;
    }

    public void setBollywoodTopSongs(List<SongModel> bollywoodTopSongs) {
        this.bollywoodTopSongs = bollywoodTopSongs;
    }
}
