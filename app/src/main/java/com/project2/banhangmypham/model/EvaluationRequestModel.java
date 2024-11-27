package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class EvaluationRequestModel implements Parcelable {
    private Product product ;
    private float rating ;
    private String message ;
    private String userId ;
    private User user;
    @SerializedName("created_date")
    private String createdDate;
    private int status ;
    public EvaluationRequestModel(Product product, float rating, String message, String userId) {
        this.product = product;
        this.rating = rating;
        this.message = message;
        this.userId = userId;
    }

    protected EvaluationRequestModel(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        rating = in.readFloat();
        message = in.readString();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        dest.writeFloat(rating);
        dest.writeString(message);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EvaluationRequestModel> CREATOR = new Creator<EvaluationRequestModel>() {
        @Override
        public EvaluationRequestModel createFromParcel(Parcel in) {
            return new EvaluationRequestModel(in);
        }

        @Override
        public EvaluationRequestModel[] newArray(int size) {
            return new EvaluationRequestModel[size];
        }
    };

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getRating() {
        return rating;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }
}
