package com.trends.trending.model.youtube;

/**
 * Created by ankit.a.vishwakarma on 02-Apr-18.
 */

public class Video {
    private String VideoTitle;
    private String VideoDesc;
    private int VideoImage;

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideotTitle(String videoTitle) {
        this.VideoTitle = VideoTitle;
    }

    public int getVideoImage() {
        return VideoImage;
    }

    public void setVideoImage(int playlistImage) {
        this.VideoImage = VideoImage;
    }

    public String getVideoDesc() {
        return VideoDesc;
    }

    public void setVideoTitle(String VideoTitle) {
        this.VideoDesc = VideoDesc;
    }
}
