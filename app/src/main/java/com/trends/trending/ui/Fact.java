package com.trends.trending.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.trends.trending.R;
import com.trends.trending.adapter.FactPagerAdapter;
import com.trends.trending.adapter.QuotePagerAdapter;
import com.trends.trending.utils.FactShadowTransformer;
import com.trends.trending.utils.FirebaseHelper;
import com.trends.trending.utils.NetworkHelper;
import com.trends.trending.utils.ShadowTransformer;

/**
 * Created by USER on 3/7/2018.
 */

public class Fact extends AppCompatActivity {

    public static final String TAG = Fact.class.getSimpleName();

    private ViewPager mViewPager;
    private FactPagerAdapter mCardAdapter;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseHelper mFirebaseHelper;
    private Toolbar toolbar;
    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_quote);
    //    ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar_quote);
        setSupportActionBar(toolbar);
        MaterialFancyButton btnUserQuoteUpload = findViewById(R.id.toolbar_user_upload);
        mViewPager = findViewById(R.id.viewPager);
        mCardAdapter = new FactPagerAdapter(Fact.this);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("fact");
        mFirebaseHelper = new FirebaseHelper(mFirebaseDatabase, Fact.this);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_quote_container);
        mShimmerViewContainer.startShimmerAnimation();

        if(NetworkHelper.hasNetworkAccess(this))
            fetchFact();
        else {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
        }
        btnUserQuoteUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Fact.this, UserUploadQuote.class));
            }
        });
    }

    private void fetchFact() {

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                mCardAdapter = mFirebaseHelper.fetchfacts(dataSnapshot);
               // Log.d(TAG, "onDataChange: "+mCardAdapter.getFactItem(0).getFact());
                displayData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                Toast.makeText(Fact.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
//
//        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                mCardAdapter = mFirebaseHelper.fetchQuotes(dataSnapshot);
//                displayData();
//            }
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                mCardAdapter = mFirebaseHelper.fetchQuotes(dataSnapshot);
//                displayData();
//            }
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                mCardAdapter = mFirebaseHelper.fetchQuotes(dataSnapshot);
//                displayData();
//            }
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                mCardAdapter = mFirebaseHelper.fetchQuotes(dataSnapshot);
//                displayData();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: ");
//            }
//        });
    }

    private void displayData(){
        FactShadowTransformer cardShadowTransformer = new FactShadowTransformer(mViewPager, mCardAdapter);
        cardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, cardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        return;
    }
}
