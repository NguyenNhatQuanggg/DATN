package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.model.DiscountModel;
import com.project2.banhangmypham.model.DiscountResponse;
import com.project2.banhangmypham.model.MessageResponse;

import io.reactivex.rxjava3.core.Single;

public interface IDiscountRepository {
    Single<MessageResponse> addDiscountModel(DiscountModel discountModel);
    Single<MessageResponse> updateDiscountModel(DiscountModel discountModel);
    Single<MessageResponse> deleteDiscountModel(DiscountModel discountModel);
    Single<DiscountResponse> getAllDiscountModelList();
}
