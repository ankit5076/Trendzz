package com.trends.trending;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.plumillonforge.android.chipview.OnChipClickListener;
import com.trends.trending.adapter.MainChipViewAdapter;
import com.trends.trending.fragment.ChannelFragment;
import com.trends.trending.fragment.VideoFragment;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.service.ReturnReceiver;
import com.trends.trending.utils.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_METHOD;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.SEARCH_PARENT_TO_STRING;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_FITNESS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_MUSIC;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NEWS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TECHNOLOGY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_VINES;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_WEBSERIES;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;

/**
 * Created by USER on 5/6/2018.
 */

public class DummyBottom extends AppCompatActivity implements OnChipClickListener, ReturnReceiver.Receiver {

    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private static final int TYPE_FIVE = 5;
    private static final int TYPE_SIX = 6;
    private static final int TYPE_SEVEN = 7;
    private static final int TYPE_EIGHT = 8;

    @BindView(R.id.bottom_sheet)
    NestedScrollView layoutBottomSheet;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_bottom_main);

        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(TAB_TRENDING);
        }

        List<Chip> chipList = new ArrayList<>();
        chipList.add(new Tag(TAB_TRENDING, TYPE_ONE));
        chipList.add(new Tag(TAB_TRAILER, TYPE_TWO));
        chipList.add(new Tag(TAB_WEBSERIES, TYPE_THREE));
        chipList.add(new Tag(TAB_MUSIC, TYPE_FOUR));
        chipList.add(new Tag(TAB_TECHNOLOGY, TYPE_FIVE));
        chipList.add(new Tag(TAB_FITNESS, TYPE_SIX));
        chipList.add(new Tag(TAB_NEWS, TYPE_SEVEN));
        chipList.add(new Tag(TAB_VINES, TYPE_EIGHT));

        ChipViewAdapter adapterOverride = new MainChipViewAdapter(this);


        // Chip override
        ChipView textChipOverride = findViewById(R.id.text_chip_override);
        textChipOverride.setAdapter(adapterOverride);
        textChipOverride.setChipList(chipList);
        textChipOverride.setOnChipClickListener(this);


        BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frag, VideoFragment.newInstance(TAB_TRENDING))
                .commit();

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        ((TextView) findViewById(R.id.bottom_sheet_header)).setText(R.string.swipe_down);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        ((TextView) findViewById(R.id.bottom_sheet_header)).setText(R.string.swipe_up);
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

    @Override
    public void onChipClick(Chip chip) {
        String title = chip.getText();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!TextUtils.equals(actionBar.getTitle(), title))

            Toast.makeText(this, chip.getText(), Toast.LENGTH_SHORT).show();
            switch (title) {
                case TAB_TRENDING:
                case TAB_TRAILER:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag, VideoFragment.newInstance(title))
                            .commit();
                    break;
                case TAB_MUSIC:
                case TAB_FITNESS:
                case TAB_VINES:
                case TAB_COMEDY:
                case TAB_NEWS:
                case TAB_TECHNOLOGY:
                case TAB_WEBSERIES:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frag, ChannelFragment.newInstance(title))
                            .commit();
                    break;
                default:
                    break;

            }
//        mTextChipLayout.remove(chip);
        //setChipBackgroundColorSelected(getResources().getColor(R.color.green));

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

        if (TextUtils.equals(resultData.getString(KEY_METHOD), VAL_SEARCH)) {
            searchParent = resultData.getParcelable(KEY_PARENT);
//            if (searchParent != null)
//                Toast.makeText(this, "video:: " + searchParent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
//            else
//                Toast.makeText(this, "nulllllllllllll", Toast.LENGTH_SHORT).show();
            String parentString = gson.toJson(searchParent);

            editor.putString(SEARCH_PARENT_TO_STRING, parentString);
            editor.commit();
        } else {
            parent = resultData.getParcelable(KEY_PARENT);
            String parentString = gson.toJson(parent);

            editor.putString(PARENT_TO_STRING, parentString);
            editor.commit();
        }
    }
}
