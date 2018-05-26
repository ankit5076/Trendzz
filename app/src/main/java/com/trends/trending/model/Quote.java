package com.trends.trending.model;

/**
 * Created by USER on 3/5/2018.
 */

public class Quote {

    private String authorName;
    private String famousQuote;
    private String uploadedBy;

    public Quote() {
    }

    public Quote(String authorName, String famousQuote, String uploadedBy) {
        this.authorName = authorName;
        this.famousQuote = famousQuote;
        this.uploadedBy = uploadedBy;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
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
