package com.trends.trending.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.adapter.PlaceAdapter;
import com.trends.trending.adapter.SongAdapter;
import com.trends.trending.model.PlaceToVisitModel;
import com.trends.trending.model.SongModel;
import com.trends.trending.model.youtube.TopTenModel;
import com.trends.trending.repository.PlaceResponseList;
import com.trends.trending.repository.SongResponseList;
import com.trends.trending.utils.ExtraHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.TopTen.MOST_VIEWED;
public class TopTen extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SongAdapter mAdapter;
    private ArrayList<SongModel> mSongModels = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bollywood");

        setAdapter();


    }

    private void setAdapter() {

        Gson gson = new Gson();
        String jsonString = ExtraHelper.parseJson(TopTen.this, MOST_VIEWED);
        if (jsonString != null) {
            SongResponseList songResponseList = gson.fromJson(jsonString, SongResponseList.class);
            mSongModels.addAll(songResponseList.getBollywoodTopSongs());
        } else {
            mSongModels = null;
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        mAdapter = new SongAdapter(TopTen.this, mSongModels);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        // dividerItemDecoration.setDrawable(ContextCompat.getDrawable(Place.this, R.drawable.divider_recyclerview));
        //recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mAdapter);


    }
}
