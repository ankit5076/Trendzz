package com.trends.trending.model;

import com.google.gson.annotations.SerializedName;

public class SongModel {

    @SerializedName("name")
    private String songName;
    @SerializedName("movie")
    private String movieName;
    @SerializedName("views")
    private String songViews;
    @SerializedName("id")
    private String songYoutubeId;

    public SongModel(String songName, String movieName, String songViews, String songYoutubeId) {
        this.songName = songName;
        this.movieName = movieName;
        this.songViews = songViews;
        this.songYoutubeId = songYoutubeId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getSongViews() {
        return songViews;
    }

    public void setSongViews(String songViews) {
        this.songViews = songViews;
    }

    public String getSongYoutubeId() {
        return songYoutubeId;
    }

    public void setSongYoutubeId(String songYoutubeId) {
        this.songYoutubeId = songYoutubeId;
    }
}
