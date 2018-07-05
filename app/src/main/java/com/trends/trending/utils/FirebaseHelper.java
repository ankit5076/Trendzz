package com.trends.trending.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.trends.trending.adapter.FactPagerAdapter;
import com.trends.trending.adapter.QuotePagerAdapter;
import com.trends.trending.model.FactModel;
import com.trends.trending.model.QuoteModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by USER on 3/10/2018.
 */

public class FirebaseHelper {

    private DatabaseReference mDatabaseReference;
    private Context mContext;
    private List<QuoteModel> quotes = new ArrayList<>();
    private List<FactModel> facts = new ArrayList<>();

    public FirebaseHelper(DatabaseReference databaseReference, Context context) {
        mDatabaseReference = databaseReference;
        mContext = context;
    }

    public Boolean uploadQuote(QuoteModel quote, String node) {
        Boolean isSaved;
        if (quote == null || quote.getAuthorName().trim().length()==0 || quote.getFamousQuote().trim().length()==0 || quote.getUploadedBy().trim().length()==0) isSaved = false;
        else {
            try {
                mDatabaseReference.child(node).push().setValue(quote);
                isSaved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                isSaved = false;
            }
        }
        return isSaved;
    }

    public QuotePagerAdapter fetchQuotes(DataSnapshot dataSnapshot) {
        QuotePagerAdapter quotePagerAdapter = new QuotePagerAdapter(mContext);
        if (dataSnapshot.exists()) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                QuoteModel quote = ds.getValue(QuoteModel.class);
                quotes.add(quote);
                //quotePagerAdapter.addCardItem(quote);
            }
            Collections.shuffle(quotes);
            for (QuoteModel quote : quotes) {
                quotePagerAdapter.addCardItem(quote);
            }
        }
        return quotePagerAdapter;
    }

    public FactPagerAdapter fetchfacts(DataSnapshot dataSnapshot) {
        FactPagerAdapter factPagerAdapter = new FactPagerAdapter(mContext);
        if (dataSnapshot.exists()) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                FactModel fact = ds.getValue(FactModel.class);
                facts.add(fact);
                Log.d("factttttt:", "fetchfacts: "+ fact.getFactContent());
//                Log.d("facttttttttttt:", "fetchfacts: "+ dataSnapshot.toString());
//                Log.d("facttttttttttttttt:", "fetchfacts: "+ dataSnapshot.getChildren().toString());
//                //quotePagerAdapter.addCardItem(quote);^
            }
            Collections.shuffle(facts);
            for (FactModel factModel : facts) {
                factPagerAdapter.addCardItem(factModel);
            }
        }
        return factPagerAdapter;
    }
}
