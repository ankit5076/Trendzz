package com.trends.trending;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.trends.trending.model.youtube.Parent;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.service.ReturnReceiver;
import com.trends.trending.ui.FamousQuote;

import butterknife.ButterKnife;

import static com.trends.trending.utils.Keys.VideoInfo.KEY_BOLLYWOOD_TRAILERS;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_INTENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_PARENT;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_RECEIVER;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_SEARCH;
import static com.trends.trending.utils.Keys.VideoInfo.VAL_SEARCH;

public class MainActivity extends AppCompatActivity implements ReturnReceiver.Receiver {

//    Button mButton;
//    @BindView(R.id.quoteAct)
//    Button mQuoteAct;

    ReturnReceiver mReturnReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mReturnReceiver = new ReturnReceiver(new Handler());
        mReturnReceiver.setReceiver(this);

        Intent parent1 = new Intent(this, VideoRepository.class);
        parent1.putExtra(KEY_RECEIVER, mReturnReceiver);
        parent1.putExtra(KEY_INTENT, VAL_SEARCH);
        parent1.putExtra(KEY_SEARCH, KEY_BOLLYWOOD_TRAILERS);
        this.startService(parent1);

    }


    public void goToUpload(View view) {
        startActivity(new Intent(this, DummyUploadQuote.class));
    }

    public void goToQuote(View view) {
        startActivity(new Intent(this, FamousQuote.class));
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
