package com.trends.trending.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.trends.trending.R;
import com.trends.trending.adapter.VideoAdapter;
import com.trends.trending.interfaces.ItemClickListener;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.SearchItem;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.service.ReturnReceiver;
import com.trends.trending.utils.CustomTabs;
import com.trends.trending.utils.RecyclerDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_ID;
import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_OR_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_URL;
import static com.trends.trending.utils.Keys.VideoInfo.CURRENT_TITLE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.PLAYLIST_TITLE;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_WEBSERIES;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_CHANNEL_VIDEOS;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_PLAYLIST_VIDEOS;
import static com.trends.trending.utils.Keys.VideoInfo.YOUTUBE_PACKAGE_NAME;

/**
 * Created by USER on 5/14/2018.
 */

public class PlaylistVideo extends AppCompatActivity implements ReturnReceiver.Receiver {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private ReturnReceiver mReturnReceiver;
    private String mTitle;
    private String id;
    private String channelId;
    private List<Item> mVideoList = new ArrayList<>();
    private YouTubePlayer YPlayer;
    private ShimmerFrameLayout mShimmerFrameLayout;
    private LinearLayout mContainer;
    private VideoAdapter adapter;
    private boolean isFullScreen = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);
        mContainer = findViewById(R.id.video_container);
        mReturnReceiver = new ReturnReceiver(new Handler());
        mReturnReceiver.setReceiver(this);
        mShimmerFrameLayout = findViewById(R.id.shimmer_view_video_container);
        mShimmerFrameLayout.startShimmerAnimation();
        Bundle extras = getIntent().getExtras();
        mTitle = (extras == null) ? "" : extras.getString(CURRENT_TITLE);
        id = (extras == null) ? "" : extras.getString(CHANNEL_OR_PLAYLIST_ID);
        channelId = (extras == null) ? "" : extras.getString(CHANNEL_ID);
        ActionBar toolbar = getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle((extras == null) ? TAB_WEBSERIES : extras.getString(PLAYLIST_TITLE));
        }
        getData();

    }

    private void getData() {

        Intent parent1 = new Intent(this, VideoRepository.class);
        parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
        if (TextUtils.equals(mTitle, TAB_WEBSERIES))
            parent1.putExtra(KEY_INTENT, VAL_PLAYLIST_VIDEOS);
        else
            parent1.putExtra(KEY_INTENT, VAL_CHANNEL_VIDEOS);
        parent1.putExtra(KEY_CHANNEL_ID, id);
        startService(parent1);

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        Parent parent;

        RecyclerView recyclerView = findViewById(R.id.recycler_view_video);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerDividerItemDecoration(this));

        mShimmerFrameLayout.stopShimmerAnimation();
        mContainer.removeView(mShimmerFrameLayout);
        if ((resultData.getParcelable(KEY_PARENT)) instanceof Parent) {

            parent = resultData.getParcelable(KEY_PARENT);
            if (parent != null) {

                mVideoList.addAll(parent.getItems());

                ItemClickListener itemClickListener = new ItemClickListener() {
                    /*
                    New feature share and download
                    @Override
                    public void onShareClick(Item item) {

                    }

                    @Override
                    public void onDownloadClick(Item item) {
                        downloadVideo(item.getId());
                    }
                    */

                    @Override
                    public void onChannelTitleClick(Item item) {
                        goToYouTube(CHANNEL_URL + channelId);
                    }

                    @Override
                    public void onPlayClick(Item item) {
                        youTubePlayer(item);
                    }
                };

                adapter = new VideoAdapter(mVideoList, itemClickListener, PlaylistVideo.this);
            } else {
                adapter = null;
                Toast.makeText(this, getResources().getString(R.string.try_again), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Some error occoured", Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(adapter);
    }


    private void goToYouTube(String url) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage(YOUTUBE_PACKAGE_NAME);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            CustomTabs.openTab(PlaylistVideo.this, url);
        }
    }


    private void youTubePlayer(Object item) {
        final String videoId;
        if (item instanceof Item) {
            videoId = ((Item) item).getSnippet().getResourceId().getVideoId();

        } else {
            videoId = ((SearchItem) item).getId().getVideoId();
        }

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_fragment, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(KEY_API, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);
                    isFullScreen = false;
                    YPlayer.loadVideo(videoId);
                    YPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
                    YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }

                YPlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                    @Override
                    public void onFullscreen(boolean b) {
                        if (b) {
                            YPlayer.setFullscreen(true);
                            isFullScreen = true;
                        } else {
                            YPlayer.setFullscreen(false);
                            isFullScreen = false;
                        }
                    }
                });

                YPlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
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
                            int pos = adapter.getItemPosition();
                            adapter.setItemPosition(-1);
                            adapter.notifyItemChanged(pos);
                        }

                    }

                    @Override
                    public void onVideoStarted() {
                    }
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(PlaylistVideo.this, RECOVERY_DIALOG_REQUEST).show();
                } else {
                    String errorMessage = String.format(
                            getString(R.string.error_player), youTubeInitializationResult.toString());
                    Toast.makeText(PlaylistVideo.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (isFullScreen) {
            YPlayer.setFullscreen(false);
            isFullScreen = false;
        } else
            super.onBackPressed();
    }

//    private void downloadVideo(String videoId) {
//        ExtraHelper extraHelper = new ExtraHelper();
//        extraHelper.getYoutubeDownloadUrl(YOUTUBE_VIDEO_URL + videoId, this);
//        DownloadFormatDialog downloadFormatDialog = new DownloadFormatDialog();
//        downloadFormatDialog.show(getSupportFragmentManager(), "DIALOG_FRAGMENT");
//
//    }

}
