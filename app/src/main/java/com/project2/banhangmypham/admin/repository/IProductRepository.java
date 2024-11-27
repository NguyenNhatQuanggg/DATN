package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.model.ProductResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface IProductRepository {
    Single<MessageResponse> addProduct(Product product);
    Single<MessageResponse> updateProduct(Product product);
    Single<MessageResponse> deleteProduct(String id);
    Single<ProductResponse> getAllProductsList();
}
