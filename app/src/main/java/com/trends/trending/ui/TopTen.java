package com.trends.trending.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.adapter.SongAdapter;
import com.trends.trending.model.OldSongModel;
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
    public final static int spaceBetweenAds = 3;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.youtube_player)
    YouTubePlayerView mYouTubePlayerView;


    private ArrayList<Object> mSongModels = new ArrayList<>();
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
                    mSongModels.addAll(songResponseList.getPopularOldSongs());
                    break;
                default:
                    mSongModels.addAll(songResponseList.getBollywoodTopSongs());
                    break;
            }

        } else {
            mSongModels = null;
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        addNativeExpressAds();
        SongAdapter adapter = new SongAdapter(TopTen.this, mSongModels, spaceBetweenAds);
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

            @Override
            public void onOldItemClick(View view, int position, OldSongModel model) {
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

    private void addNativeExpressAds() {

        // We are looping through our original dataset
        // And adding Admob's Native Express Ad at consecutive positions at a distance of spaceBetweenAds
        // You should change the spaceBetweenAds variable according to your need
        // i.e how often you want to show ad in RecyclerView

        for (int i = spaceBetweenAds; i <= mSongModels.size(); i += (spaceBetweenAds + 1)) {
            AdView adView = new AdView(this);
            // I have used a Test ID provided by Admob below
            // you should replace it with yours
            // And if wou are just experimenting, then just copy the code
            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            mSongModels.add(i, adView);
        }

        // Below we are using post on RecyclerView
        // because we want to resize our native ad's width equal to screen width
        // and we should do it after RecyclerView is created

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                float scale = TopTen.this.getResources().getDisplayMetrics().density;
                int adWidth = (int) (recyclerView.getWidth() - (2 * TopTen.this.getResources().getDimension(R.dimen.activity_horizontal_margin)));

                // we are setting size of adView
                // you should check admob's site for possible ads size
                AdSize adSize = new AdSize((int) (adWidth / scale), 60);

                // looping over mDataset to sesize every Native Express Ad to ew adSize
                for (int i = spaceBetweenAds; i <= mSongModels.size(); i += (spaceBetweenAds + 1)) {
                    AdView adViewToSize = (AdView) mSongModels.get(i);
                    adViewToSize.setAdSize(AdSize.BANNER);
                }

                // calling method to load native ads in their views one by one
                loadNativeExpressAd(spaceBetweenAds);
            }
        });

    }

    private void loadNativeExpressAd(final int index) {

        if (index >= mSongModels.size()) {
            return;
        }

        Object item = mSongModels.get(index);
        if (!(item instanceof AdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final AdView adView = (AdView) item;

        // Set an AdListener on the NativeExpressAdView to wait for the previous Native Express ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + spaceBetweenAds + 1);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("AdmobMainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + spaceBetweenAds + 1);
            }
        });

        // Load the Native Express ad.
        //We also registering our device as Test Device with addTestDevic("ID") method
        adView.loadAd(new AdRequest.Builder().addTestDevice("54F2A2C6318B029B2338389DB10AFDBE").build());
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        if (!wasRestored) {
            youTubePlayer.loadVideo(getVideoId());
            mYoutubePlayer = youTubePlayer;
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
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
