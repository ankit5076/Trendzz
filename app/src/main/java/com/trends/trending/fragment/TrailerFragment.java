package com.trends.trending.fragment;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trends.trending.R;
import com.trends.trending.adapter.VideosAdapter;
import com.trends.trending.model.youtube.Playlist;

import java.util.ArrayList;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_TAB_TITLE;

public class TrailerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Playlist> planetList = new ArrayList();
    private View view;

    public TrailerFragment() {
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

        adapter = new VideosAdapter(planetList, getContext());
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
    }

}
