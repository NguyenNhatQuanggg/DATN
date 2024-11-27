package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.model.ProductSellerRespsonse;

import io.reactivex.rxjava3.core.Single;

public interface IProductHotSeller {
    Single<ProductSellerRespsonse> getProductHotSellerList(Long from, Long to);
}
