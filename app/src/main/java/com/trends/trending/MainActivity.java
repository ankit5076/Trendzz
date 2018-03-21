package com.trends.trending;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.trends.trending.model.youtube.Parent;
import com.trends.trending.repository.VideoRepository;
import com.trends.trending.ui.FamousQuote;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.trends.trending.utils.Keys.VideoInfo.KEY_BOLLYWOOD_TRAILERS;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //mButton =  findViewById(R.id.quoteAct);

        VideoRepository videoRepository = new VideoRepository();
        Parent parent = videoRepository.getSearchResults(KEY_BOLLYWOOD_TRAILERS);
        if (parent != null) {
            Toast.makeText(MainActivity.this, parent.getItems().get(0).getSnippet().getTitle(), Toast.LENGTH_LONG).show();
        }

    }

    public void goToUpload(View view) {
        startActivity(new Intent(this, DummyUploadQuote.class));
    }

    public void goToQuote(View view) {
        startActivity(new Intent(this, FamousQuote.class));
    }

}
