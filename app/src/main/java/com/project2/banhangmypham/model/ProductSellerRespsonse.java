package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductSellerRespsonse {
    @SerializedName("data")
    private List<ProductHotSeller> productHotSellerList = new ArrayList<>();
    private boolean isSuccess;
    private String error ;

    public List<ProductHotSeller> getProductHotSellerList() {
        return productHotSellerList;
    }

    public void setProductHotSellerList(List<ProductHotSeller> productHotSellerList) {
        this.productHotSellerList = productHotSellerList;
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
