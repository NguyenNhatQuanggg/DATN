package com.project2.banhangmypham.model;

import com.google.gson.annotations.SerializedName;

public class ProductHotSeller {
    private Product product;
    @SerializedName("quantity")
    private int numberProductSeller;
    @SerializedName("amount")
    private int totalMoneyProductSeller;

    public ProductHotSeller() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumberProductSeller() {
        return numberProductSeller;
    }

    public void setNumberProductSeller(int numberProductSeller) {
        this.numberProductSeller = numberProductSeller;
    }

    public int getTotalMoneyProductSeller() {
        return totalMoneyProductSeller;
    }

    public void setTotalMoneyProductSeller(int totalMoneyProductSeller) {
        this.totalMoneyProductSeller = totalMoneyProductSeller;
    }
}
