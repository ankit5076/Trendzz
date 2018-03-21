package com.trends.trending.model.youtube;

/**
 * Created by USER on 3/4/2018.
 */
public class Thumbnails {

    private Default _default;
    private Medium medium;
    private High high;



    public Default getDefault() {
        return _default;
    }

    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

}
