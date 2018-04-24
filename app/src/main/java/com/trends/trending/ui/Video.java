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

import com.trends.trending.R;
import com.trends.trending.fragment.VideosFragment;

import java.util.ArrayList;
import java.util.List;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_TAB_TITLE;

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
        adapter.addFragment(new VideosFragment(), "Trending");
        adapter.addFragment(new VideosFragment(), "Trailers");
        adapter.addFragment(new VideosFragment(), "Music");
        adapter.addFragment(new VideosFragment(), "Fitness");
        adapter.addFragment(new VideosFragment(), "Vines");
        adapter.addFragment(new VideosFragment(), "Standup comedy");
        adapter.addFragment(new VideosFragment(), "News");
        adapter.addFragment(new VideosFragment(), "Technology");
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
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            editor = settings.edit();
            editor.putString(KEY_TAB_TITLE, mFragmentTitleList.get(position));
            editor.commit();
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
