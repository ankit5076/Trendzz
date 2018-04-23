package com.trends.trending.fragment;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

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

import java.util.ArrayList;

public class VideosFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Playlist> planetList=new ArrayList();
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


        Playlist p = new Playlist();
        p.setPlaylistTitle("Guru Mann Fitness");
        p.setPlaylistImage(R.drawable.guruman);
        planetList.add(p);
        Playlist p1 = new Playlist();
        p1.setPlaylistTitle("Mumbiker Nikhil");
        p1.setPlaylistImage(R.drawable.mumbaikernikhil);
        planetList.add(p1);
        Playlist p2 = new Playlist();
        p2.setPlaylistTitle("NickVisionMotivation");
        p2.setPlaylistImage(R.drawable.guruman);
        planetList.add(p2);
        Playlist p3 = new Playlist();
        p3.setPlaylistTitle("Health And Fitness");
        p3.setPlaylistImage(R.drawable.mumbaikernikhil);
        planetList.add(p3);
        Playlist p4 = new Playlist();
        p4.setPlaylistTitle("Guru Mann Fitness");
        p4.setPlaylistImage(R.drawable.guruman);
        planetList.add(p4);
        Playlist p5 = new Playlist();
        p5.setPlaylistTitle("Mumbiker Nikhil");
        p5.setPlaylistImage(R.drawable.mumbaikernikhil);
        planetList.add(p5);
        Playlist p6 = new Playlist();
        p6.setPlaylistTitle("Health And Fitness");
        p6.setPlaylistImage(R.drawable.guruman);
        planetList.add(p6);
        Playlist p7 = new Playlist();
        p7.setPlaylistTitle("Bikes");
        p7.setPlaylistImage(R.drawable.mumbaikernikhil);
        planetList.add(p7);

        adapter = new VideosAdapter(planetList,getContext());
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
    }

}
