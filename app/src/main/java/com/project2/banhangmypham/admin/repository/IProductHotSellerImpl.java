package com.project2.banhangmypham.admin.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.project2.banhangmypham.model.OrderAdminResponse;
import com.project2.banhangmypham.model.ProductSellerRespsonse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class IProductHotSellerImpl implements IProductHotSeller{
    public static final String TAG = "OrderAdminRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<ProductSellerRespsonse> getProductHotSellerList(Long from, Long to) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("startDate", from);
            jsonObject.put("endDate", to);
        }catch (Exception e){

        }
        Log.d(TAG, "getProductHotSellerList: ====> request = " + jsonObject.toString());

        // SA -> PROduct , sô luongj bán product , total -> sô lượng product
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/get-top-selling-products", ApiMethod.POST, ProductSellerRespsonse.class,
                            RequestBody.create(jsonObject.toString(), JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
