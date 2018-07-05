package com.trends.trending.service;

import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.model.youtube.Parent;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by USER on 3/4/2018.
 */

public interface VideoService {

    String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    String SEARCH = "search";
    String TRENDING = "videos";
    String PLAYLISTS = "playlists";
    String PLAYLIST_ITEMS = "playlistItems";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET(SEARCH)
    Call<SearchParent> searchResults(@Query("part") String part,
                                     @Query("q") String search,
                                     @Query("maxResults") int resultsPerPage,
                                     @Query("key") String apiKey);

    @GET(SEARCH)
    Call<SearchParent> channelVideos(@Query("part") String part,
                                     @Query("channelId") String channelId,
                                     @Query("maxResults") int resultsPerPage,
                                     @Query("order") String orderBy,
                                     @Query("key") String apiKey);

    @GET(TRENDING)
    Call<Parent> trendingVideos(@Query("part") String part,
                                @Query("chart") String chart,
                                @Query("regionCode") String code,
                                @Query("maxResults") int resultsPerPage,
                                @Query("key") String apiKey);


    @GET(PLAYLISTS)
    Call<Parent> channelPlaylists(@Query("part") String part,
                                  @Query("channelId") String channelId,
                                  @Query("maxResults") int resultsPerPage,
                                  @Query("key") String apiKey);


    @GET(PLAYLIST_ITEMS)
    Call<Parent> playlistsVideos(@Query("part") String part,
                                 @Query("playlistId") String playlistId,
                                 @Query("maxResults") int resultsPerPage,
                                 @Query("key") String apiKey);

    @GET
    @Streaming
    Call<ResponseBody> downloadVideo(@Url String videoUrl);

}
