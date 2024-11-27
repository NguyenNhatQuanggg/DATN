package com.project2.banhangmypham.admin.repository;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.DiscountModel;
import com.project2.banhangmypham.model.DiscountResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DiscountRepositoryImpl implements IDiscountRepository{

    public static final String TAG = "DiscountRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> addDiscountModel(DiscountModel discountModel) {
        String jsonRequest = new Gson().toJson(discountModel);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/discount/add-new-discount", ApiMethod.POST, MessageResponse.class,
                            RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> updateDiscountModel(DiscountModel discountModel) {
        String jsonRequest = new Gson().toJson(discountModel);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/discount/update-discount", ApiMethod.PUT, MessageResponse.class,
                            RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> deleteDiscountModel(DiscountModel discountModel) {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/discount/delete-discount/"+ discountModel.get_id(), ApiMethod.POST, MessageResponse.class,
                            null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<DiscountResponse> getAllDiscountModelList() {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/discount/get-all-discounts", ApiMethod.GET, DiscountResponse.class, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
