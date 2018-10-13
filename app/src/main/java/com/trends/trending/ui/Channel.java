package com.trends.trending.ui;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.trends.trending.R;
import com.trends.trending.adapter.PlaylistAdapter;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.utils.RecyclerDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.VideoInfo.TAB_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_VINES;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_WEBSERIES;

public class Channel extends AppCompatActivity {
    
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<Playlist> channelList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_channel);
        ButterKnife.bind(this);
        setRecyclerView();

    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Channel.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerDividerItemDecoration(Channel.this));
        Bundle bundle = getIntent().getExtras();
        String title;

        if (bundle != null) {
            title = bundle.getString(TAB_NAME, TAB_WEBSERIES);
        }
        else 
            title = "";

        switch (title) {
            case TAB_VINES:
                //playListDataSet(R.array.vine_chhanel_title, R.array.vine_chhanel_id, R.array.vine_chhanel_image, title);
                break;

            case TAB_WEBSERIES:
                playListDataSet(R.array.web_series_playlist_title, R.array.web_series_playlist_id,
                        R.array.web_series_channel_id, R.array.web_series_playlist_image, title);
                break;

            case TAB_COMEDY:
                //playListDataSet(R.array.standup_comedy_chhanel_title, R.array.standup_comedy_chhanel_id, R.array.standup_comedy_chhanel_image, title);
                break;
        }
    }

    private void playListDataSet(int channelTitle, int playlistId, int channelId, int channelImage, String title){
        String[] cTitle = getResources().getStringArray(channelTitle);
        String[] pId = getResources().getStringArray(playlistId);
        String[] cId = getResources().getStringArray(channelId);
        TypedArray cImage = getResources().obtainTypedArray(channelImage);
        for (int i = 0; i < cTitle.length; i++) {
            Playlist p = new Playlist();
            p.setPlaylistTitle(cTitle[i]);
            p.setPlaylistImage(cImage.getResourceId(i, -1));
            channelList.add(p);
        }
        RecyclerView.Adapter vineAdapter = new PlaylistAdapter(channelList, title, Channel.this, pId, cId);
        recyclerView.setAdapter(vineAdapter);

    }
}
