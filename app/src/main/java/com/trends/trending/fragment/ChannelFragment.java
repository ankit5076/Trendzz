package com.trends.trending.fragment;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trends.trending.R;
import com.trends.trending.adapter.PlaylistAdapter;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.service.ReturnReceiver;
import com.trends.trending.utils.RecyclerDividerItemDecoration;

import java.util.ArrayList;

import static com.trends.trending.utils.Keys.VideoInfo.TAB_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_FITNESS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_MUSIC;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NEWS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TECHNOLOGY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_VINES;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_WEBSERIES;

public class ChannelFragment extends Fragment {

    private ArrayList<Playlist> channelList = new ArrayList();
    private View view;

    ReturnReceiver mReturnReceiver;

    public ChannelFragment() {
        // Required empty public constructor
    }


    public static ChannelFragment newInstance(String title) {

        Bundle args = new Bundle();

        ChannelFragment fragment = new ChannelFragment();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_channel, container, false);
        mReturnReceiver = new ReturnReceiver(new Handler());
        mReturnReceiver.setReceiver((ReturnReceiver.Receiver) getContext());
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerDividerItemDecoration(getContext()));

        channelList.clear();
        String title = getTitle();
        switch (title) {
            case TAB_MUSIC:
                String[] musicTitle = getResources().getStringArray(R.array.music_chhanel_title);
                String[] musicChannelId = getResources().getStringArray(R.array.music_chhanel_id);
                TypedArray musicImage = getResources().obtainTypedArray(R.array.music_chhanel_image);
                for (int i = 0; i < musicTitle.length; i++) {
                    Playlist p = new Playlist();
                    p.setPlaylistTitle(musicTitle[i]);
                    p.setPlaylistImage(musicImage.getResourceId(i, -1));
                    channelList.add(p);
                }
                RecyclerView.Adapter musicAdapter = new PlaylistAdapter(channelList, title, getContext(), musicChannelId);
                recyclerView.setAdapter(musicAdapter);
                break;
            case TAB_FITNESS:
                String[] fitnessTitle = getResources().getStringArray(R.array.fitness_playlist_title);
                String[] fitnessPlaylistId = getResources().getStringArray(R.array.fitness_playlist_id);
                TypedArray fitnessImage = getResources().obtainTypedArray(R.array.fitness_playlist_image);
                for (int i = 0; i < fitnessTitle.length; i++) {
                    Playlist p = new Playlist();
                    p.setPlaylistTitle(fitnessTitle[i]);
                    p.setPlaylistImage(fitnessImage.getResourceId(i, -1));
                    channelList.add(p);
                }
                RecyclerView.Adapter fitnessAdapter = new PlaylistAdapter(channelList, title, getContext(), fitnessPlaylistId);
                recyclerView.setAdapter(fitnessAdapter);
                break;

            case TAB_VINES:
                String[] vineTitle = getResources().getStringArray(R.array.vine_chhanel_title);
                String[] vineChannelId = getResources().getStringArray(R.array.vine_chhanel_id);
                TypedArray vineImage = getResources().obtainTypedArray(R.array.vine_chhanel_image);
                for (int i = 0; i < vineTitle.length; i++) {
                    Playlist p = new Playlist();
                    p.setPlaylistTitle(vineTitle[i]);
                    p.setPlaylistImage(vineImage.getResourceId(i, -1));
                    channelList.add(p);
                }
                RecyclerView.Adapter vineAdapter = new PlaylistAdapter(channelList, title, getContext(), vineChannelId);
                recyclerView.setAdapter(vineAdapter);
                break;

            case TAB_WEBSERIES:
                String[] webSeriesTitle = getResources().getStringArray(R.array.web_series_playlist_title);
                String[] webSeriesPlaylistId = getResources().getStringArray(R.array.web_series_playlist_id);
                TypedArray webSeriesImage = getResources().obtainTypedArray(R.array.web_series_playlist_image);
                for (int i = 0; i < webSeriesTitle.length; i++) {
                    Playlist p = new Playlist();
                    p.setPlaylistTitle(webSeriesTitle[i]);
                    p.setPlaylistImage(webSeriesImage.getResourceId(i, -1));
                    channelList.add(p);
                }
                RecyclerView.Adapter webSeriesAdapter = new PlaylistAdapter(channelList, title, getContext(), webSeriesPlaylistId);
                recyclerView.setAdapter(webSeriesAdapter);
                break;

            case TAB_COMEDY:
                String[] comedyTitle = getResources().getStringArray(R.array.standup_comedy_chhanel_title);
                String[] comedyChannelId = getResources().getStringArray(R.array.standup_comedy_chhanel_id);
                TypedArray comedyImage = getResources().obtainTypedArray(R.array.standup_comedy_chhanel_image);
                for (int i = 0; i < comedyTitle.length; i++) {
                    Playlist p = new Playlist();
                    p.setPlaylistTitle(comedyTitle[i]);
                    p.setPlaylistImage(comedyImage.getResourceId(i, -1));
                    channelList.add(p);
                }
                RecyclerView.Adapter comedyAdapter = new PlaylistAdapter(channelList, title, getContext(), comedyChannelId);
                recyclerView.setAdapter(comedyAdapter);
                break;

            case TAB_NEWS:
                String[] newsTitle = getResources().getStringArray(R.array.news_chhanel_title);
                String[] newsChannelId = getResources().getStringArray(R.array.news_chhanel_id);
                TypedArray newsImage = getResources().obtainTypedArray(R.array.news_chhanel_image);
                for (int i = 0; i < newsTitle.length; i++) {
                    Playlist p = new Playlist();
                    p.setPlaylistTitle(newsTitle[i]);
                    p.setPlaylistImage(newsImage.getResourceId(i, -1));
                    channelList.add(p);
                }
                RecyclerView.Adapter newsAdapter = new PlaylistAdapter(channelList, title, getContext(), newsChannelId);
                recyclerView.setAdapter(newsAdapter);
                break;

            case TAB_TECHNOLOGY:
                String[] techTitle = getResources().getStringArray(R.array.technology_chhanel_title);
                String[] techChannelId = getResources().getStringArray(R.array.technology_chhanel_id);
                TypedArray techImage = getResources().obtainTypedArray(R.array.technology_chhanel_image);
                for (int i = 0; i < techTitle.length; i++) {
                    Playlist p = new Playlist();
                    p.setPlaylistTitle(techTitle[i]);
                    p.setPlaylistImage(techImage.getResourceId(i, -1));
                    channelList.add(p);
                }
                RecyclerView.Adapter techAdapter = new PlaylistAdapter(channelList, title, getContext(), techChannelId);
                recyclerView.setAdapter(techAdapter);
                break;
        }
    }

    private String getTitle() {
        Bundle args = getArguments();
        return args.getString("title", "NO TITLE FOUND");
    }


}
