package com.project2.banhangmypham.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CartProductRequest implements Parcelable {
    @SerializedName("product")
    private Product product ;
    private String userId ;
    @SerializedName("quantity")
    private int numberChoice;

    public CartProductRequest(Product product, Category category, String userId, int numberChoice) {
        this.product = product;
        this.userId = userId;
        this.numberChoice = numberChoice;
    }

    protected CartProductRequest(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        userId = in.readString();
        numberChoice = in.readInt();
    }

    public static final Creator<CartProductRequest> CREATOR = new Creator<CartProductRequest>() {
        @Override
        public CartProductRequest createFromParcel(Parcel in) {
            return new CartProductRequest(in);
        }

        @Override
        public CartProductRequest[] newArray(int size) {
            return new CartProductRequest[size];
        }
    };

    public void setNumberChoice(int numberChoice) {
        this.numberChoice = numberChoice;
    }

    public Product getProduct() {
        return product;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNumberChoice() {
        return numberChoice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        dest.writeString(userId);
        dest.writeInt(numberChoice);
    }
}
