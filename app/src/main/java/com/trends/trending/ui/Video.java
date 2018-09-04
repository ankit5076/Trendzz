package com.trends.trending.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.adapter.NewVideoAdapter;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.service.ReturnReceiver;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_METHOD;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.PARENT_BOLLYWOOD_TRAILER_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.PARENT_HOLLYWOOD_TRAILER_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.PARENT_SONGS_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_BOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_HOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_ID_NEW_SONGS;
import static com.trends.trending.utils.Keys.VideoInfo.SEARCH_PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_BOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_HOLLYWOOD_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_REVIEWS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_SONGS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_PLAYLIST_VIDEOS;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_TRENDING;


public class Video extends YouTubeBaseActivity implements ReturnReceiver.Receiver, YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    public final static int spaceBetweenAds = 3;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.youtube_player)
    YouTubePlayerView mYouTubePlayerView;


    private ArrayList<Object> mSongModels = new ArrayList<>();
    private String videoId;
    private View mView;
    private YouTubePlayer mYoutubePlayer;
    private ReturnReceiver mReturnReceiver;
    private boolean isTrending = true;
    private ValueAnimator colorAnimation;

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
        int colorFrom = getResources().getColor(R.color.red);
        int colorTo = getResources().getColor(R.color.blue);
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setDuration(500);
        setAdapter();
    }


    private void setAdapter() {

        Bundle b = getIntent().getExtras();
        if (b != null) {
            SharedPreferences settings = this.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            String tab = b.getString(TAB_NAME, TAB_TRENDING);
            switch (tab) {
                case TAB_TRENDING:
                    isTrending = true;
                    Intent parent1 = new Intent(Video.this, VideoRepository.class);

                    parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
                    parent1.putExtra(KEY_INTENT, VAL_TRENDING);

                    this.startService(parent1);
                    String jsonParent = settings.getString(PARENT_TO_STRING, null);
                    Gson gson = new Gson();
                    Parent parent = gson.fromJson(jsonParent, Parent.class);
                    if (parent != null) {
//                        mShimmerViewContainer.stopShimmerAnimation();
                        //                      mContainer.removeView(mShimmerViewContainer);
                        mSongModels.addAll(parent.getItems());
                    } else
                        Toast.makeText(this, "Parent null", Toast.LENGTH_LONG).show();


                    break;
                case TAB_BOLLYWOOD_TRAILER:
                    isTrending = false;
                    Intent parent2 = new Intent(Video.this, VideoRepository.class);

                    parent2.putExtra(KEY_RECEIVER, mReturnReceiver);
                    parent2.putExtra(KEY_INTENT, VAL_PLAYLIST_VIDEOS);
                    parent2.putExtra(KEY_CHANNEL_ID, PLAYLIST_ID_BOLLYWOOD_TRAILER);

                    this.startService(parent2);
                    String jsonParent1 = settings.getString(PARENT_BOLLYWOOD_TRAILER_TO_STRING, null);
                    Gson gson1 = new Gson();
                    Parent parent22 = gson1.fromJson(jsonParent1, Parent.class);
                    if (parent22 != null) {
//                        mShimmerViewContainer.stopShimmerAnimation();
                        //                      mContainer.removeView(mShimmerViewContainer);
                        mSongModels.addAll(parent22.getItems());
                    } else
                        Toast.makeText(this, "Parent null", Toast.LENGTH_LONG).show();

                    break;
                case TAB_HOLLYWOOD_TRAILER:
                    isTrending = false;
                    Intent holly = new Intent(Video.this, VideoRepository.class);

                    holly.putExtra(KEY_RECEIVER, mReturnReceiver);
                    holly.putExtra(KEY_INTENT, VAL_PLAYLIST_VIDEOS);
                    holly.putExtra(KEY_CHANNEL_ID, PLAYLIST_ID_HOLLYWOOD_TRAILER);

                    this.startService(holly);
                    String jsonholly = settings.getString(PARENT_HOLLYWOOD_TRAILER_TO_STRING, null);
                    Gson gsonholly = new Gson();
                    Parent parentholly = gsonholly.fromJson(jsonholly, Parent.class);
                    if (parentholly != null) {
//                        mShimmerViewContainer.stopShimmerAnimation();
                        //                      mContainer.removeView(mShimmerViewContainer);
                        mSongModels.addAll(parentholly.getItems());
                    } else
                        Toast.makeText(this, "Parent null", Toast.LENGTH_LONG).show();

                    break;
                case TAB_SONGS:
                    isTrending = false;
                    Intent songs = new Intent(Video.this, VideoRepository.class);

                    songs.putExtra(KEY_RECEIVER, mReturnReceiver);
                    songs.putExtra(KEY_INTENT, VAL_PLAYLIST_VIDEOS);
                    songs.putExtra(KEY_CHANNEL_ID, PLAYLIST_ID_NEW_SONGS);

                    this.startService(songs);
                    String jsonsongs = settings.getString(PARENT_SONGS_TO_STRING, null);
                    Gson gsonsongs = new Gson();
                    Parent parentsongs = gsonsongs.fromJson(jsonsongs, Parent.class);
                    if (parentsongs != null) {
//                        mShimmerViewContainer.stopShimmerAnimation();
                        //                      mContainer.removeView(mShimmerViewContainer);
                        mSongModels.addAll(parentsongs.getItems());
                    } else
                        Toast.makeText(this, "Parent null", Toast.LENGTH_LONG).show();
                    break;
                case TAB_REVIEWS:
                    break;
            }

            addNativeExpressAds();
            NewVideoAdapter adapter = new NewVideoAdapter(Video.this, mSongModels, spaceBetweenAds, isTrending);
            adapter.SetOnItemClickListener(new NewVideoAdapter.OnItemClickListener() {

                @Override
                public void onOldItemClick(final View view, int position, Item model) {
                    setView(view);
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


    }


    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Parent parent;
        SearchParent searchParent;
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        Bundle b = getIntent().getExtras();

        if (TextUtils.equals(resultData.getString(KEY_METHOD), VAL_SEARCH)) {
            searchParent = resultData.getParcelable(KEY_PARENT);
//            if (searchParent != null)
//                Toast.makeText(this, "video:: "+searchParent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
//            else
//                Toast.makeText(this, "nulllllllllllll", Toast.LENGTH_SHORT).show();
            String parentString = gson.toJson(searchParent);

            editor.putString(SEARCH_PARENT_TO_STRING, parentString);
            editor.commit();
        } else {

            parent = resultData.getParcelable(KEY_PARENT);
            String parentString = gson.toJson(parent);
            if (b.getString(TAB_NAME, TAB_TRENDING).equals(TAB_BOLLYWOOD_TRAILER)) {
                editor.putString(PARENT_BOLLYWOOD_TRAILER_TO_STRING, parentString);
            } else if (b.getString(TAB_NAME, TAB_TRENDING).equals(TAB_SONGS)) {
                Toast.makeText(this, "tab songssss", Toast.LENGTH_SHORT).show();
                editor.putString(PARENT_SONGS_TO_STRING, parentString);
            } else if (b.getString(TAB_NAME, TAB_TRENDING).equals(TAB_HOLLYWOOD_TRAILER)) {
                editor.putString(PARENT_HOLLYWOOD_TRAILER_TO_STRING, parentString);
            } else
                editor.putString(PARENT_TO_STRING, parentString);
            editor.commit();
        }


//        if (parent != null) {
//            Toast.makeText(this, parent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
//        } else
//            Toast.makeText(this, "MainActivity null", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            mYoutubePlayer = youTubePlayer;
            mYoutubePlayer.setFullscreen(false);
            mYoutubePlayer.loadVideo(getVideoId());
            mYoutubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
            mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }

        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                if(b){
                    mYoutubePlayer.setFullscreen(true);
//                    mYoutubePlayer.loadVideo(getVideoId());
//                    mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
                else
                    mYoutubePlayer.setFullscreen(false);
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
                if (colorAnimation.isRunning()) {
                    colorAnimation.end();
                }
            }

            @Override
            public void onPlaying() {


                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        getView().findViewById(R.id.horizontal_divider).setBackgroundColor((int) animator.getAnimatedValue());
                    }

                });
                colorAnimation.start();


            }

            @Override
            public void onSeekTo(int arg0) {
            }

            @Override
            public void onStopped() {
                if (colorAnimation.isRunning()) {
                    colorAnimation.end();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (colorAnimation.isRunning()) {
            colorAnimation.end();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (colorAnimation.isRunning()) {
            colorAnimation.end();
        }
        setView(null);
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

}
