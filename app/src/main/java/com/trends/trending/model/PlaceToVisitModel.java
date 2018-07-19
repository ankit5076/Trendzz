package com.trends.trending.model;

import com.google.gson.annotations.SerializedName;

public class PlaceToVisitModel {

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
}
