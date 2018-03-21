package com.trends.trending.utils;

import android.content.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.trends.trending.adapter.QuotePagerAdapter;
import com.trends.trending.model.Quote;

/**
 * Created by USER on 3/10/2018.
 */

public class FirebaseHelper {

    private DatabaseReference mDatabaseReference;
    private Context mContext;

    public FirebaseHelper(DatabaseReference databaseReference, Context context) {
        mDatabaseReference = databaseReference;
        mContext = context;
    }

    public Boolean uploadQuote(Quote quote) {
        Boolean isSaved;
        if (quote == null) isSaved = false;
        else {
            try {
                mDatabaseReference.child("quotes").push().setValue(quote);
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
                Quote quote = ds.getValue(Quote.class);
                quotePagerAdapter.addCardItem(quote);
            }
        }
        return quotePagerAdapter;
    }
}
