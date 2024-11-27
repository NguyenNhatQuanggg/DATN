package com.project2.banhangmypham.repository.evaluation;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.project2.banhangmypham.model.EvaluationRequestModel;
import com.project2.banhangmypham.model.EvaluationResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EvaluationRepositoryImpl implements  IEvaluationRepository{
    public static final String TAG = "EvaluationRepositoryImpl";
    private ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> addEvaluationRepository(EvaluationRequestModel evaluationRequestModel) {
        String jsonRequest = new Gson().toJson(evaluationRequestModel);
        Log.d(TAG, "addEvaluationRepository: ===>  jsonRequest = " + jsonRequest);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/rating/add-rating", ApiMethod.POST, MessageResponse.class, RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @Override
    public Completable getAllEvaluationRepository() {
        return null;
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<EvaluationResponse> getEvaluationProductById(String idProduct) {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/rating/get-all-ratings-by-product/"+idProduct, ApiMethod.GET, EvaluationResponse.class,null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
