package com.trends.trending.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.trends.trending.R;
import com.trends.trending.fragment.ChannelFragment;

import java.util.ArrayList;
import java.util.List;

import static com.trends.trending.utils.Keys.VideoInfo.TAB_COMEDY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_FITNESS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_MUSIC;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_NEWS;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TECHNOLOGY;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRAILER;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_TRENDING;
import static com.trends.trending.utils.Keys.VideoInfo.TAB_VINES;

/**
 * Created by ankit.a.vishwakarma on 18-Apr-18.
 */

public class Video extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    //tabLayout.OnTabSelectedListener(new )

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ChannelFragment.newInstance(TAB_TRENDING), TAB_TRENDING);
        adapter.addFragment(ChannelFragment.newInstance(TAB_TRAILER), TAB_TRAILER);
        adapter.addFragment(ChannelFragment.newInstance(TAB_MUSIC), TAB_MUSIC);
        adapter.addFragment(ChannelFragment.newInstance(TAB_FITNESS), TAB_FITNESS);
        adapter.addFragment(ChannelFragment.newInstance(TAB_VINES), TAB_VINES);
        adapter.addFragment(ChannelFragment.newInstance(TAB_COMEDY), TAB_COMEDY);
        adapter.addFragment(ChannelFragment.newInstance(TAB_NEWS), TAB_NEWS);
        adapter.addFragment(ChannelFragment.newInstance(TAB_TECHNOLOGY), TAB_TECHNOLOGY);
        viewPager.setAdapter(adapter);
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
