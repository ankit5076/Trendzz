package com.trends.trending.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;

import com.trends.trending.ui.Home;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SessionManagement {

    private static final String PREF_NAME = "Trendzz";
    private static final String QUOTE_START_INDEX = "QuoteStartIndex";
    private static final String NEW_USER = "NewUser";
    private static final String LAST_VISITED_QUOTE_DATE = "LastVisitedQuote";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;

    @SuppressLint("CommitPrefEdits")
    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void updateQuoteStartIndex(String index) {
        editor.putString(QUOTE_START_INDEX, index);

        editor.commit();
    }

    public void updateLastVisitedQuoteDate(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String dateTime = dateFormat.format(date);
            editor.putString(LAST_VISITED_QUOTE_DATE, dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // todo update this when user is logged in every time by checking user existence in firebase
    public void updateNewUser(boolean newUser) {
        editor.putBoolean(NEW_USER, newUser);

        editor.commit();
    }

    public Date getLastVisitedQuoteDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(pref.getString(LAST_VISITED_QUOTE_DATE, null));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public boolean isNewUser() {
        return pref.getBoolean(NEW_USER, false);
    }

    public String getQuoteStartIndex() {
        return pref.getString(QUOTE_START_INDEX, "1");
    }

}
