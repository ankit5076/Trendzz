package com.trends.trending.model;

public class PlaceModel {

    private String placeName;

    private String placeInfo;

    private String placeRating;
    private String placeImage;


    public PlaceModel(String placeName, String placeInfo, String placeRating, String placeImage) {
        this.placeName = placeName;
        this.placeInfo = placeInfo;
        this.placeRating = placeRating;
        this.placeImage = placeImage;
    }

    public PlaceModel() {

    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

    public String getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(String placeRating) {
        this.placeRating = placeRating;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(String placeInfo) {
        this.placeInfo = placeInfo;
    }
}
