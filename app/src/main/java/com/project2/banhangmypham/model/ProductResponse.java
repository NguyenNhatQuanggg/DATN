package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductResponse  implements Serializable {
    @SerializedName("data")
    private List<Product> data = new ArrayList<>();
    @SerializedName("isSuccess")
    private boolean isSuccess = false;
    @SerializedName("error")
    private String error ;

    public ProductResponse(List<Product> data, boolean isSuccess, String error) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
