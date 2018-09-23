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

import static com.trends.trending.utils.Keys.VideoInfo.TAB_BE_YOUNICK;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_BHAJAN;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_BOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_HOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_MOTIVATION;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NEW_SONGS;
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
        arrayList.add(new VideoHomeModel(TAB_TRENDING, R.drawable.hot, "#F44336"));
        arrayList.add(new VideoHomeModel(TAB_NEW_SONGS, R.drawable.new1, "#9C27B0"));
        arrayList.add(new VideoHomeModel(TAB_BOLLYWOOD_TRAILER, R.drawable.trailer, "#3F51B5"));
        arrayList.add(new VideoHomeModel(TAB_HOLLYWOOD_TRAILER, R.drawable.trailer, "#009688"));
        arrayList.add(new VideoHomeModel(TAB_MOTIVATION, R.drawable.motivation, "#4CAF50"));
        arrayList.add(new VideoHomeModel(TAB_WEBSERIES, R.drawable.web_series, "#795548"));
        arrayList.add(new VideoHomeModel(TAB_TECHNOLOGY, R.drawable.bulb, "#607D8B"));
        arrayList.add(new VideoHomeModel(TAB_BHAJAN, R.drawable.bhajan_om, "#0A9B88"));
        arrayList.add(new VideoHomeModel(TAB_COMEDY, R.drawable.comedy, "#0A9B88"));

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
                openVideoActivity(TAB_TRENDING);
                break;
            case TAB_BOLLYWOOD_TRAILER:
                openVideoActivity(TAB_BOLLYWOOD_TRAILER);
                break;
            case TAB_HOLLYWOOD_TRAILER:
                openVideoActivity(TAB_HOLLYWOOD_TRAILER);
                break;
            case TAB_NEW_SONGS:
                openVideoActivity(TAB_NEW_SONGS);
                break;
            case TAB_MOTIVATION:
                openVideoActivity(TAB_MOTIVATION);
                break;
            case TAB_BHAJAN:
                openVideoActivity(TAB_BHAJAN);
                break;
            case TAB_COMEDY:
                openVideoActivity(TAB_COMEDY);
                break;
            case TAB_WEBSERIES:
                Intent web = new Intent(VideoHome.this, Channel.class);
                web.putExtra(TAB_NAME,TAB_WEBSERIES);
                startActivity(web);
                break;
            case TAB_TECHNOLOGY:
                openVideoActivity(TAB_TECHNOLOGY);
                break;
        }

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();

    }

    private void openVideoActivity(String tabName){
        Intent songs = new Intent(VideoHome.this, Video.class);
        songs.putExtra(TAB_NAME, tabName);
        startActivity(songs);
    }
}

