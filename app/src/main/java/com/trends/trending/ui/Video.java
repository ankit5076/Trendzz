package com.trends.trending.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.trends.trending.R;
import com.trends.trending.adapter.NewVideoAdapter;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.service.ReturnReceiver;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_METHOD;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_BHAJAN;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_BOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_HOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_MOTIVATION;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_NEW_SONGS;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_TECHNOLOGY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_BHAJAN;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_BOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_HOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_MOTIVATION;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NEW_SONGS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TECHNOLOGY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_PLAYLIST_VIDEOS;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_TRENDING;


public class Video extends YouTubeBaseActivity implements ReturnReceiver.Receiver, YouTubePlayer.OnInitializedListener {

    public final static int spaceBetweenAds = 3;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.youtube_player)
    YouTubePlayerView mYouTubePlayerView;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerFrameLayout;


    private ArrayList<Object> mSongModels = new ArrayList<>();
    private String videoId;
    private View mView;
    private YouTubePlayer mYoutubePlayer;
    private ReturnReceiver mReturnReceiver;
    private boolean isTrending = true;
    private NewVideoAdapter adapter;
    private int pos;
    private boolean isFullScreen = false;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    protected String getVideoId() {
        return videoId;
    }

    protected void setVideoId(String videoId) {
        this.videoId = videoId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_videos);
        ButterKnife.bind(this);
        if (!(getIntent().getExtras().getString(TAB_NAME, TAB_TRENDING)).equals(TAB_TRENDING))
            isTrending = false;
        mReturnReceiver = new ReturnReceiver(new Handler());
        mReturnReceiver.setReceiver(this);
        setAdapter();
    }


    private void setAdapter() {

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String tab = b.getString(TAB_NAME, TAB_TRENDING);
            switch (tab) {
                case TAB_TRENDING:
                    isTrending = true;
                    Intent parent1 = new Intent(Video.this, VideoRepository.class);
                    parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
                    parent1.putExtra(KEY_INTENT, VAL_TRENDING);
                    this.startService(parent1);
                    break;

                case TAB_BOLLYWOOD_TRAILER:
                    getDataInBackground(PLAYLIST_ID_BOLLYWOOD_TRAILER);
                    break;

                case TAB_HOLLYWOOD_TRAILER:
                    getDataInBackground(PLAYLIST_ID_HOLLYWOOD_TRAILER);
                    break;

                case TAB_NEW_SONGS:
                    getDataInBackground(PLAYLIST_ID_NEW_SONGS);
                    break;

                case TAB_MOTIVATION:
                    getDataInBackground(PLAYLIST_ID_MOTIVATION);
                    break;

                case TAB_BHAJAN:
                    getDataInBackground(PLAYLIST_ID_BHAJAN);
                    break;

                case TAB_COMEDY:
                    getDataInBackground(PLAYLIST_ID_COMEDY);
                    break;

                case TAB_TECHNOLOGY:
                    getDataInBackground(PLAYLIST_ID_TECHNOLOGY);
                    break;
            }
        }
    }


    private void getDataInBackground(String playlistId) {

        isTrending = false;
        Intent intent = new Intent(Video.this, VideoRepository.class);
        intent.putExtra(KEY_RECEIVER, mReturnReceiver);
        intent.putExtra(KEY_INTENT, VAL_PLAYLIST_VIDEOS);
        intent.putExtra(KEY_CHANNEL_ID, playlistId);
        this.startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Parent parent;
        if (!TextUtils.equals(resultData.getString(KEY_METHOD), VAL_SEARCH)) {

            parent = resultData.getParcelable(KEY_PARENT);

            if (parent != null) {
                displayData(parent);
            } else
                toastResult();

        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        if (!wasRestored) {
            mYoutubePlayer = youTubePlayer;
            mYoutubePlayer.setFullscreen(false);
            isFullScreen = false;
            mYoutubePlayer.loadVideo(getVideoId());
            mYoutubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
            mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }

        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                if (b) {
                    mYoutubePlayer.setFullscreen(true);
                    isFullScreen = true;
                } else {
                    mYoutubePlayer.setFullscreen(false);
                    isFullScreen = false;
                }
            }
        });

        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onAdStarted() {
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason arg0) {
            }

            @Override
            public void onLoaded(String arg0) {
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onVideoEnded() {
                if (adapter != null) {
                    adapter.setItemPosition(-1);
                    adapter.notifyItemChanged(getPos());
                }

            }

            @Override
            public void onVideoStarted() {
            }
        });

        youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onBuffering(boolean arg0) {
            }

            @Override
            public void onPaused() {
            }

            @Override
            public void onPlaying() {
            }

            @Override
            public void onSeekTo(int arg0) {
            }

            @Override
            public void onStopped() {
            }
        });
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
                float scale = Video.this.getResources().getDisplayMetrics().density;
                int adWidth = (int) (recyclerView.getWidth() - (2 * Video.this.getResources().getDimension(R.dimen.activity_horizontal_margin)));

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(KEY_API, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_player);
    }

    private void displayData(Parent parent) {
        mShimmerFrameLayout.stopShimmerAnimation();
        mShimmerFrameLayout.setVisibility(View.GONE);
        mSongModels.addAll(parent.getItems());
        addNativeExpressAds();
        adapter = new NewVideoAdapter(Video.this, mSongModels, spaceBetweenAds, isTrending);
        adapter.setOnItemClickListener(new NewVideoAdapter.OnItemClickListener() {

            @Override
            public void onOldItemClick(final View view, int position, Item model) {
                setView(view);
                setPos(position);
                if (!isTrending) {
                    Toast.makeText(Video.this, "id: " + model.getSnippet().getResourceId().getVideoId(), Toast.LENGTH_SHORT).show();
                    setVideoId(model.getSnippet().getResourceId().getVideoId());
                } else {
                    Toast.makeText(Video.this, "id: " + model.getId(), Toast.LENGTH_SHORT).show();
                    setVideoId(model.getId());
                }
                // milliseconds

                if (mYoutubePlayer == null) {
                    mYouTubePlayerView.initialize(KEY_API, Video.this);
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

    private void toastResult() {
        mShimmerFrameLayout.stopShimmerAnimation();
        mShimmerFrameLayout.setVisibility(View.GONE);
        Toast.makeText(this, "Please try again later", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        mShimmerFrameLayout.startShimmerAnimation();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mShimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setView(null);
    }

    @Override
    public void onBackPressed() {
        if (isFullScreen) {
            mYoutubePlayer.setFullscreen(false);
            isFullScreen = false;
        }
        else
            super.onBackPressed();
    }
}
