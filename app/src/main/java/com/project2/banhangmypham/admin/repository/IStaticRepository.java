package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.model.ProductResponse;
import com.project2.banhangmypham.model.ProductSellerRespsonse;
import com.project2.banhangmypham.model.StaticResponse;

import io.reactivex.rxjava3.core.Single;

public interface IStaticRepository {
    Single<StaticResponse> getStaticProductByTime(Long from, Long to, boolean isAll);
}
