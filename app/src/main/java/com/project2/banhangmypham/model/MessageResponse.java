package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    private int code;
    private boolean data ;
    @SerializedName("error")
    private String message ;

    public MessageResponse() {
    }

    public MessageResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
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
