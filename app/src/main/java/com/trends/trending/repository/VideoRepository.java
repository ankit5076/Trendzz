package com.trends.trending.repository;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.trends.trending.model.youtube.Parent;
import com.trends.trending.service.VideoService;

import java.io.IOException;

import retrofit2.Call;

import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHART;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_COUNTRY_CODE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PART;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RESULTS_PER_PAGE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_CHANNEL_PLAYLIST;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_PLAYLIST_VIDEOS;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_TRENDING;

/**
 * Created by USER on 3/21/2018.
 */

public class VideoRepository extends IntentService {

    private static final String TAG = "VideoRepository";

    public VideoRepository() {
        super("Background service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        final ResultReceiver receiver;
        if (intent != null) {
            receiver = intent.getParcelableExtra(KEY_RECEIVER);
            String methodName = intent.getStringExtra(KEY_INTENT);
            Bundle bundle = new Bundle();
            switch (methodName)
            {
                case VAL_SEARCH:
                    String search = intent.getStringExtra(KEY_SEARCH);
                    Parent parent = getSearchResults(search);
                    bundle.putParcelable(KEY_PARENT,parent);
                    receiver.send(1,bundle);
                    break;

                case VAL_TRENDING:
                    Parent trendingParent = getTrendingVideos();
                    bundle.putParcelable(KEY_PARENT, trendingParent);
                    receiver.send(1,bundle);
                    break;

                case VAL_CHANNEL_PLAYLIST:
                    String channelId = intent.getStringExtra(KEY_CHANNEL_PLAYLIST_ID);
                    Parent channelParent = getChannelPlaylists(channelId);
                    bundle.putParcelable(KEY_PARENT, channelParent);
                    receiver.send(1,bundle);
                    break;

                case VAL_PLAYLIST_VIDEOS:
                    String playlistId = intent.getStringExtra(KEY_PLAYLIST_ID);
                    Parent playlistParent = getPlaylistVideos(playlistId);
                    bundle.putParcelable(KEY_PARENT, playlistParent);
                    receiver.send(1,bundle);
                    break;
            }

        }

    }

    public Parent getSearchResults(String search){
        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.searchResults
                (KEY_PART,search, KEY_RESULTS_PER_PAGE, KEY_API);
        return getResult(call);
    }

    public Parent getTrendingVideos(){

        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.trendingVideos
                (KEY_PART, KEY_CHART, KEY_COUNTRY_CODE, KEY_RESULTS_PER_PAGE, KEY_API);

//        call.enqueue(new Callback<Parent>() {
//            @Override
//            public void onResponse(Call<Parent> call, Response<Parent> response) {
//                parent = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Parent> call, Throwable t) {
//                parent = null;
//            }
//        });

        return getResult(call);
    }

    public Parent getChannelPlaylists(String channelId){

        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.channelPlaylists
                (KEY_PART, channelId, KEY_RESULTS_PER_PAGE, KEY_API);


        return getResult(call);
    }

    public Parent getPlaylistVideos(String playlistId){

        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.playlistsVideos
                (KEY_PART, playlistId, KEY_RESULTS_PER_PAGE, KEY_API);

        return getResult(call);
    }

    public Parent getResult(Call<Parent> call){
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
