package com.project2.banhangmypham.repository.discount;

import com.project2.banhangmypham.model.DiscountResponse;

import io.reactivex.rxjava3.core.Single;

public interface IDiscountRepository {

    Single<DiscountResponse> getAllDiscountList(String uid );
}
