package com.project2.banhangmypham.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Parcelable {
    private String full_name, profile_image, address, phone, _id, username, date, password;
    private boolean banned;
    private boolean is_admin;

    public User() {
    }
    protected User(Parcel in) {
        full_name = in.readString();
        profile_image = in.readString();
        address = in.readString();
        phone = in.readString();
        _id = in.readString();
        username = in.readString();
        password = in.readString();
        banned = in.readByte() != 0;
        is_admin = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(full_name);
        dest.writeString(profile_image);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(_id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeByte((byte) (banned ? 1 : 0));
        dest.writeByte((byte) (is_admin ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public User(String full_name, String profile_image, String address, String phone, String uid, String username, String date, String password, boolean banned, boolean is_admin) {
        this.full_name = full_name;
        this.profile_image = profile_image;
        this.address = address;
        this.phone = phone;
        this._id = uid;
        this.username = username;
        this.date = date;
        this.password = password;
        this.banned = banned;
        this.is_admin = is_admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }


    boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
    public boolean isValid() {
        if (isEmailValid(this.username) && this.password.length() > 6 ){
            return true ;
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "full_name='" + full_name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", _id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
