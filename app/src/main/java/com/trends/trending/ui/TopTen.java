package com.trends.trending.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trends.trending.R;
import com.trends.trending.model.youtube.TopTenModel;
import com.trends.trending.repository.BollywoodSongsResponseList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.trends.trending.utils.Keys.VideoInfo.BOLLYWOOD_TOP_SONGS;
import static com.trends.trending.utils.Keys.VideoInfo.MOST_VIEWED;

public class TopTen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        Gson gson = new Gson();
        if (parseJson() != null) {
            BollywoodSongsResponseList bollywoodSongsResponseList = gson.fromJson(parseJson(), BollywoodSongsResponseList.class);
            for (TopTenModel topTenModel : bollywoodSongsResponseList.getBollywoodTopSongs()
                    ) {
                Toast.makeText(this, topTenModel.getName(), Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
    }

    public String parseJson() {
        String json;
        try {
            InputStream inputStream = getAssets().open(MOST_VIEWED);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
