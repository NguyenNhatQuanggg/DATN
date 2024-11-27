package com.project2.banhangmypham.repository.detail_product;

import com.project2.banhangmypham.model.CartDeleteRequest;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.CartProductResponse;
import com.project2.banhangmypham.model.DetailProductResponse;
import com.project2.banhangmypham.model.FavoriteRequest;
import com.project2.banhangmypham.model.FavoriteResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IDetailProductRepository {
    Single<MessageResponse> addCartProduct(CartProductRequest data);
    Single<MessageResponse> updateListCartProduct(CartDeleteRequest data);
    Single<CartProductResponse> getAllCartProductList(String uid);
    Single<MessageResponse> createOrderForProductList(OrderRequest orderRequest);
    Single<OrderResponse> getAllOrderForProductList(String uid);
    Single<MessageResponse> updateFavorite(FavoriteRequest favoriteRequest);
    Single<MessageResponse> getFavoriteById(String productId, String userId);
    Single<FavoriteResponse>  getAllFavoriteProductByIdUser(String userId);
}
