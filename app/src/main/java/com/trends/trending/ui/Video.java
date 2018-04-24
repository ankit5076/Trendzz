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
import com.trends.trending.fragment.FitnessFragment;
import com.trends.trending.fragment.MusicFragment;
import com.trends.trending.fragment.NewsFragment;
import com.trends.trending.fragment.StandupComedyFragment;
import com.trends.trending.fragment.TechnologyFragment;
import com.trends.trending.fragment.TrailerFragment;
import com.trends.trending.fragment.TrendingFragment;
import com.trends.trending.fragment.VinesFragment;

import java.util.ArrayList;
import java.util.List;

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
        adapter.addFragment(new TrendingFragment(), "Trending");
        adapter.addFragment(new TrailerFragment(), "Trailers");
        adapter.addFragment(new MusicFragment(), "Music");
        adapter.addFragment(new FitnessFragment(), "Fitness");
        adapter.addFragment(new VinesFragment(), "Vines");
        adapter.addFragment(new StandupComedyFragment(), "Standup comedy");
        adapter.addFragment(new NewsFragment(), "News");
        adapter.addFragment(new TechnologyFragment(), "Technology");
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
