package com.trends.trending.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trends.trending.R;
import com.trends.trending.adapter.SlidePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity {

    public static final String GOOD_MORNING = "GOOD MORNING";
    public static final String GOOD_AFTERNOON = "GOOD AFTERNOON";
    public static final String GOOD_EVENING = "GOOD EVENING";

    List<String> colorName;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.greet_user)
    TextView mGreetUser;
    @BindView(R.id.adView)
    AdView mBannerAd;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mNavigationView = findViewById(R.id.nav_view);

        setUpNavigationView();

        mGreetUser.setText(greetText() + " MR. NEERAJ!!");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getSupportActionBar().setElevation(0);
//        }


        colorName = new ArrayList<>();
        colorName.add("The Pessimist Sees Difficulty In Every Opportunity. The Optimist Sees Opportunity In Every Difficulty.");
        colorName.add("You Learn More From Failure Than From Success. Don’t Let It Stop You. Failure Builds Character.");
        colorName.add("If You Are Working On Something That You Really Care About, You Don’t Have To Be Pushed. The Vision Pulls You.");

        viewPager.setAdapter(new SlidePagerAdapter(this, colorName));
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        adViewSetup1();

    }

    private void setUpNavigationView() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_video:
                        Toast.makeText(Home.this, "Video", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_quote:
                        Toast.makeText(Home.this, "Quote", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_fact:
                        Toast.makeText(Home.this, "Fact", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_best_place:
                        Toast.makeText(Home.this, "Place to visit", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_top_ten:
                        Toast.makeText(Home.this, "Top 10", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_feedback:
                        Toast.makeText(Home.this, "Feedback", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_setting:
                        Toast.makeText(Home.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;


                }


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);


                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the mDrawerLayout closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the mDrawerLayout open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to mDrawerLayout layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    private void startActivity(final Class<? extends Activity> clz) {
        startActivity(new Intent(Home.this, clz));
    }

    private void adViewSetup1() {
        mBannerAd = findViewById(R.id.adView);
//        mBannerAd.setAdSize(AdSize.BANNER);
//        mBannerAd.setAdUnitId(getString(R.string.banner_home_footer));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("54F2A2C6318B029B2338389DB10AFDBE")
                .build();

        mBannerAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

        });

        mBannerAd.loadAd(adRequest);
    }

    private String greetText() {

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            return GOOD_MORNING;
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return GOOD_AFTERNOON;
        } else {
            return GOOD_EVENING;
        }
    }

    @OnClick({R.id.home_video, R.id.home_quote, R.id.home_fact, R.id.home_place, R.id.home_top10, R.id.home_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_video:
                startActivity(VideoHome.class);
                break;
            case R.id.home_quote:
                startActivity(Quote.class);
                break;
            case R.id.home_fact:
                startActivity(Fact.class);
                break;
            case R.id.home_place:
                startActivity(Place.class);
                break;
            case R.id.home_top10:
                startActivity(TopTenHome.class);
                break;
            case R.id.home_feedback:
                break;
        }
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < colorName.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public void onPause() {
        if (mBannerAd != null) {
            mBannerAd.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBannerAd != null) {
            mBannerAd.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mBannerAd != null) {
            mBannerAd.destroy();
        }
        super.onDestroy();
    }


}
