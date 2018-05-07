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
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.trends.trending.R;
import com.trends.trending.adapter.VideoAdapter;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.model.youtube.SearchItem;
import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.service.ReturnReceiver;

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
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_TRENDING;

public class TrailerFragment extends Fragment  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Playlist> channelList = new ArrayList();
    private List<Item> videoList = new ArrayList<>();
    private List<SearchItem> searchVideoList = new ArrayList<>();
    private View view;
    private YouTubePlayer YPlayer;

    SharedPreferences settings;

    ReturnReceiver mReturnReceiver;

    public TrailerFragment() {
        // Required empty public constructor
    }



    public static TrailerFragment newInstance(String title) {

        Bundle args = new Bundle();

        TrailerFragment fragment = new TrailerFragment();
        args.putString("title", title);
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
        //YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getFragmentManager().findFragmentById(R.id.youtube_fragment);
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

                    YPlayer.cueVideo("oZF0xUA6xCc");
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        mReturnReceiver = new ReturnReceiver(new Handler());
        mReturnReceiver.setReceiver((ReturnReceiver.Receiver) getContext());
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_video);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        channelList.clear();

        settings = getActivity().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);


//       // if(getTitle().equals(TAB_TRENDING)){
//            Intent parent1 = new Intent(getActivity(), VideoRepository.class);
//
//            parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
//            parent1.putExtra(KEY_INTENT, VAL_TRENDING);
//
//            getActivity().startService(parent1);
//            String jsonParent = settings.getString(PARENT_TO_STRING, null);
//            Gson gson = new Gson();
//            Parent parent = gson.fromJson(jsonParent, Parent.class);
//            if (parent != null) {
//                Toast.makeText(getActivity(), parent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
//            } else
//                Toast.makeText(getActivity(), "MainActivity null", Toast.LENGTH_LONG).show();
//
//            for (Item item: parent.getItems()) {
//                videoList.add(item);
//            }
//
//            adapter = new VideoAdapter<Item>(videoList,getActivity()) {
//
//                @Override
//                public void onBindData(PlanetViewHolder holder1, Item val) {
//                    Item userModel = val;
//
//                    PlanetViewHolder holder =  holder1;
//                    Picasso.get()
//                .load(userModel.getSnippet().getThumbnails().getMedium().getUrl())
//                .resize(90, 70)
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.amit_bhadana)
//                .into(holder.image);
//                    holder.title.setText(userModel.getSnippet().getTitle());
//                    holder.channelTtile.setText(userModel.getSnippet().getChannelTitle());
//
//                }
//            };

        //}
//        else if(getTitle().equals(TAB_TRAILER)){
            Intent parent1 = new Intent(getActivity(), VideoRepository.class);

            parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
            parent1.putExtra(KEY_INTENT, VAL_SEARCH);
            parent1.putExtra(KEY_SEARCH, KEY_TRAILERS);
            getActivity().startService(parent1);
            String jsonParent = settings.getString(SEARCH_PARENT_TO_STRING, null);
            Gson gson = new Gson();
            SearchParent searchParent = gson.fromJson(jsonParent, SearchParent.class);
            if (searchParent != null) {
                Toast.makeText(getActivity(), "frag::"+searchParent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getActivity(), "MainActivity null", Toast.LENGTH_LONG).show();
            for (SearchItem item: searchParent.getItems()) {
                searchVideoList.add(item);
            }
            adapter = new VideoAdapter<SearchItem>(searchVideoList,getActivity()) {

                @Override
                public void onBindData(VideoAdapter.PlanetViewHolder holder1, SearchItem val) {

                    SearchItem userModel = val;

                    Picasso.get()
                            .load(userModel.getSnippet().getThumbnails().getMedium().getUrl())
                            .resize(90, 70)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.amit_bhadana)
                            .into(holder1.image);

                    holder1.title.setText(userModel.getSnippet().getTitle());
                    holder1.channelTtile.setText(userModel.getSnippet().getChannelTitle());

                }
            };
//        }
//        else {
//            Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();
//        }

        recyclerView.setAdapter(adapter);
    }

    private String getTitle() {
        Bundle args = getArguments();
        return args.getString("title", "NO TITLE FOUND");
    }

}
