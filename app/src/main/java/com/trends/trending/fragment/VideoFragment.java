package com.trends.trending.fragment;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.trends.trending.R;
import com.trends.trending.adapter.VideoAdapter;
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

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_API;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_TRAILERS;
import static com.trends.trending.utils.Keys.VideoInfo.PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.SEARCH_PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.YOUTUBE_VIDEO_URL;

public class VideoFragment extends Fragment {

    public static final int IMAGE_WIDTH = 74;
    public static final int IMAGE_HEIGHT = 90;
    public static final int IMAGE_CORNER_RADIUS = 12;


    private List<Item> videoList = new ArrayList<>();
    private List<SearchItem> searchVideoList = new ArrayList<>();
    private View view;
    private YouTubePlayer YPlayer;
    private ReturnReceiver mReturnReceiver;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout mContainer;



    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance(String title) {

        Bundle args = new Bundle();

        VideoFragment fragment = new VideoFragment();
        args.putString("tab_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_video_container);
        mContainer = view.findViewById(R.id.video_container);
        mShimmerViewContainer.startShimmerAnimation();
        mReturnReceiver = new ReturnReceiver(new Handler());
        mReturnReceiver.setReceiver((ReturnReceiver.Receiver) getContext());
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_video);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerDividerItemDecoration(getContext()));
        //channelList.clear();

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        RecyclerView.Adapter adapter;
        if (getTitle().equals(TAB_TRENDING)) {
            Intent parent1 = new Intent(getActivity(), VideoRepository.class);

            parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
            parent1.putExtra(KEY_INTENT, VAL_TRENDING);

            getActivity().startService(parent1);
            String jsonParent = settings.getString(PARENT_TO_STRING, null);
            Gson gson = new Gson();
            Parent parent = gson.fromJson(jsonParent, Parent.class);
            if (parent != null) {
                mShimmerViewContainer.stopShimmerAnimation();
                mContainer.removeView(mShimmerViewContainer);
                videoList.addAll(parent.getItems());
            } else
                Toast.makeText(getActivity(), "Parent null", Toast.LENGTH_LONG).show();

            adapter = new VideoAdapter<Item>(videoList, new ItemClickListener<Item>() {
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
            Intent parent1 = new Intent(getActivity(), VideoRepository.class);

            parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
            parent1.putExtra(KEY_INTENT, VAL_SEARCH);
            parent1.putExtra(KEY_SEARCH, KEY_TRAILERS);
            getActivity().startService(parent1);
            String jsonParent = settings.getString(SEARCH_PARENT_TO_STRING, null);
            Gson gson = new Gson();
            SearchParent searchParent = gson.fromJson(jsonParent, SearchParent.class);
            if (searchParent != null) {
                mShimmerViewContainer.stopShimmerAnimation();
                mContainer.removeView(mShimmerViewContainer);
                searchVideoList.addAll(searchParent.getItems());
            } else
                Toast.makeText(getActivity(), "MainActivity null", Toast.LENGTH_LONG).show();


            adapter = new VideoAdapter<SearchItem>(searchVideoList, new ItemClickListener<SearchItem>() {
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
        }
        recyclerView.setAdapter(adapter);
    }

    private String getTitle() {
        Bundle args = getArguments();
        return args.getString("tab_title", "NO TITLE FOUND");
    }


    private void youTubePlayer(Object item) {
        final String videoId;
        if (item instanceof Item) {
            videoId = ((Item) item).getId();
        } else {
            videoId = ((SearchItem) item).getId().getVideoId();
        }

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
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
        extraHelper.getYoutubeDownloadUrl(YOUTUBE_VIDEO_URL + videoId, getContext());
        DownloadFormatDialog downloadFormatDialog = new DownloadFormatDialog();
        downloadFormatDialog.show(getFragmentManager(), "DIALOG_FRAGMENT");

    }

    @Override
    public void onResume() {
        super.onResume();
//        mContainer.addView(mShimmerViewContainer);
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        mContainer.removeView(mShimmerViewContainer);
        super.onPause();
    }
}
