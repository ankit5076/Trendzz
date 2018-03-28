package com.trends.trending.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trends.trending.R;
import com.trends.trending.utils.ShadowTransformer;
import com.trends.trending.adapter.QuotePagerAdapter;
import com.trends.trending.utils.FirebaseHelper;
import com.trends.trending.utils.NetworkHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 3/7/2018.
 */

public class FamousQuote extends AppCompatActivity {

    public static final String TAG = FamousQuote.class.getSimpleName();

    private ViewPager mViewPager;
    private QuotePagerAdapter mCardAdapter;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseHelper mFirebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_quote);
    //    ButterKnife.bind(this);
        mViewPager = findViewById(R.id.viewPager);
        mCardAdapter = new QuotePagerAdapter(FamousQuote.this);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("quotes");
        mFirebaseHelper = new FirebaseHelper(mFirebaseDatabase, FamousQuote.this);

        if(NetworkHelper.hasNetworkAccess(this))
            fetchQuote();
        else
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
    }

    private void fetchQuote() {

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCardAdapter = mFirebaseHelper.fetchQuotes(dataSnapshot);
                displayData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        ShadowTransformer cardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        cardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, cardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
