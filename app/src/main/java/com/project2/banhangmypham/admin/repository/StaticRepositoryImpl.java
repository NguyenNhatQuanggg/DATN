package com.project2.banhangmypham.admin.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.project2.banhangmypham.model.ProductResponse;
import com.project2.banhangmypham.model.ProductSellerRespsonse;
import com.project2.banhangmypham.model.StaticResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StaticRepositoryImpl implements IStaticRepository{
    public static final String TAG = "OrderAdminRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<StaticResponse> getStaticProductByTime(Long from, Long to, boolean isAll) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("startDate", from);
            jsonObject.put("endDate", to);
            if (isAll) jsonObject.put("all", 1);
        }catch (Exception e){

        }

        Log.d(TAG, "getStaticProductByTime: ====> request = ");
        return Single.create(emitter -> apiClientService.makeRequest("http://10.0.2.2:5000/user/get-dashboard-data", ApiMethod.POST, StaticResponse.class,
                        RequestBody.create(jsonObject.toString(), JSON))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(emitter::onSuccess, emitter::onError));
    }
}
