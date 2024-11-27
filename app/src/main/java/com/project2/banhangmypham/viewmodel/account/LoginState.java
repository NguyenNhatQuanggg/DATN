package com.project2.banhangmypham.viewmodel.account;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.project2.banhangmypham.model.User;

public class LoginState implements Parcelable {
    private User user;
    private boolean isSuccess ;
    private String error ;

    protected LoginState(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        isSuccess = in.readByte() != 0;
        error = in.readString();
    }



    public static final Creator<LoginState> CREATOR = new Creator<LoginState>() {
        @Override
        public LoginState createFromParcel(Parcel in) {
            return new LoginState(in);
        }

        @Override
        public LoginState[] newArray(int size) {
            return new LoginState[size];
        }
    };

    public LoginState(User id, boolean isSuccess, String error) {
        this.user = id;
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeByte((byte) (isSuccess ? 1 : 0));
        dest.writeString(error);
    }
}
