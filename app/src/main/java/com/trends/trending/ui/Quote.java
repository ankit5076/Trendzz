package com.trends.trending.ui;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private boolean updateIndex = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetworkHelper.hasNetworkAccess(this)) {
            setContentView(R.layout.activity_famous_quote);
            ButterKnife.bind(this);
            init();

            if (mFirebaseAuth.getCurrentUser() != null) {
                Date currentDate = currentDate();
                Date lastVisitedDate = sessionManagement.getLastVisitedQuoteDate();

//                Log.d(TAG, sessionManagement.isNewUser() + " new user?");
//                Log.d(TAG, currentDate + " current date");
//                Log.d(TAG, lastVisitedDate + " prev date");
//                Log.d(TAG, currentDate.compareTo(lastVisitedDate) + " compare");

                if (lastVisitedDate == null) {
                    sessionManagement.updateLastVisitedQuoteDate(new Date());
                    updateIndex = true;
                    fetchIndex();
                } else if ((currentDate.compareTo(lastVisitedDate) == 0 || currentDate.compareTo(lastVisitedDate) < 0)) {
                    updateIndex = false;
                    Toast.makeText(this, "fetch quote", Toast.LENGTH_SHORT).show();
                    fetchQuote();
                } else {
                    updateIndex = true;
                    sessionManagement.updateLastVisitedQuoteDate(new Date());
                    Toast.makeText(this, "fetch index", Toast.LENGTH_SHORT).show();
                    fetchIndex();
                }
            } else {
                Toast.makeText(this, "annonyms fetch quote", Toast.LENGTH_SHORT).show();
                updateIndex = false;
                fetchQuote();
            }
        } else {
            setContentView(R.layout.shared_no_wifi);
        }

    }

    private Date currentDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String today = dateFormat.format(new Date());
        Date date = null;
        try {
            date = dateFormat.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void fetchIndex() {

        String uid = mFirebaseAuth.getCurrentUser().getUid();

        mFirebaseDatabase.child("users")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sessionManagement.updateQuoteStartIndex(mFirebaseHelper.getUserQuoteStartIndex(dataSnapshot));
                        Toast.makeText(Quote.this, "saved: "+sessionManagement.getQuoteStartIndex(), Toast.LENGTH_LONG).show();
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
        mCardAdapter = new QuotePagerAdapter(Quote.this);
        sessionManagement = new SessionManagement(Quote.this);
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

        long startValue;
        final long endValue;

        if (mFirebaseAuth.getCurrentUser() != null) {
            startValue = Long.parseLong(sessionManagement.getQuoteStartIndex());
            endValue = (LOGGED_IN_TOTAL_QUOTES_TO_DISPLAY + startValue - 1);
        } else {
            startValue = Long.parseLong(mFirebaseRemoteConfig.getString(FB_RM_GUEST_START));
            endValue = (GUEST_TOTAL_QUOTES_TO_DISPLAY + startValue - 1);
        }

        Toast.makeText(this, "start: " + startValue, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "end: " + endValue, Toast.LENGTH_SHORT).show();
//        Query randomQuotes = mFirebaseDatabase.child(FB_QUOTE).orderByChild(FB_QUOTE_ID_CHILD)
//                .startAt(startValue)
//                .endAt(endValue);
        Query randomQuotes = mFirebaseDatabase.child(FB_QUOTE).orderByChild("id")
                .startAt(startValue)
                .endAt(endValue);
        randomQuotes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                mCardAdapter = mFirebaseHelper.fetchQuotes(dataSnapshot);
                displayData();
                if (updateIndex) {
//                    sessionManagement.updateQuoteStartIndex(String.valueOf(Integer.parseInt(endValue) + 1));
                    FirebaseHelper firebaseHelper = new FirebaseHelper(mFirebaseDatabase, Quote.this);
                    if (firebaseHelper.updateUserQuoteStartIndex(mFirebaseAuth.getCurrentUser().getUid(), endValue + 1)) {
                        Log.d(TAG, "onDataChange: updated index");
                    }
                }
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

    @Override
    protected void onStart() {
        super.onStart();
    }
}
