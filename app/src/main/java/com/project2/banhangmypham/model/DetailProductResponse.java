package com.project2.banhangmypham.model;

import java.util.ArrayList;
import java.util.List;

public class DetailProductResponse {
    private List<CartProductRequest> addressResponseList = new ArrayList<>();
    private boolean isSuccess;
    private String error ;

    public DetailProductResponse(List<CartProductRequest> addressResponseList, boolean isSuccess, String error) {
        this.addressResponseList = addressResponseList;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public List<CartProductRequest> getAddressResponseList() {
        return addressResponseList;
    }

    public void setAddressResponseList(List<CartProductRequest> addressResponseList) {
        this.addressResponseList = addressResponseList;
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
