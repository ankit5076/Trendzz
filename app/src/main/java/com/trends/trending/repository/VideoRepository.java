package com.trends.trending.repository;

import android.util.Log;

import com.trends.trending.model.youtube.Parent;
import com.trends.trending.service.VideoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHART;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_COUNTRY_CODE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PART;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RESULTS_PER_PAGE;

/**
 * Created by USER on 3/21/2018.
 */

public class VideoRepository {

    public static final String TAG = "VideoRepository";
    //public Parent parent;
    public static Parent parent;
    public Parent getSearchResults(String search){
        //parent = null;
        //Parent parent1;
        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Parent> call = videoService.searchResults
                (KEY_PART,search, KEY_RESULTS_PER_PAGE, KEY_API);

        call.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                parent = response.body();
                Log.d(TAG, "onResponse: "+parent.getItems().get(0).getSnippet().getTitle());
                //Log.d("qwqwqw", "onResponse: "+ parent.getItems().get(0).getSnippet().getTitle());
             //   parent = parent;
            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                parent = null;
                Log.d("qwqwqw", "onFailure: "+ parent);
            }
        });

        Log.d("qwqwqw", "parent: "+ parent);
        return parent;
    }

//    public Parent getTrendingVideos(){
//
//        VideoService videoService = VideoService.retrofit.create(VideoService.class);
//        Call<Parent> call = videoService.trendingVideos
//                (KEY_PART, KEY_CHART, KEY_COUNTRY_CODE, KEY_RESULTS_PER_PAGE, KEY_API);
//
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
//
//        return parent;
//    }
//
//    public Parent getChannelPlaylists(String channelId){
//
//        VideoService videoService = VideoService.retrofit.create(VideoService.class);
//        Call<Parent> call = videoService.channelPlaylists
//                (KEY_PART, channelId, KEY_RESULTS_PER_PAGE, KEY_API);
//
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
//
//        return parent;
//    }
//
//
//    public Parent getPlaylistVideos(String playlistId){
//
//        VideoService videoService = VideoService.retrofit.create(VideoService.class);
//        Call<Parent> call = videoService.playlistsVideos
//                (KEY_PART, playlistId, KEY_RESULTS_PER_PAGE, KEY_API);
//
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
//
//        return parent;
//    }
//
}
