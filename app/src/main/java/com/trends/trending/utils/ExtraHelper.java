package com.trends.trending.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.trends.trending.model.youtube.VideoLink;

import java.util.ArrayList;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * Created by USER on 4/4/2018.
 */

public class ExtraHelper {
    public static final String PREFS_NAME = "TRENDING_APP";
    public static final String VIDEO_TYPE = "Video_Type";

    @SuppressLint("StaticFieldLeak")
    public void getYoutubeDownloadUrl(String youtubeLink, final Context context) {

        final VideoLink videoLink = new VideoLink();
        new YouTubeExtractor(context) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                //mainProgressBar.setVisibility(View.GONE);
                SharedPreferences settings;
                SharedPreferences.Editor editor;
                settings = context.getSharedPreferences(PREFS_NAME,
                        Context.MODE_PRIVATE);
                editor = settings.edit();

                if (ytFiles == null) {
                    videoLink.setYtFiles(null);
                    videoLink.setVideoMeta(null);
                    return;
                }
                Gson gson = new Gson();
                ArrayList<YtFile> ytFileArrayList = new ArrayList<>();
                videoLink.setVideoMeta(vMeta);
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    YtFile ytFile = ytFiles.get(itag);
                    ytFileArrayList.add(ytFile);
                }
                videoLink.setYtFiles(ytFileArrayList);
                String links = gson.toJson(videoLink);
                editor.putString(VIDEO_TYPE, links);
                editor.commit();
            }
        }.extract(youtubeLink, true, false);
    }

    public static boolean checkPermission(Context context){
        int result = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
