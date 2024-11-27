package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.OrderAdminResponse;

import io.reactivex.rxjava3.core.Single;

public interface IOrderAdminRepository {

    Single<OrderAdminResponse> getAllOrderList();
    Single<MessageResponse> updateStatusOrder(String id, int status);
}
