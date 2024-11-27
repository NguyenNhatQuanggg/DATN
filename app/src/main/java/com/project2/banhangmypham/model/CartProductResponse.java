package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartProductResponse implements Serializable {

    @SerializedName("data")
    private ItemCartProductResponse data ;
    private boolean isSuccess ;
    private String error ;

    public ItemCartProductResponse getData() {
        return data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }

    public static class ItemCartProductResponse {
        private String _id ;
        @SerializedName("user")
        private String user ;
        @SerializedName("items")
        private final List<CartProductRequest> cartProductRequestList = new ArrayList<>();

        @SerializedName("total_price")
        private long totalCart;
        public String get_id() {
            return _id;
        }

        public long getTotalCart() {
            return totalCart;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public List<CartProductRequest> getCartProductRequestList() {
            return cartProductRequestList;
        }
    }
}
