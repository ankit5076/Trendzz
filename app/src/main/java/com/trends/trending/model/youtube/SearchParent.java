package com.trends.trending.model.youtube;

/**
 * Created by USER on 3/4/2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SearchParent implements Parcelable {

    private List<SearchItem> items = null;



    public List<SearchItem> getItems() {
        return items;
    }

    public void setItems(List<SearchItem> items) {
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

    public SearchParent() {
    }

    protected SearchParent(Parcel in) {
        this.items = new ArrayList<SearchItem>();
        in.readList(this.items, SearchItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchParent> CREATOR = new Parcelable.Creator<SearchParent>() {
        @Override
        public SearchParent createFromParcel(Parcel source) {
            return new SearchParent(source);
        }

        @Override
        public SearchParent[] newArray(int size) {
            return new SearchParent[size];
        }
    };
}
