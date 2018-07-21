package com.trends.trending.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trends.trending.R;
import com.trends.trending.model.PlaceToVisitModel;
import com.trends.trending.utils.ExtraHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.trends.trending.utils.Keys.PlaceInfo.KEY_PLACE_OBJECT;

public class PlaceDetail extends AppCompatActivity {


    @BindView(R.id.share_place)
    ImageView mSharePlace;
    @BindView(R.id.place_names)
    TextView mPlaceNames;
    @BindView(R.id.aboutPlace)
    TextView mAboutPlace;
    @BindView(R.id.adViewPlaceDetail)
    AdView mAdViewPlaceDetail;

    private PlaceToVisitModel mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPlace = bundle.getParcelable(KEY_PLACE_OBJECT);
        } else
            mPlace = null;

        ExtraHelper.bannerAdViewSetup(mAdViewPlaceDetail);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mPlace.getPlaceName());
//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        Picasso.get().load(mPlace.getPlaceImageUrl())
                .placeholder(R.drawable.loading).error(R.drawable.aaj_tak).into((ImageView) findViewById(R.id.place_detail_image));
        mPlaceNames.setText(mPlace.getPlaces().replace(",", "#"));
        mAboutPlace.setText(mPlace.getAboutPlace());

    }

    public void expandableButton4(View view) {
        ExpandableRelativeLayout expandableLayout4 = findViewById(R.id.expandableAbout);
        expandableLayout4.toggle(); // toggle expand and collapse
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @OnClick({R.id.share_place})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_place:
                Picasso.get().load(mPlace.getPlaceImageUrl()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                        i.putExtra(Intent.EXTRA_TEXT, mPlace.getPlaceName());
                        startActivity(Intent.createChooser(i, "Share this place"));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
                break;
        }
    }
}
