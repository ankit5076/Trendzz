package com.trends.trending.model.youtube;

/**
 * Created by USER on 3/4/2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Parent implements Parcelable {

    private List<Item> items = null;



    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.items);
    }

    public Parent() {
    }

    protected Parent(Parcel in) {
        this.items = new ArrayList<Item>();
        in.readList(this.items, Item.class.getClassLoader());
    }

    public static final Parcelable.Creator<Parent> CREATOR = new Parcelable.Creator<Parent>() {
        @Override
        public Parent createFromParcel(Parcel source) {
            return new Parent(source);
        }

        @Override
        public Parent[] newArray(int size) {
            return new Parent[size];
        }
    };
}
