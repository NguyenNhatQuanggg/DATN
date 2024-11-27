package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StaticModel {
    private int userCount ;
    @SerializedName("income")
    private long inCome ;
    private long paidOrderCount;
    private List<OrderRequestAdmin> orderList = new ArrayList<>();

    public int getUserCount() {
        return userCount;
    }

    public long getInCome() {
        return inCome;
    }

    public long getPaidOrderCount() {
        return paidOrderCount;
    }

    public List<OrderRequestAdmin> getOrdersList() {
        return orderList;
    }
}
