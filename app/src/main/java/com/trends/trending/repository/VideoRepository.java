package com.trends.trending.repository;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.service.VideoService;

import java.io.IOException;

import retrofit2.Call;

import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_PART;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHART;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_COUNTRY_CODE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_METHOD;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_ORDER_BY;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PART;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.NUMBER_OF_VIDEOS_RETURNED;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_CHANNEL_PLAYLIST;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_CHANNEL_VIDEOS;
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
           // Log.d(TAG, "onHandleIntent:::: "+receiver);
            switch (methodName)
            {
                case VAL_SEARCH:
                    String search = intent.getStringExtra(KEY_SEARCH);
                    SearchParent parent = getSearchResults(search);
                    bundle.putParcelable(KEY_PARENT,parent);
                    bundle.putString(KEY_METHOD,VAL_SEARCH);
                    receiver.send(1,bundle);
                    break;

                case VAL_TRENDING:
                    Parent trendingParent = getTrendingVideos();
                    bundle.putParcelable(KEY_PARENT, trendingParent);
                    bundle.putString(KEY_METHOD,VAL_TRENDING);
                    receiver.send(1,bundle);
                    break;

                case VAL_CHANNEL_PLAYLIST:
                    String channelId = intent.getStringExtra(KEY_CHANNEL_PLAYLIST_ID);
                    Parent channelParent = getChannelPlaylists(channelId);
                    bundle.putParcelable(KEY_PARENT, channelParent);
                    bundle.putString(KEY_METHOD,VAL_CHANNEL_PLAYLIST);
                    receiver.send(1,bundle);
                    break;

                case VAL_PLAYLIST_VIDEOS:
                    String playlistId = intent.getStringExtra(KEY_CHANNEL_ID);
                    Parent playlistParent = getPlaylistVideos(playlistId);
                    bundle.putParcelable(KEY_PARENT, playlistParent);
                    bundle.putString(KEY_METHOD,VAL_PLAYLIST_VIDEOS);
                    receiver.send(1,bundle);
                    break;

                case VAL_CHANNEL_VIDEOS:
                    String mChannelId = intent.getStringExtra(KEY_CHANNEL_ID);
                    SearchParent channelVideoParent = getChannelVideos(mChannelId);
                    bundle.putParcelable(KEY_PARENT, channelVideoParent);
                    bundle.putString(KEY_METHOD,VAL_CHANNEL_VIDEOS);
                    receiver.send(1,bundle);
                    break;


                default:
                    break;
            }

        }

    }

    public SearchParent getSearchResults(String search){
        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<SearchParent> call = videoService.searchResults
                (KEY_PART,search, NUMBER_OF_VIDEOS_RETURNED, KEY_API);
        return getResult(call);
    }

    public SearchParent getChannelVideos(String channelId){
        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<SearchParent> call = videoService.channelVideos(KEY_CHANNEL_PART, channelId, NUMBER_OF_VIDEOS_RETURNED, KEY_ORDER_BY, KEY_API);
        return getResult(call);
    }

    public Parent getTrendingVideos(){

        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.trendingVideos
                (KEY_PART, KEY_CHART, KEY_COUNTRY_CODE, NUMBER_OF_VIDEOS_RETURNED, KEY_API);

//        call.enqueue(new Callback<SearchParent>() {
//            @Override
//            public void onResponse(Call<SearchParent> call, Response<SearchParent> response) {
//                parent = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<SearchParent> call, Throwable t) {
//                parent = null;
//            }
//        });

        return getResultTrending(call);
    }

    public Parent getChannelPlaylists(String channelId){

        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.channelPlaylists
                (KEY_PART, channelId, NUMBER_OF_VIDEOS_RETURNED, KEY_API);


        return getResultTrending(call);
    }

    public Parent getPlaylistVideos(String playlistId){

        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.playlistsVideos
                (KEY_PART, playlistId, NUMBER_OF_VIDEOS_RETURNED, KEY_API);

        return getResultTrending(call);
    }

    public SearchParent getResult(Call<SearchParent> call){
        try {
            //Log.d(TAG, "getResult: "+call.execute().body());
            return call.execute().body();
        } catch (IOException | IllegalStateException e) {
            Log.d(TAG, "getResultc: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // TODO: 4/3/2018 try to use generic template to optimize code
    public Parent getResultTrending(Call<Parent> call){
        try {
            //Log.d(TAG, "getResult: "+call.execute().body());
            return call.execute().body();
        } catch (IOException | IllegalStateException e) {
            Log.d(TAG, "getResultc: "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
