package com.trends.trending.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import com.squareup.picasso.Picasso;
import com.trends.trending.R;
import com.trends.trending.adapter.VideoAdapter;
import com.trends.trending.fragment.DownloadFormatDialog;
import com.trends.trending.interfaces.ItemClickListener;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.SearchItem;
import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.service.ReturnReceiver;
import com.trends.trending.utils.ExtraHelper;
import com.trends.trending.utils.RecyclerDividerItemDecoration;
import com.trends.trending.utils.RoundCornersTransformation;

import java.util.ArrayList;
import java.util.List;

import static com.trends.trending.fragment.VideoFragment.IMAGE_CORNER_RADIUS;
import static com.trends.trending.fragment.VideoFragment.IMAGE_HEIGHT;
import static com.trends.trending.fragment.VideoFragment.IMAGE_WIDTH;
import static com.trends.trending.utils.Keys.VideoInfo.CHANNEL_OR_PLAYLIST_ID;
import static com.trends.trending.utils.Keys.VideoInfo.CURRENT_TITLE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_CHANNEL_ID;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_FITNESS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_WEBSERIES;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_CHANNEL_VIDEOS;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_PLAYLIST_VIDEOS;
import static com.trends.trending.utils.Keys.VideoInfo.YOUTUBE_VIDEO_URL;

/**
 * Created by USER on 5/14/2018.
 */

public class PlaylistVideo extends AppCompatActivity implements ReturnReceiver.Receiver {


    private ReturnReceiver mReturnReceiver;
    private String mTitle;
    private String id;
    private List<Item> mVideoList = new ArrayList<>();
    private List<SearchItem> mSearchVideoList = new ArrayList<>();
    private YouTubePlayer YPlayer;
    private ShimmerFrameLayout mShimmerFrameLayout;
    private LinearLayout mContainer;



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

        getData();

    }

    private void getData() {

        Intent parent1 = new Intent(this, VideoRepository.class);
        parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
        if (TextUtils.equals(mTitle, TAB_WEBSERIES) || TextUtils.equals(mTitle, TAB_FITNESS))
            parent1.putExtra(KEY_INTENT, VAL_PLAYLIST_VIDEOS);
        else
            parent1.putExtra(KEY_INTENT, VAL_CHANNEL_VIDEOS);
        parent1.putExtra(KEY_CHANNEL_ID, id);
        startService(parent1);

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        Parent parent;
        SearchParent searchParent;

        RecyclerView recyclerView = findViewById(R.id.recycler_view_video);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerDividerItemDecoration(this));
        RecyclerView.Adapter adapter;

        mShimmerFrameLayout.stopShimmerAnimation();
        mContainer.removeView(mShimmerFrameLayout);
        if ((resultData.getParcelable(KEY_PARENT)) instanceof Parent) {
            parent = resultData.getParcelable(KEY_PARENT);
            if (parent != null) {
               // Toast.makeText(this, parent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
                mVideoList.addAll(parent.getItems());

                adapter = new VideoAdapter<Item>(mVideoList, new ItemClickListener<Item>() {
                    @Override
                    public void onShareClick(Item item) {

                    }

                    @Override
                    public void onDownloadClick(Item item) {
                        downloadVideo(item.getId());
                    }

                    @Override
                    public void onPlayClick(Item item) {
                        youTubePlayer(item);
                    }
                }) {

                    @Override
                    public void onBindData(PlanetViewHolder holder1, Item val) {
                        Item userModel = val;
                        PlanetViewHolder holder = holder1;
                        Picasso.get()
                                .load(userModel.getSnippet().getThumbnails().getHigh().getUrl())
                                .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                                .transform(new RoundCornersTransformation(IMAGE_CORNER_RADIUS, 0, true, true))
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.amit_bhadana)
                                .into(holder.image);
                        holder.title.setText(userModel.getSnippet().getTitle());
                        holder1.title.setSelected(true);
                        holder.channelTtile.setText(userModel.getSnippet().getChannelTitle());

                    }
                };


            } else {
                adapter = null;
                Toast.makeText(this, "MainActivity null", Toast.LENGTH_LONG).show();
            }
        } else
        {
            searchParent = resultData.getParcelable(KEY_PARENT);
            if (searchParent != null) {
               // Toast.makeText(this, searchParent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
                mSearchVideoList.addAll(searchParent.getItems());

                adapter = new VideoAdapter<SearchItem>(mSearchVideoList, new ItemClickListener<SearchItem>() {
                    @Override
                    public void onShareClick(SearchItem item) {

                    }

                    @Override
                    public void onDownloadClick(SearchItem item) {
                        downloadVideo(item.getId().getVideoId());
                    }

                    @Override
                    public void onPlayClick(SearchItem item) {
                        youTubePlayer(item);
                    }
                }) {

                    @Override
                    public void onBindData(VideoAdapter.PlanetViewHolder holder1, SearchItem val) {

                        SearchItem userModel = val;

                        Picasso.get()
                                .load(userModel.getSnippet().getThumbnails().getMedium().getUrl())
                                .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                                .transform(new RoundCornersTransformation(IMAGE_CORNER_RADIUS, 0, true, true))
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.amit_bhadana)
                                .into(holder1.image);

                        holder1.title.setText(userModel.getSnippet().getTitle());
                        holder1.title.setSelected(true);
                        holder1.channelTtile.setText(userModel.getSnippet().getChannelTitle());

                    }
                };

            } else {
                adapter = null;
                Toast.makeText(this, "MainActivity null", Toast.LENGTH_LONG).show();
            }
        }
        recyclerView.setAdapter(adapter);

    }


    private void youTubePlayer(Object item) {
        final String videoId;
        if (item instanceof Item) {
            videoId = ((Item) item).getId();
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
//                    YPlayer.setFullscreen(false);
//                    YPlayer.loadVideo("2zNSgSzhBfM");
//                    YPlayer.play();

                    YPlayer.cueVideo(videoId);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }

    private void downloadVideo(String videoId) {
        ExtraHelper extraHelper = new ExtraHelper();
        extraHelper.getYoutubeDownloadUrl(YOUTUBE_VIDEO_URL + videoId, this);
        DownloadFormatDialog downloadFormatDialog = new DownloadFormatDialog();
        downloadFormatDialog.show(getSupportFragmentManager(), "DIALOG_FRAGMENT");

    }

    }
