package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class EvaluationResponseAdmin implements Parcelable {
    private String _id ;
    private Product product ;
    private float rating ;
    private String message ;
    private User user;
    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public EvaluationResponseAdmin(Product product, float rating, String message, User user) {
        this.product = product;
        this.rating = rating;
        this.message = message;
        this.user = user;
    }

    protected EvaluationResponseAdmin(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        rating = in.readFloat();
        message = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<EvaluationResponseAdmin> CREATOR = new Creator<EvaluationResponseAdmin>() {
        @Override
        public EvaluationResponseAdmin createFromParcel(Parcel in) {
            return new EvaluationResponseAdmin(in);
        }

        @Override
        public EvaluationResponseAdmin[] newArray(int size) {
            return new EvaluationResponseAdmin[size];
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

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        dest.writeFloat(rating);
        dest.writeString(message);
        dest.writeParcelable(user, flags);
    }
}
