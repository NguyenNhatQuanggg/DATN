package com.project2.banhangmypham.repository.account;

import android.net.Uri;

import com.project2.banhangmypham.model.AccountResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface IAccountRepository {
    Single<AccountResponse> logIn(String email, String password);
    Single<MessageResponse> logOut();
    Single<AccountResponse> signUp(User user);
    Single<AccountResponse> changePassword(String newPassword, String oldPassword, String uid);
    Single<AccountResponse> updateInfoAccount(User user);
    Single<AccountResponse> resetEmail(String email);
    Single<AccountResponse> checkTokenValid(String token) ;
}
