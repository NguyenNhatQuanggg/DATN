package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

public class AccountResponse {
    private int code;
    private User user;
    private String token;
    private boolean data ;
    private String message ;

    public AccountResponse() {
    }

    public String getToken() {
        return token;
    }

    public boolean isData() {
        return data;
    }

    public AccountResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
