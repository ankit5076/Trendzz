package com.trends.trending.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.trends.trending.model.youtube.VideoLink;

import java.io.IOException;
import java.io.InputStream;
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

    public static String parseJson(Context context, String jsonFile) {
        String json;
        try {
            InputStream inputStream = context.getAssets().open(jsonFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public static void bannerAdViewSetup(AdView bannerAd) {
        //mBannerAd = findViewById(R.id.adView);
//        mBannerAd.setAdSize(AdSize.BANNER);
//        mBannerAd.setAdUnitId(getString(R.string.banner_home_footer));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("54F2A2C6318B029B2338389DB10AFDBE")
                .build();

        bannerAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                //Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                //Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

        });

        bannerAd.loadAd(adRequest);
    }

}
