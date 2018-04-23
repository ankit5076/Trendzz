package com.trends.trending.fragment;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.trends.trending.adapter.VideosAdapter;
import com.trends.trending.R;
import com.trends.trending.model.youtube.Playlist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Playlist> planetList = new ArrayList();
    private View view;

    public VideosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_activity, container, false);

        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        String[] musicTitle = getResources().getStringArray(R.array.music_chhanel_title);
        TypedArray musicImage = getResources().obtainTypedArray(R.array.music_chhanel_image);

        for (int i = 0; i < musicTitle.length; i++) {
            Playlist p = new Playlist();
            p.setPlaylistTitle(musicTitle[i]);
            p.setPlaylistImage(musicImage.getResourceId(i, -1));
            planetList.add(p);
        }

        adapter = new VideosAdapter(planetList, getContext());
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
    }

}
