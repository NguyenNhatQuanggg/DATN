package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AddressTransfer implements Parcelable {

    private String _id ;
    @SerializedName("user")
    private String uid ;
    @SerializedName("receiver_name")
    private String nameReceiver ;
    @SerializedName("receiver_phone")

    private String phoneReceiver ;
    @SerializedName("address")

    private String addressReceiver ;

    public AddressTransfer() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public AddressTransfer(String nameReceiver, String phoneReceiver, String addressReceiver) {
        this.nameReceiver = nameReceiver;
        this.phoneReceiver = phoneReceiver;
        this.addressReceiver = addressReceiver;
    }

    protected AddressTransfer(Parcel in) {
        nameReceiver = in.readString();
        phoneReceiver = in.readString();
        addressReceiver = in.readString();
    }

    public static final Creator<AddressTransfer> CREATOR = new Creator<AddressTransfer>() {
        @Override
        public AddressTransfer createFromParcel(Parcel in) {
            return new AddressTransfer(in);
        }

        @Override
        public AddressTransfer[] newArray(int size) {
            return new AddressTransfer[size];
        }
    };

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public String getAddressReceiver() {
        return addressReceiver;
    }

    public void setAddressReceiver(String addressReceiver) {
        this.addressReceiver = addressReceiver;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nameReceiver);
        dest.writeString(phoneReceiver);
        dest.writeString(addressReceiver);
    }
}
