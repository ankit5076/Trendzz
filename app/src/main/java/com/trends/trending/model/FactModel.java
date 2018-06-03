package com.trends.trending.model;

/**
 * Created by USER on 3/5/2018.
 */

public class FactModel {

    private String factContent;
    private String imageURL;
    private String imageName;

    public FactModel() {
    }

    public FactModel(String url, String fact) {
        this.imageURL = url;
        this.factContent = fact;
    }

    public String getFactContent() {
        return factContent;
    }

    public void setFactContent(String factContent) {
        this.factContent = factContent;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
