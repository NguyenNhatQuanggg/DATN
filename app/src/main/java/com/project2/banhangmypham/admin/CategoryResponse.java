package com.project2.banhangmypham.admin;

import com.google.gson.annotations.SerializedName;
import com.project2.banhangmypham.model.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryResponse implements Serializable {
    @SerializedName("data")
   private List<Category> data = new ArrayList<>();
    @SerializedName("isSuccess")
   private boolean isSuccess = false;
    @SerializedName("error")
    private String error ;

    public CategoryResponse(List<Category> data, boolean isSuccess, String error) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
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
