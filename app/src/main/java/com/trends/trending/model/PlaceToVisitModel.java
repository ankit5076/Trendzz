package com.trends.trending.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaceToVisitModel implements Parcelable {

    private String placeName;
    @SerializedName("rating")
    private String placeRating;
    @SerializedName("state")
    private String placeState;
    @SerializedName("imageUrl")
    private String placeImageUrl;
    private String bestTimeToVisit;
    @SerializedName("summary")
    private String aboutPlace;
    @SerializedName("places")
    private String places;

    public PlaceToVisitModel(String placeName, String placeRating, String placeState, String placeImageUrl, String bestTimeToVisit, String aboutPlace, String places) {
        this.placeName = placeName;
        this.placeRating = placeRating;
        this.placeState = placeState;
        this.placeImageUrl = placeImageUrl;
        this.bestTimeToVisit = bestTimeToVisit;
        this.aboutPlace = aboutPlace;
        this.places = places;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(String placeRating) {
        this.placeRating = placeRating;
    }

    public String getPlaceState() {
        return placeState;
    }

    public void setPlaceState(String placeState) {
        this.placeState = placeState;
    }

    public String getPlaceImageUrl() {
        return placeImageUrl;
    }

    public void setPlaceImageUrl(String placeImageUrl) {
        this.placeImageUrl = placeImageUrl;
    }

    public String getBestTimeToVisit() {
        return bestTimeToVisit;
    }

    public void setBestTimeToVisit(String bestTimeToVisit) {
        this.bestTimeToVisit = bestTimeToVisit;
    }

    public String getAboutPlace() {
        return aboutPlace;
    }

    public void setAboutPlace(String aboutPlace) {
        this.aboutPlace = aboutPlace;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.placeName);
        dest.writeString(this.placeRating);
        dest.writeString(this.placeState);
        dest.writeString(this.placeImageUrl);
        dest.writeString(this.bestTimeToVisit);
        dest.writeString(this.aboutPlace);
        dest.writeString(this.places);
    }

    protected PlaceToVisitModel(Parcel in) {
        this.placeName = in.readString();
        this.placeRating = in.readString();
        this.placeState = in.readString();
        this.placeImageUrl = in.readString();
        this.bestTimeToVisit = in.readString();
        this.aboutPlace = in.readString();
        this.places = in.readString();
    }

    public static final Parcelable.Creator<PlaceToVisitModel> CREATOR = new Parcelable.Creator<PlaceToVisitModel>() {
        @Override
        public PlaceToVisitModel createFromParcel(Parcel source) {
            return new PlaceToVisitModel(source);
        }

        @Override
        public PlaceToVisitModel[] newArray(int size) {
            return new PlaceToVisitModel[size];
        }
    };
}
