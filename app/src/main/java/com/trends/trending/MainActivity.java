package com.trends.trending;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.trends.trending.model.youtube.Parent;
import com.trends.trending.service.ReturnReceiver;
import com.trends.trending.ui.FamousQuote;

import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;

public class MainActivity extends AppCompatActivity implements ReturnReceiver.Receiver {

//    Button mButton;
//    @BindView(R.id.quoteAct)
//    Button mQuoteAct;

    ReturnReceiver mReturnReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {bil
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mReturnReceiver = new ReturnReceiver(new Handler());
//        mReturnReceiver.setReceiver(this);
//
//        Intent parent1 = new Intent(this, VideoRepository.class);
//        parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
//        //parent1.putExtra(KEY_INTENT, VAL_SEARCH);
//        //parent1.putExtra(KEY_SEARCH, KEY_BOLLYWOOD_TRAILERS);
//        //parent1.putExtra(KEY_INTENT, VAL_TRENDING);
//        //parent1.putExtra(KEY_INTENT, VAL_CHANNEL_PLAYLIST);
//        //parent1.putExtra(KEY_CHANNEL_PLAYLIST_ID, "UCq-Fj5jknLsUf-MWSy4_brA");
//        parent1.putExtra(KEY_INTENT, VAL_PLAYLIST_VIDEOS);
//        parent1.putExtra(KEY_PLAYLIST_ID, "PL9bw4S5ePsEG47QE3VB9Uv7Uu63naEP2m");
//        this.startService(parent1);

    }


    public void goToUpload(View view) {
        startActivity(new Intent(this, DummyUploadQuote.class));
    }

    public void goToQuote(View view) {
        startActivity(new Intent(this, FamousQuote.class));
    }

    public void goToVideos(View view) {
        Intent intent = new Intent(this, ActivityVideos.class);
        startActivity(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        Parent parent = resultData.getParcelable(KEY_PARENT);

        if (parent != null) {
            Toast.makeText(MainActivity.this, parent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "MainActivity null", Toast.LENGTH_LONG).show();

    }
}
