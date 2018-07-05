package com.trends.trending.model.youtube;

/**
 * Created by USER on 3/4/2018.
 */
public class Item {

    private String id;
    private Snippet snippet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

}

