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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.plumillonforge.android.chipview.OnChipClickListener;
import com.trends.trending.adapter.MainChipViewAdapter;
import com.trends.trending.service.ReturnReceiver;
import com.trends.trending.utils.Tag;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by USER on 5/6/2018.
 */

public class DummyBottom extends AppCompatActivity implements OnChipClickListener, ReturnReceiver.Receiver {
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    //    @BindView(R.id.bottom_sheet_recycler)
    RecyclerView mRecyclerView;

    private List<Chip> mTagList1;
    private List<Chip> mTagList2;

    private ChipView mTextChipOverride;

    private RecyclerView.LayoutManager layoutManager;

    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_bottom_main);

        ButterKnife.bind(this);



        mTagList2 = new ArrayList<>();
        mTagList2.add(new Tag("Music", 1));
        mTagList2.add(new Tag("Vines", 2));
        mTagList2.add(new Tag("Standup comedy", 3));
        mTagList2.add(new Tag("News", 4));
        mTagList2.add(new Tag("Fitness", 5));
        mTagList2.add(new Tag("Web series", 6));

        // Adapter
        ChipViewAdapter adapterLayout = new MainChipViewAdapter(this);
        ChipViewAdapter adapterOverride = new MainChipViewAdapter(this);


        // Chip override
        mTextChipOverride = (ChipView) findViewById(R.id.text_chip_override);
        mTextChipOverride.setAdapter(adapterOverride);
        mTextChipOverride.setChipList(mTagList2);
        mTextChipOverride.setOnChipClickListener(this);




        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);






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

    @Override
    public void onChipClick(Chip chip) {

//        mTextChipLayout.remove(chip);

        Toast.makeText(this, chip.getText(), Toast.LENGTH_SHORT).show();

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
