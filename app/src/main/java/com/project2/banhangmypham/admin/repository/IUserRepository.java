package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.model.UserResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IUserRepository {
    Single<UserResponse> getAllUsersList();
    List<User> filterByName(String data);
    List<User> filterByTypeAccount(boolean data);
    List<User> filterByStateAccount(boolean data);
}
