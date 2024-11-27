package com.project2.banhangmypham.repository.address_transfer;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.project2.banhangmypham.model.AddressResponse;
import com.project2.banhangmypham.model.AddressTransfer;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddressTransferRepository implements IAddressTransferRepository{

    private ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> addAddressTransfer(AddressTransfer addressTransfer) {
        String jsonRequest = new Gson().toJson(addressTransfer);

        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/delivery-address/add-delivery-address", ApiMethod.POST, MessageResponse.class, RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<AddressResponse> getAllAddressTransferList(String uid) {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/delivery-address/get-delivery-addresses-by-user/"+uid, ApiMethod.GET, AddressResponse.class, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
