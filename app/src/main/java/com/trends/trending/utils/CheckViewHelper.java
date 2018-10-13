package com.trends.trending.utils;

import android.widget.EditText;

public class CheckViewHelper {

    public static boolean isEmptyEditText(EditText editText){
        return editText.getText().toString().trim().equals("") || editText.getText().toString().length() == 0;
    }

}
