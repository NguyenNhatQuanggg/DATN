package com.project2.banhangmypham.viewmodel.product;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.model.CartDeleteRequest;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.CartProductResponse;
import com.project2.banhangmypham.model.DetailProductResponse;
import com.project2.banhangmypham.model.FavoriteRequest;
import com.project2.banhangmypham.model.FavoriteResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderRequestAdmin;
import com.project2.banhangmypham.model.OrderResponse;
import com.project2.banhangmypham.repository.detail_product.IDetailProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailProductViewModel extends ViewModel {

    public static final String TAG = "DetailProductViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<MessageResponse> _detailProductLiveData = new MutableLiveData<>();
    private LiveData<MessageResponse> detailProductLiveData = _detailProductLiveData;
    private MutableLiveData<MessageResponse> _updateProductLiveData = new MutableLiveData<>();
    private LiveData<MessageResponse> updateProductLiveData = _updateProductLiveData;
    private MutableLiveData<CartProductResponse> _listProductLiveData = new MutableLiveData<>();
    private final LiveData<CartProductResponse> listProductLiveData = _listProductLiveData;
    private IDetailProductRepository iDetailProductRepository ;
    private MutableLiveData<MessageResponse> _createOrderLiveData = new MutableLiveData<>();
    private LiveData<MessageResponse> createOrderLiveData = _createOrderLiveData;

    private MutableLiveData<List<OrderRequest>> _orderCompletingResponseLiveData = new MutableLiveData<>();
    private LiveData<List<OrderRequest>> orderCompletingResponseLiveData = _orderCompletingResponseLiveData;

    private MutableLiveData<List<OrderRequest>> _orderExecutingResponseLiveData = new MutableLiveData<>();
    private LiveData<List<OrderRequest>> orderExecutingResponseLiveData = _orderExecutingResponseLiveData;

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public LiveData<List<OrderRequest>> getOrderExecutingResponseLiveData() {
        return orderExecutingResponseLiveData;
    }

    public LiveData<List<OrderRequest>> getOrderCompletingResponseLiveData() {
        return orderCompletingResponseLiveData;
    }

    private MutableLiveData<MessageResponse> _updateFavoriteLiveData = new MutableLiveData<>();
    private LiveData<MessageResponse> updateFavoriteLiveData = _updateFavoriteLiveData;

    private MutableLiveData<MessageResponse> _oneProductFavoriteLiveData = new MutableLiveData<>();
    private LiveData<MessageResponse> oneProductFavoriteLiveData = _oneProductFavoriteLiveData;

    private MutableLiveData<FavoriteResponse> _allProductFavoriteLiveData = new MutableLiveData<>();
    private LiveData<FavoriteResponse> allProductFavoriteLiveData = _allProductFavoriteLiveData;

    public LiveData<MessageResponse> getOneProductFavoriteLiveData() {
        return oneProductFavoriteLiveData;
    }

    public LiveData<FavoriteResponse> getAllProductFavoriteLiveData() {
        return allProductFavoriteLiveData;
    }

    public LiveData<MessageResponse> getUpdateFavoriteLiveData() {
        return updateFavoriteLiveData;
    }

    public void setIDetailProductRepository(IDetailProductRepository data) {
        iDetailProductRepository = data;
    }

    public LiveData<MessageResponse> getCreateOrderLiveData() {
        return createOrderLiveData;
    }

    public LiveData<MessageResponse> getDetailProductLiveData() {
        return detailProductLiveData;
    }

    public LiveData<CartProductResponse> getListProductLiveData() {
        return listProductLiveData;
    }

    public LiveData<MessageResponse> getUpdateProductLiveData() {
        return updateProductLiveData;
    }

    public void addCartProduct(CartProductRequest cartProductRequest){
        Disposable disposable = iDetailProductRepository.addCartProduct(cartProductRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _detailProductLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void getAllCartProductList(String uid ){
        Disposable disposable = iDetailProductRepository.getAllCartProductList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _listProductLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void updateAllCartProductList(CartDeleteRequest data){
        Disposable disposable = iDetailProductRepository.updateListCartProduct(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _updateProductLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }
    public void createOrderProductList(OrderRequest data){
        Disposable disposable = iDetailProductRepository.createOrderForProductList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _createOrderLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }
    public void getAllOrderProductList(String uid) {
        Disposable disposable = iDetailProductRepository.getAllOrderForProductList(uid)
                .flatMap(it -> Single.create(emitter -> {
                    if (it != null && it.getData() != null) {
                        // Nhóm dữ liệu theo trạng thái của OrderRequest
                        Map<Integer, List<OrderRequest>> dataMap = it.getData()
                                .stream()
                                .collect(Collectors.groupingBy(OrderRequest::getStatus));

                        // Đưa dữ liệu vào LiveData
//                        List<OrderRequest> executingOrders = dataMap.getOrDefault(0, new ArrayList<>());
//                        List<OrderRequest> completingOrders = dataMap.getOrDefault(1, new ArrayList<>());
                        List<OrderRequest> executingOrders = dataMap.getOrDefault(0, new ArrayList<>());
                        if (dataMap.get(1) != null) {
                            executingOrders.addAll(Objects.requireNonNull(dataMap.get(1)));
                        }
                        List<OrderRequest> completingOrders = dataMap.get(2);

                        Log.d(TAG, "getAllOrderProductList: ===> dataMap = "+ dataMap);
                        if (executingOrders != null) {
                            _orderExecutingResponseLiveData.postValue(executingOrders);
                        }
                        if (completingOrders != null) {
                            _orderCompletingResponseLiveData.postValue(completingOrders);
                        }

                        // Trả về kết quả thành công
                        emitter.onSuccess(new MessageResponse(200, ""));
                    } else {
                        // Trả về lỗi khi dữ liệu null
                        emitter.onError(new Throwable("Data is null"));
                    }
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            // Xử lý kết quả thành công
                            // Có thể log hoặc xử lý khác nếu cần
                        },
                        error -> {
                            // Xử lý lỗi
                            Log.e("Error", "Error occurred: " + error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }
    public void updateFavoriteProduct(FavoriteRequest favoriteRequest) {
        Disposable disposable = iDetailProductRepository.updateFavorite(favoriteRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _updateFavoriteLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void getFavoriteById(String productId, String userId) {
        Disposable disposable = iDetailProductRepository.getFavoriteById(productId,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _oneProductFavoriteLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }
    public void getAllFavoriteProductsList(String userId) {
        Disposable disposable = iDetailProductRepository.getAllFavoriteProductByIdUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _allProductFavoriteLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }
    public void clear(){
        compositeDisposable.clear();
    }
    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
