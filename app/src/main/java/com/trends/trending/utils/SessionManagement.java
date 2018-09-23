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
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String QUOTE_START_INDEX = "QuoteStartIndex";
    private static final String NEW_USER = "NewUser";
    private static final String LAST_VISITED_QUOTE_DATE = "LastVisitedQuote";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

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

    public void createLoginSession(String name, String email) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);

        editor.commit();
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

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return user;
    }

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {

            Intent i = new Intent(_context, Home.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, Home.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public Date getLastVisitedQuoteDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(pref.getString(LAST_VISITED_QUOTE_DATE, "11/01/2018"));
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isNewUser() {
        return pref.getBoolean(NEW_USER, false);
    }

    public String getQuoteStartIndex() {
        return pref.getString(QUOTE_START_INDEX, "1");
    }

}
