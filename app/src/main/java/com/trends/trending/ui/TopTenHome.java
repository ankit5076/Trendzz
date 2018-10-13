package com.trends.trending.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.trends.trending.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.trends.trending.utils.Keys.TopTen.BOLLYWOOD;
import static com.trends.trending.utils.Keys.TopTen.HOLLYWOOD;
import static com.trends.trending.utils.Keys.TopTen.OLD_IS_GOLD;
import static com.trends.trending.utils.Keys.TopTen.SONG_TYPE;

public class TopTenHome extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bollywood_text, R.id.hollywood_text, R.id.old_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bollywood_text:
                startActivity(BOLLYWOOD,TopTen.class);
                break;
            case R.id.hollywood_text:
                startActivity(HOLLYWOOD,TopTen.class);
                break;
            case R.id.old_text:
                startActivity(OLD_IS_GOLD,TopTen.class);
                break;
        }
    }

    private void startActivity(String name, final Class<? extends Activity> clz) {
        Intent intent = new Intent(TopTenHome.this, clz);
        intent.putExtra(SONG_TYPE, name);
        startActivity(intent);
    }
}
