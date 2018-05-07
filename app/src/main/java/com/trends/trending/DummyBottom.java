package com.trends.trending;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trends.trending.adapter.PlaylistAdapter;
import com.trends.trending.fragment.TrailerFragment;
import com.trends.trending.fragment.VideoFragment;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.Playlist;
import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.service.ReturnReceiver;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_METHOD;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.SEARCH_PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;

/**
 * Created by USER on 5/6/2018.
 */

public class DummyBottom extends AppCompatActivity implements ReturnReceiver.Receiver {

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    @BindView(R.id.bottom_sheet_recycler)
    RecyclerView mRecyclerView;


    private RecyclerView.LayoutManager layoutManager;

    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_bottom_main);

        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        String[] musicTitle = getResources().getStringArray(R.array.music_chhanel_title);
        TypedArray musicImage = getResources().obtainTypedArray(R.array.music_chhanel_image);

        ArrayList<Playlist> channelList = new ArrayList();

        RecyclerView.Adapter adapter;
channelList.clear();
        for (int i = 0; i < musicTitle.length; i++) {
            Playlist p = new Playlist();
            p.setPlaylistTitle(musicTitle[i]);
            p.setPlaylistImage(musicImage.getResourceId(i, -1));
            channelList.add(p);
        }
        adapter = new PlaylistAdapter(channelList, this);

        mRecyclerView.setAdapter(adapter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        VideoFragment videoFragment = new VideoFragment();
        fragmentManager.beginTransaction()
                .add(R.id.frag, videoFragment)
                .commit();

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                       // btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


    }



    public void toggleBottomSheet(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        //VideoFragment videoFragment = new VideoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.frag, new TrailerFragment())
                .commit();

//        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            // btnBottomSheet.setText("Close sheet");
//        } else {
//            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            //btnBottomSheet.setText("Expand sheet");
//        }
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

        if (TextUtils.equals(resultData.getString(KEY_METHOD),VAL_SEARCH)) {
            searchParent = resultData.getParcelable(KEY_PARENT);
            if (searchParent != null)
                Toast.makeText(this, "video:: "+searchParent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "nulllllllllllll", Toast.LENGTH_SHORT).show();
            String parentString = gson.toJson(searchParent);

            editor.putString(SEARCH_PARENT_TO_STRING, parentString);
            editor.commit();
        } else {
            parent = resultData.getParcelable(KEY_PARENT);
            String parentString = gson.toJson(parent);

            editor.putString(PARENT_TO_STRING, parentString);
            editor.commit();
    }
}}
