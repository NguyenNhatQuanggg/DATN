package com.project2.banhangmypham.repository.discount;

import android.annotation.SuppressLint;

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
    private final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @SuppressLint("CheckResult")
    @Override
    public Single<DiscountResponse> getAllDiscountList(String uid) {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/discount/get-all-discounts", ApiMethod.GET, DiscountResponse.class,null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
