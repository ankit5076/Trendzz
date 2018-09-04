package com.trends.trending.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.trends.trending.R;
import com.trends.trending.adapter.VideoHomeAdapter;
import com.trends.trending.model.VideoHomeModel;
import com.trends.trending.utils.AutoFitGridLayoutManager;

import java.util.ArrayList;

import static com.trends.trending.utils.Keys.VideoInfo.TAB_BOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_HOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_REVIEWS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_SONGS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TECHNOLOGY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_WEBSERIES;

public class VideoHome extends AppCompatActivity implements VideoHomeAdapter.ItemListener {


    RecyclerView recyclerView;
    ArrayList<VideoHomeModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_home);


        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new VideoHomeModel(TAB_TRENDING, R.drawable.ic_b, "#F44336"));
        arrayList.add(new VideoHomeModel(TAB_SONGS, R.drawable.dummy_jetpack_joyride, "#9C27B0"));
        arrayList.add(new VideoHomeModel(TAB_BOLLYWOOD_TRAILER, R.drawable.dummy_jetpack_joyride, "#3F51B5"));
        arrayList.add(new VideoHomeModel(TAB_HOLLYWOOD_TRAILER, R.drawable.dummy_jetpack_joyride, "#009688"));
        arrayList.add(new VideoHomeModel(TAB_REVIEWS, R.drawable.dummy_jetpack_joyride, "#4CAF50"));
        arrayList.add(new VideoHomeModel(TAB_WEBSERIES, R.drawable.dummy_jetpack_joyride, "#795548"));
        arrayList.add(new VideoHomeModel(TAB_TECHNOLOGY, R.drawable.dummy_jetpack_joyride, "#607D8B"));
        arrayList.add(new VideoHomeModel("Item 6", R.drawable.dummy_jetpack_joyride, "#0A9B88"));

        VideoHomeAdapter adapter = new VideoHomeAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);


        /**
         Simple GridLayoutManager that spans two columns
         **/
        /*GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);*/
    }

    @Override
    public void onItemClick(VideoHomeModel item) {

        switch (item.text){
            case TAB_TRENDING:
                Intent i = new Intent(VideoHome.this, Video.class);
                i.putExtra(TAB_NAME,TAB_TRENDING);
                startActivity(i);
                break;
            case TAB_BOLLYWOOD_TRAILER:
                Intent i1 = new Intent(VideoHome.this, Video.class);
                i1.putExtra(TAB_NAME,TAB_BOLLYWOOD_TRAILER);
                startActivity(i1);
                break;
            case TAB_HOLLYWOOD_TRAILER:
                Intent holly = new Intent(VideoHome.this, Video.class);
                holly.putExtra(TAB_NAME,TAB_HOLLYWOOD_TRAILER);
                startActivity(holly);
                break;
            case TAB_SONGS:
                Intent songs = new Intent(VideoHome.this, Video.class);
                songs.putExtra(TAB_NAME,TAB_SONGS);
                startActivity(songs);
                break;
            case TAB_REVIEWS:
                break;
            case TAB_WEBSERIES:
                break;
            case TAB_TECHNOLOGY:
                break;
        }

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();

    }
}

