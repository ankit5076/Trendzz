package com.trends.trending.model.youtube;

/**
 * Created by USER on 3/4/2018.
 */
public class SearchItem {

    private Id id;
    private Snippet snippet;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

}

