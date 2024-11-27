package com.project2.banhangmypham.admin.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CategoryRepositoryImpl implements ICategoryRepository{
    public static final String TAG = "CategoryRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @SuppressLint("CheckResult")
    @Override
    public Single<Category> addCategory(Category category) {
        String jsonRequest = new Gson().toJson(category);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/category/addNewCategory", ApiMethod.POST, Category.class,
                        RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<Category> updateCategory(Category category) {
        String jsonRequest = new Gson().toJson(category);
        Log.d(TAG, "updateCategory: =====> request = "+ category);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/category/updateCategory", ApiMethod.PUT, Category.class,
                            RequestBody.create(jsonRequest, JSON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> deleteCategory(String id) {
        Log.d(TAG, "deleteCategory: ====> category = " + id);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/category/deleteCategory/"+id, ApiMethod.DELETE, MessageResponse.class,
                          null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<CategoryResponse> getAllCategoryList() {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/category/getAllCategories", ApiMethod.GET, CategoryResponse.class, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
}
