package com.trends.trending;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.trends.trending.model.youtube.Playlist;

import java.util.ArrayList;

/**
 * Created by ankit.a.vishwakarma on 28-Mar-18.
 */

public class MainActivity1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Playlist> planetList=new ArrayList();
    public static final String TAG = MainActivity1.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
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

        adapter = new PlanetAdapter(planetList,this);
        recyclerView.setAdapter(adapter);
    }
}
