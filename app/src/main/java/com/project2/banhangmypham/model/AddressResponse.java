package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddressResponse {
    @SerializedName("data")
    private List<AddressTransfer> addressResponseList = new ArrayList<>();
    private boolean isSuccess;
    private String error ;

    public List<AddressTransfer> getAddressResponseList() {
        return addressResponseList;
    }

    public void setAddressResponseList(List<AddressTransfer> addressResponseList) {
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
