package com.project2.banhangmypham.repository.detail_product;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.project2.banhangmypham.model.CartDeleteRequest;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.CartProductResponse;
import com.project2.banhangmypham.model.DetailProductResponse;
import com.project2.banhangmypham.model.FavoriteRequest;
import com.project2.banhangmypham.model.FavoriteResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DetailProductRepositoryImpl implements IDetailProductRepository{
    public static final String TAG = "DetailProductRepositoryImpl";
    private ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> addCartProduct(CartProductRequest data) {
        String jsonRequest = new Gson().toJson(data);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/cart/add-item-to-cart", ApiMethod.PUT, MessageResponse.class, RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> updateListCartProduct(CartDeleteRequest data) {
        String jsonRequest = new Gson().toJson(data);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/cart/update-cart", ApiMethod.PUT, MessageResponse.class, RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<CartProductResponse> getAllCartProductList(String uid) {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/cart/get-cart/", ApiMethod.GET, CartProductResponse.class, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> createOrderForProductList(OrderRequest data) {
        String jsonRequest = new Gson().toJson(data);
        Log.d(TAG, "createOrderForProductList: ====>");
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/order/add-new-order", ApiMethod.POST,
                            MessageResponse.class, RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<OrderResponse> getAllOrderForProductList(String uid) {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/order/get-orders-by-user/", ApiMethod.GET,
                            OrderResponse.class,null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse>  updateFavorite(FavoriteRequest favoriteRequest) {
        String jsonRequest = new Gson().toJson(favoriteRequest);
        Log.d(TAG, "updateFavorite: "+ jsonRequest);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/toggle-favourite-product", ApiMethod.POST,
                            MessageResponse.class,RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> getFavoriteById(String productId, String userId) {
        FavoriteRequest favoriteRequest = new FavoriteRequest(productId, userId);
        String jsonRequest = new Gson().toJson(favoriteRequest);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/product/is-favourite-product", ApiMethod.POST,
                            MessageResponse.class,RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<FavoriteResponse> getAllFavoriteProductByIdUser(String userId) {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/product/get-favourite-product-by-user/", ApiMethod.GET,
                            FavoriteResponse.class,null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
