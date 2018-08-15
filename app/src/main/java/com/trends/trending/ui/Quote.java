package com.trends.trending.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.trends.trending.R;
import com.trends.trending.adapter.QuotePagerAdapter;
import com.trends.trending.utils.FirebaseHelper;
import com.trends.trending.utils.NetworkHelper;
import com.trends.trending.utils.SessionManagement;
import com.trends.trending.utils.ShadowTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.trends.trending.utils.Keys.QuoteInfo.FB_QUOTE;
import static com.trends.trending.utils.Keys.QuoteInfo.FB_QUOTE_ID_CHILD;
import static com.trends.trending.utils.Keys.QuoteInfo.FB_RM_GUEST_START;
import static com.trends.trending.utils.Keys.QuoteInfo.GUEST_TOTAL_QUOTES_TO_DISPLAY;
import static com.trends.trending.utils.Keys.QuoteInfo.LOGGED_IN_TOTAL_QUOTES_TO_DISPLAY;

/**
 * Created by USER on 3/7/2018.
 */

public class Quote extends AppCompatActivity {

    public static final String TAG = Quote.class.getSimpleName();

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar_quote)
    Toolbar toolbar;
    @BindView(R.id.shimmer_view_quote_container)
    ShimmerFrameLayout mShimmerViewContainer;

    private QuotePagerAdapter mCardAdapter;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseHelper mFirebaseHelper;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAuth mFirebaseAuth;
    private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetworkHelper.hasNetworkAccess(this)) {
            setContentView(R.layout.activity_famous_quote);
            ButterKnife.bind(this);
            init();
            if (sessionManagement.isLoggedIn()) {

                // todo current date equal to previous date
                if (sessionManagement.isNewUser()) {
                    fetchQuote();
                } else {
                    fetchIndex();
                }
            }
            else {
                fetchQuote();
            }
        } else {
            setContentView(R.layout.shared_no_wifi);
        }

    }

    private void fetchIndex() {

        String uid = mFirebaseAuth.getCurrentUser().getUid();

        mFirebaseDatabase.child("users")
                .orderByChild(uid)
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sessionManagement.updateQuoteStartIndex(mFirebaseHelper.getUserQuoteStartIndex(dataSnapshot));
                        fetchQuote();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void init() {
        setSupportActionBar(toolbar);
        mShimmerViewContainer.startShimmerAnimation();
        sessionManagement = new SessionManagement(Quote.this);
        mCardAdapter = new QuotePagerAdapter(Quote.this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseHelper = new FirebaseHelper(mFirebaseDatabase, Quote.this);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

//            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
//                    .build();
//            mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.user);
        mFirebaseRemoteConfig.fetch()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        }
                    }
                });
    }

    private void fetchQuote() {

        String startValue;
        String endValue;

        if (sessionManagement.isLoggedIn()) {
            startValue = sessionManagement.getQuoteStartIndex();
            endValue = String.valueOf((LOGGED_IN_TOTAL_QUOTES_TO_DISPLAY + Integer.parseInt(startValue) - 1));
        } else {
            startValue = mFirebaseRemoteConfig.getString(FB_RM_GUEST_START);
            endValue = String.valueOf((GUEST_TOTAL_QUOTES_TO_DISPLAY + Integer.parseInt(startValue) - 1));
        }

        Query randomQuotes = mFirebaseDatabase.child(FB_QUOTE).orderByChild(FB_QUOTE_ID_CHILD)
                .startAt(startValue)
                .endAt(endValue);
        randomQuotes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                Log.d("data_snapshot: ", dataSnapshot.toString());
                mCardAdapter = mFirebaseHelper.fetchQuotes(dataSnapshot);
                displayData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

                Toast.makeText(Quote.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void displayData() {
        ShadowTransformer cardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        cardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, cardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @OnClick(R.id.toolbar_user_upload)
    public void onViewClicked() {
        startActivity(new Intent(Quote.this, UserUploadQuote.class));
    }
}
