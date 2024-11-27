package com.project2.banhangmypham.admin.repository;

import android.annotation.SuppressLint;

import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.OrderAdminResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderAdminRepositoryImpl implements IOrderAdminRepository{
    public static final String TAG = "OrderAdminRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<OrderAdminResponse> getAllOrderList() {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/order/get-all-orders-by-admin", ApiMethod.GET, OrderAdminResponse.class,
                            null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> updateStatusOrder(String id, int status) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", id);
            jsonObject.put("status",status );

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/order/update-order-status", ApiMethod.PUT, MessageResponse.class,
                            RequestBody.create(jsonObject.toString(), JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
