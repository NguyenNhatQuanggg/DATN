package com.project2.banhangmypham.admin.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.model.ProductResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProductRepositoryImpl implements  IProductRepository{
    public static final String TAG = "ProductRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> addProduct(Product product) {
        String jsonRequest = new Gson().toJson(product);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/product/addNewProduct", ApiMethod.POST, MessageResponse.class,
                            RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> updateProduct(Product product) {
        String jsonRequest = new Gson().toJson(product);
        Log.d(TAG, "updateCategory: =====> request = "+ product);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/product/updateProduct", ApiMethod.PUT, MessageResponse.class,
                            RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> deleteProduct(String id) {
        Log.d(TAG, "deleteCategory: ====> category = " + id);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/product/deleteProduct/"+id, ApiMethod.DELETE, MessageResponse.class,
                            null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<ProductResponse> getAllProductsList() {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/product/getAllProducts", ApiMethod.GET, ProductResponse.class, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
