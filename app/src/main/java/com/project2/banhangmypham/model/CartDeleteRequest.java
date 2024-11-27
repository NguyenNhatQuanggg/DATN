package com.project2.banhangmypham.model;

import java.util.ArrayList;
import java.util.List;

public class CartDeleteRequest {
    private String userId ;
    private List<CartProductRequest> data = new ArrayList<>();

    public CartDeleteRequest(String userId, List<CartProductRequest> data) {
        this.userId = userId;
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartProductRequest> getData() {
        return data;
    }

    public void setData(List<CartProductRequest> data) {
        this.data = data;
    }
}
