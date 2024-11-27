package com.project2.banhangmypham.model;

import java.util.ArrayList;
import java.util.List;

public class StaticResponse {

    private StaticModel data ;
    private boolean isSuccess ;
    private String error ;

    public StaticModel getData() {
        return data;
    }

    public void setData(StaticModel data) {
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
