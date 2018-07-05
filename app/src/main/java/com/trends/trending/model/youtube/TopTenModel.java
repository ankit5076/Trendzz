package com.trends.trending.model.youtube;

import com.google.gson.annotations.SerializedName;

public class TopTenModel {

    private String name;
    @SerializedName("views")
    private String viewsInMillion;
    @SerializedName("id")
    private String videoId;

    public TopTenModel(String name, String viewsInMillion, String videoId) {
        this.name = name;
        this.viewsInMillion = viewsInMillion;
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViewsInMillion() {
        return viewsInMillion;
    }

    public void setViewsInMillion(String viewsInMillion) {
        this.viewsInMillion = viewsInMillion;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
