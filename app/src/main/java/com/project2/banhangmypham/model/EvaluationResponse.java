package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EvaluationResponse {
    @SerializedName("data")
    private List<EvaluationRequestModel> evaluationRequestModelList = new ArrayList<>();
    private boolean isSuccess;
    private String error ;

    public List<EvaluationRequestModel> getEvaluationRequestModelList() {
        return evaluationRequestModelList;
    }

    public void setAddressResponseList(List<EvaluationRequestModel> addressResponseList) {
        this.evaluationRequestModelList = addressResponseList;
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
