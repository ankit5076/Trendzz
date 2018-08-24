package com.trends.trending.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.trends.trending.R;
import com.trends.trending.adapter.VideoHomeAdapter;
import com.trends.trending.model.VideoHomeModel;
import com.trends.trending.utils.AutoFitGridLayoutManager;

import java.util.ArrayList;

public class VideoHome extends AppCompatActivity implements VideoHomeAdapter.ItemListener {


    RecyclerView recyclerView;
    ArrayList<VideoHomeModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_home);


        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new VideoHomeModel("Item 1", R.drawable.ic_b, "#09A9FF"));
        arrayList.add(new VideoHomeModel("Item 2", R.drawable.dummy_jetpack_joyride, "#3E51B1"));
        arrayList.add(new VideoHomeModel("Item 3", R.drawable.dummy_jetpack_joyride, "#673BB7"));
        arrayList.add(new VideoHomeModel("Item 4", R.drawable.dummy_jetpack_joyride, "#4BAA50"));
        arrayList.add(new VideoHomeModel("Item 5", R.drawable.dummy_jetpack_joyride, "#F94336"));
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

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();

    }
}

