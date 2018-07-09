package com.trends.trending.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.trends.trending.R;
import com.trends.trending.adapter.SlidePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mGreetUser.setText(greetText()+" MR. NEERAJ!!");
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
    }

    private String greetText() {

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            return GOOD_MORNING;
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            return GOOD_AFTERNOON;
        }else{
            return GOOD_EVENING;
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


}
