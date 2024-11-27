package com.project2.banhangmypham.repository.home;

import android.util.Pair;

import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.EvaluationResponse;
import com.project2.banhangmypham.model.HomeResponse;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.model.ProductResponse;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;

public class HomeRepositoryImpl implements IHomeRepository{
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    public Single<HomeResponse> getHomeScreenData() {
        Single<CategoryResponse> categoryResponse =  apiClientService.makeRequest("http://10.0.2.2:5000/category/getAllCategories", ApiMethod.GET, CategoryResponse.class, null)
                .subscribeOn(Schedulers.io());
        Single<ProductResponse> productResponse =  apiClientService.makeRequest("http://10.0.2.2:5000/product/getAllProducts", ApiMethod.GET, ProductResponse.class, null)
                .subscribeOn(Schedulers.io());

        return Single.zip(categoryResponse, productResponse, (category , product) ->{
            List<Product> productsFamousList = new ArrayList<>();
            List<Product> productsDiscountList = new ArrayList<>();
            HashMap<String,List<Product>> productMap = new HashMap<>();
            productsFamousList = product.getData().stream().filter(Product::isFamous).collect(Collectors.toList());
            productsDiscountList = product.getData().stream().filter(it -> it.getDiscount() > 0).collect(Collectors.toList());

            List<Category> categoryList = category.getData();
            if (categoryList != null && !categoryList.isEmpty()){
                for (Category data : categoryList){
                    List<Product> dataProductsList = product.getData().stream().filter(it -> it.getCategory().getId().equals(data.getId())).collect(Collectors.toList());
                    productMap.put(data.getId(), dataProductsList);
                }
            }

            return new HomeResponse(productsFamousList,productsDiscountList,productMap,category.getData(),product.getData());
        }).subscribeOn(Schedulers.io());
    }
}
