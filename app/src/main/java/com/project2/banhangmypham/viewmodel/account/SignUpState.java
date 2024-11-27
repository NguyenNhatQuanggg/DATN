package com.project2.banhangmypham.viewmodel.account;

public class SignUpState {
    private boolean isSuccess ;
    private String error ;

    public SignUpState(boolean isSuccess, String error) {
        this.isSuccess = isSuccess;
        this.error = error;
    }
    public boolean isSuccess() {
        return isSuccess;
    }
    public String getError() {
        return error;
    }
}
