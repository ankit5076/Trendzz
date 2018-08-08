package com.trends.trending.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.adapter.SongAdapter;
import com.trends.trending.model.SongModel;
import com.trends.trending.repository.SongResponseList;
import com.trends.trending.utils.ExtraHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.TopTen.BOLLYWOOD;
import static com.trends.trending.utils.Keys.TopTen.HOLLYWOOD;
import static com.trends.trending.utils.Keys.TopTen.MOST_VIEWED;
import static com.trends.trending.utils.Keys.TopTen.OLD_IS_GOLD;
import static com.trends.trending.utils.Keys.TopTen.SONG_TYPE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;

public class TopTen extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.youtube_player)
    YouTubePlayerView mYouTubePlayerView;

    private ArrayList<SongModel> mSongModels = new ArrayList<>();
    private String videoId;
    private YouTubePlayer mYoutubePlayer;

    protected String getVideoId() {
        return videoId;
    }

    protected void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);
        ButterKnife.bind(this);
        setAdapter();
    }

    private void setAdapter() {
        Gson gson = new Gson();
        String jsonString = ExtraHelper.parseJson(TopTen.this, MOST_VIEWED);
        if (jsonString != null) {
            SongResponseList songResponseList = gson.fromJson(jsonString, SongResponseList.class);
            String name = getIntent().getStringExtra(SONG_TYPE);
            switch (name) {
                case BOLLYWOOD:
                    mSongModels.addAll(songResponseList.getBollywoodTopSongs());
                    break;
                case HOLLYWOOD:
                    mSongModels.addAll(songResponseList.getHollywoodTopSongs());
                    break;
                case OLD_IS_GOLD:
                    mSongModels.addAll(songResponseList.getBollywoodTopSongs());
                    break;
                default:
                    mSongModels.addAll(songResponseList.getBollywoodTopSongs());
                    break;
            }

        } else {
            mSongModels = null;
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        SongAdapter adapter = new SongAdapter(TopTen.this, mSongModels);
        adapter.SetOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, SongModel model) {
                setVideoId(model.getSongYoutubeId());
                if (mYoutubePlayer == null) {
                    mYouTubePlayerView.initialize(KEY_API, TopTen.this);
                } else {
                    mYoutubePlayer.loadVideo(getVideoId());
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        if (!wasRestored) {
            youTubePlayer.loadVideo(getVideoId());
            mYoutubePlayer = youTubePlayer;
            // Hiding player controls
//            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(KEY_API, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_player);
    }
}
