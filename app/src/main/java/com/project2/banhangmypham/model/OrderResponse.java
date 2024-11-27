package com.project2.banhangmypham.model;

import java.util.ArrayList;
import java.util.List;

public class OrderResponse {
    private List<OrderRequest> data = new ArrayList<>();
    private boolean isSuccess;
    private String error ;

    public List<OrderRequest> getData() {
        return data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }
}
