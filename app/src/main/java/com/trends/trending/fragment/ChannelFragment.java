package com.trends.trending.fragment;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trends.trending.MainActivity;
import com.trends.trending.R;
import com.trends.trending.adapter.PlaylistAdapter;
import com.trends.trending.adapter.VideoAdapter;
import com.trends.trending.model.youtube.Item;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.model.youtube.SearchItem;
import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.model.youtube.Video;
import com.trends.trending.model.youtube.VideoLink;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.service.ReturnReceiver;

import java.util.ArrayList;
import java.util.List;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.ExtraHelper.VIDEO_TYPE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_FITNESS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_MUSIC;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NEWS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TECHNOLOGY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_VINES;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_TRENDING;

public class ChannelFragment extends Fragment  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Playlist> channelList = new ArrayList();
    private List<Item> videoList = new ArrayList<>();
    private List<SearchItem> searchVideoList = new ArrayList<>();
    private View view;

    SharedPreferences settings;

    ReturnReceiver mReturnReceiver;

    public ChannelFragment() {
        // Required empty public constructor
    }



    public static ChannelFragment newInstance(String title) {
        
        Bundle args = new Bundle();
        
        ChannelFragment fragment = new ChannelFragment();
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
        view = inflater.inflate(R.layout.main_activity, container, false);
        mReturnReceiver = new ReturnReceiver(new Handler());
        mReturnReceiver.setReceiver((ReturnReceiver.Receiver) getContext());
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        String[] musicTitle = getResources().getStringArray(R.array.music_chhanel_title);
        TypedArray musicImage = getResources().obtainTypedArray(R.array.music_chhanel_image);

        String[] otherTitle = getResources().getStringArray(R.array.others_chhanel_title);
        TypedArray otherImage = getResources().obtainTypedArray(R.array.other_chhanel_image);

        channelList.clear();



        settings = getActivity().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);







        if(getTitle().equals(TAB_TRENDING)){
            Intent parent1 = new Intent(getActivity(), VideoRepository.class);

            parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
            parent1.putExtra(KEY_INTENT, VAL_TRENDING);
            getActivity().startService(parent1);
            String jsonParent = settings.getString("parentstring", null);
            Gson gson = new Gson();
            Parent parent = gson.fromJson(jsonParent, Parent.class);
            if (parent != null) {
                Toast.makeText(getActivity(), parent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getActivity(), "MainActivity null", Toast.LENGTH_LONG).show();

            for (Item item: parent.getItems()) {
                videoList.add(item);
            }
            adapter = new VideoAdapter(videoList, getContext());
//            for (int i = 0; i < otherTitle.length; i++) {
//                Playlist p = new Playlist();
//                p.setPlaylistTitle(otherTitle[i]);
//                p.setPlaylistImage(otherImage.getResourceId(i, -1));
//                channelList.add(p);
//            }
        }
        else if(getTitle().equals(TAB_TRAILER)){
            Intent parent1 = new Intent(getActivity(), VideoRepository.class);

            parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
            parent1.putExtra(KEY_INTENT, VAL_SEARCH);
//            getActivity().startService(parent1);
//            String jsonParent = settings.getString("searchParentString", null);
//            Gson gson = new Gson();
//            SearchParent searchParent = gson.fromJson(jsonParent, SearchParent.class);
//            if (searchParent != null) {
//                Toast.makeText(getActivity(), searchParent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
//            } else
//                Toast.makeText(getActivity(), "MainActivity null", Toast.LENGTH_LONG).show();
//            for (SearchItem item: searchParent.getItems()) {
//                searchVideoList.add(item);
//            }
//            adapter = new VideoAdapter(searchVideoList, getContext());
            //            for (int i = 0; i < musicTitle.length; i++) {
//                Playlist p = new Playlist();
//                p.setPlaylistTitle(musicTitle[i]);
//                p.setPlaylistImage(musicImage.getResourceId(i, -1));
//                channelList.add(p);
//            }
//            adapter = new PlaylistAdapter(channelList, getContext());
        }
        else if(getTitle().equals(TAB_MUSIC)){
            Playlist p = new Playlist();
            p.setPlaylistTitle("neeraj");
            p.setPlaylistImage(musicImage.getResourceId(0, -1));
            channelList.add(p);
            adapter = new PlaylistAdapter(channelList, getContext());
        }
        else if(getTitle().equals(TAB_FITNESS)){
            Playlist p = new Playlist();
            p.setPlaylistTitle("ankit");
            p.setPlaylistImage(musicImage.getResourceId(0, -1));
            channelList.add(p);
            adapter = new PlaylistAdapter(channelList, getContext());
        }
        else if(getTitle().equals(TAB_VINES)){
            Playlist p = new Playlist();
            p.setPlaylistTitle("HYD");
            p.setPlaylistImage(musicImage.getResourceId(0, -1));
            channelList.add(p);
            adapter = new PlaylistAdapter(channelList, getContext());
        }
        else if(getTitle().equals(TAB_COMEDY)){
            Playlist p = new Playlist();
            p.setPlaylistTitle("SBC");
            p.setPlaylistImage(musicImage.getResourceId(0, -1));
            channelList.add(p);
            adapter = new PlaylistAdapter(channelList, getContext());
        }
        else if(getTitle().equals(TAB_NEWS)){
            Playlist p = new Playlist();
            p.setPlaylistTitle("BSP");
            p.setPlaylistImage(musicImage.getResourceId(0, -1));
            channelList.add(p);
            adapter = new PlaylistAdapter(channelList, getContext());
        }
        else if(getTitle().equals(TAB_TECHNOLOGY)){
            Playlist p = new Playlist();
            p.setPlaylistTitle("Youtube");
            p.setPlaylistImage(musicImage.getResourceId(0, -1));
            channelList.add(p);
            adapter = new PlaylistAdapter(channelList, getContext());
        }
        else {
            Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();
        }






        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
    }

    private String getTitle() {
        Bundle args = getArguments();
        return args.getString("title", "NO TITLE FOUND");
    }



}
