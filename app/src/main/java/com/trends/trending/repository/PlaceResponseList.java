package com.trends.trending.repository;

import com.google.gson.annotations.SerializedName;
import com.trends.trending.model.PlaceToVisitModel;

import java.util.List;

import static com.trends.trending.utils.Keys.VideoInfo.BEST_PLACES;

public class PlaceResponseList {

    @SerializedName(BEST_PLACES)
    private List<PlaceToVisitModel> bestPlaces;


    public List<PlaceToVisitModel> getBestPlaces() {
        return bestPlaces;
    }

    public void setBestPlaces(List<PlaceToVisitModel> bestPlaces) {
        this.bestPlaces = bestPlaces;
    }
}
