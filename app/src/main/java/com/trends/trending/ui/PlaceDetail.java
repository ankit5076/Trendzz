package com.trends.trending.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trends.trending.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

import static com.trends.trending.utils.Keys.PlaceInfo.KEY_PLACE_IMAGE;
import static com.trends.trending.utils.Keys.PlaceInfo.KEY_PLACE_NAME;

public class PlaceDetail extends AppCompatActivity {


    @BindView(R.id.share_place)
    ImageView mSharePlace;
    String placeTitle;
    String placeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        placeTitle = intent.getExtras().getString(KEY_PLACE_NAME);
        placeImage = intent.getExtras().getString(KEY_PLACE_IMAGE);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(placeTitle);
//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        Picasso.get().load(placeImage)
                .placeholder(R.drawable.loading).error(R.drawable.aaj_tak).into((ImageView) findViewById(R.id.place_detail_image));
    }

    @OnClick(R.id.share_place)
    public void onViewClicked() {
        Picasso.get().load(placeImage).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                i.putExtra(Intent.EXTRA_TEXT, placeTitle);
                startActivity(Intent.createChooser(i, "Share this place"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public void expandableButton4(View view) {
        ExpandableRelativeLayout expandableLayout4 = findViewById(R.id.expandableAbout);
        expandableLayout4.toggle(); // toggle expand and collapse
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

}
