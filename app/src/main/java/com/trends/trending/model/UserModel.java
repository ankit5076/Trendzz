package com.trends.trending.model;

public class UserModel {

    private String name;
    private String email;
    private String quoteStartIndex;

    public UserModel() {
    }

    public UserModel(String userName, String userEmail, String userQuoteStartIndex) {
        this.name = userName;
        this.email = userEmail;
        this.quoteStartIndex = userQuoteStartIndex;
    }

    public String getUserName() {
        return name;
    }

    public void setUserName(String userName) {
        this.name = userName;
    }

    public String getUserEmail() {
        return email;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

    public String getUserQuoteStartIndex() {
        return quoteStartIndex;
    }

    public void setUserQuoteStartIndex(String userQuoteStartIndex) {
        this.quoteStartIndex = userQuoteStartIndex;
    }
}
