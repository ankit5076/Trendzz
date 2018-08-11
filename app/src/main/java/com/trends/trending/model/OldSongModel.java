package com.trends.trending.model;

import com.google.gson.annotations.SerializedName;

public class OldSongModel {

    @SerializedName("name")
    private String songName;
    @SerializedName("singer")
    private String singer;
    @SerializedName("id")
    private String songYoutubeId;

    public OldSongModel(String songName, String singer, String songYoutubeId) {
        this.songName = songName;
        this.singer = singer;
        this.songYoutubeId = songYoutubeId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongYoutubeId() {
        return songYoutubeId;
    }

    public void setSongYoutubeId(String songYoutubeId) {
        this.songYoutubeId = songYoutubeId;
    }
}
