package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kotlin.Triple;

public class DiscountResponse {
    @SerializedName("data")
    private List<DiscountModel> discountModelList = new ArrayList<>();
    private boolean isSuccess ;
    private String error ;

    public DiscountResponse(List<DiscountModel> discountModelList, boolean isSuccess, String error) {
        this.discountModelList = discountModelList;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public List<DiscountModel> getDiscountModelList() {
        return discountModelList;
    }

    public void setDiscountModelList(List<DiscountModel> discountModelList) {
        this.discountModelList = discountModelList;
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
