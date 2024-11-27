package com.project2.banhangmypham.admin.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.CommentResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CommentRepositoryImpl implements ICommentRepository{
    public static final String TAG = "CategoryRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> updateStateComment(String _id, int status) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("_id", _id);
            jsonObject.put("status", status);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Log.d(TAG, "updateStateComment: ===> jsonObject = " + jsonObject.toString());
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/rating/update-rating-status", ApiMethod.PUT, MessageResponse.class,
                            RequestBody.create(jsonObject.toString(), JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single <CommentResponse>getALlCommentsList() {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/rating/get-all-ratings-by-admin", ApiMethod.GET, CommentResponse.class,
                            null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
