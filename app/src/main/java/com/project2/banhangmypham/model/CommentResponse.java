package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommentResponse {
    @SerializedName("data")
    private List<EvaluationResponseAdmin> evaluationResponseList = new ArrayList<>();
    private boolean isSuccess;
    private String error ;

    public List<EvaluationResponseAdmin> getEvaluationResponseList() {
        return evaluationResponseList;
    }

    public void setEvaluationResponseList(List<EvaluationResponseAdmin> evaluationResponseList) {
        this.evaluationResponseList = evaluationResponseList;
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
