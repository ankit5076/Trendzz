package com.trends.trending.utils;

import android.content.Context;
import android.os.Parcel;

import com.plumillonforge.android.chipview.Chip;

/**
 * Created by ankit.a.vishwakarma on 5/8/2018.
 */

public class Tag implements Chip {
    private String mName;
    private int mType = 0;

    public Tag(String name, int type) {
        this(name);
        mType = type;
    }

    public Tag(String name) {
        mName = name;
    }

    @Override
    public String getText() {
        return mName;
    }

    public int getType() {
        return mType;
    }
}

