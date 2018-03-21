package com.trends.trending.model;

/**
 * Created by USER on 3/5/2018.
 */

public class Quote {

    private String authorName;
    private String famousQuote;

    public Quote() {
    }

    public Quote(String authorName, String famousQuote) {
        this.authorName = authorName;
        this.famousQuote = famousQuote;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFamousQuote() {
        return famousQuote;
    }

    public void setFamousQuote(String famousQuote) {
        this.famousQuote = famousQuote;
    }
}
