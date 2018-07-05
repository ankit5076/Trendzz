package com.trends.trending.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.fragment.ChannelFragment;
import com.trends.trending.fragment.VideoFragment;
import com.trends.trending.model.youtube.Parent;
import com.trends.trending.model.youtube.SearchParent;
import com.trends.trending.service.ReturnReceiver;

import java.util.ArrayList;
import java.util.List;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_METHOD;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_SEARCH;
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
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_TRENDING;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

public class Video extends AppCompatActivity implements ReturnReceiver.Receiver {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    //tabLayout.OnTabSelectedListener(new )

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(VideoFragment.newInstance(TAB_TRENDING), TAB_TRENDING);
        adapter.addFragment(VideoFragment.newInstance(TAB_TRAILER), TAB_TRAILER);
        adapter.addFragment(ChannelFragment.newInstance(TAB_MUSIC), TAB_MUSIC);
        adapter.addFragment(ChannelFragment.newInstance(TAB_FITNESS), TAB_FITNESS);
        adapter.addFragment(ChannelFragment.newInstance(TAB_VINES), TAB_VINES);
        adapter.addFragment(ChannelFragment.newInstance(TAB_COMEDY), TAB_COMEDY);
        adapter.addFragment(ChannelFragment.newInstance(TAB_NEWS), TAB_NEWS);
        adapter.addFragment(ChannelFragment.newInstance(TAB_TECHNOLOGY), TAB_TECHNOLOGY);
        viewPager.setAdapter(adapter);
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

            editor.putString(PARENT_TO_STRING, parentString);
            editor.commit();
        }


//        if (parent != null) {
//            Toast.makeText(this, parent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
//        } else
//            Toast.makeText(this, "MainActivity null", Toast.LENGTH_LONG).show();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
