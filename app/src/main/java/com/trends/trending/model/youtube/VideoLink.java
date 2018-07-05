package com.trends.trending.model.youtube;

import java.util.ArrayList;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YtFile;

/**
 * Created by USER on 4/7/2018.
 */

public class VideoLink {

    ArrayList<YtFile> mYtFiles;
    VideoMeta mVideoMeta;

    public VideoLink() {
        mYtFiles = new ArrayList<>();
    }

    public ArrayList<YtFile> getYtFiles() {
        return mYtFiles;
    }

    public void setYtFiles(ArrayList<YtFile> ytFiles) {
        mYtFiles = ytFiles;
    }

    public VideoMeta getVideoMeta() {
        return mVideoMeta;
    }

    public void setVideoMeta(VideoMeta videoMeta) {
        mVideoMeta = videoMeta;
    }
}
