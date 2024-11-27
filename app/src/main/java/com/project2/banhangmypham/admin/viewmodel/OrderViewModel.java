package com.project2.banhangmypham.admin.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.admin.repository.IDiscountRepository;
import com.project2.banhangmypham.admin.repository.IOrderAdminRepository;
import com.project2.banhangmypham.model.DiscountResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.OrderAdminResponse;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderRequestAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderViewModel extends ViewModel {
    public static final String TAG = "CategoryViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IOrderAdminRepository iOrderAdminRepository ;
    private MutableLiveData<MessageResponse> _updateOrderLiveData = new MutableLiveData<>();
    LiveData<MessageResponse> updateOrderLiveData = _updateOrderLiveData;

    private MutableLiveData<OrderAdminResponse> _orderAdminLiveData = new MutableLiveData<>();
    LiveData<OrderAdminResponse> orderAdminLiveData = _orderAdminLiveData;

    private MutableLiveData<List<OrderRequestAdmin>> _orderCompletingResponseLiveData = new MutableLiveData<>();
    private LiveData<List<OrderRequestAdmin>> orderCompletingResponseLiveData = _orderCompletingResponseLiveData;

    private MutableLiveData<List<OrderRequestAdmin>> _orderExecutingResponseLiveData = new MutableLiveData<>();
    private LiveData<List<OrderRequestAdmin>> orderExecutingResponseLiveData = _orderExecutingResponseLiveData;

    public LiveData<List<OrderRequestAdmin>> getOrderCompletingResponseLiveData() {
        return orderCompletingResponseLiveData;
    }

    public LiveData<List<OrderRequestAdmin>> getOrderExecutingResponseLiveData() {
        return orderExecutingResponseLiveData;
    }

    public LiveData<MessageResponse> getUpdateOrderLiveData() {
        return updateOrderLiveData;
    }

    public LiveData<OrderAdminResponse> getOrderAdminLiveData() {
        return orderAdminLiveData;
    }

    public void setIOrderAdminRepository(IOrderAdminRepository iOrderAdminRepository) {
        this.iOrderAdminRepository = iOrderAdminRepository;
    }

    public void getAllOrderList(){
        Disposable disposable = iOrderAdminRepository.getAllOrderList()
                .flatMap(it -> Single.create(emitter -> {
                    if (it != null && it.getData() != null) {
                        // Nhóm dữ liệu theo trạng thái của OrderRequest
                        Map<Integer, List<OrderRequestAdmin>> dataMap = it.getData()
                                .stream()
                                .collect(Collectors.groupingBy(OrderRequestAdmin::getStatus));

                        // Đưa dữ liệu vào LiveData
                        Log.d(TAG, "getAllOrderList: ===> dataMap = " + dataMap);
                        Log.d(TAG, "getAllOrderList: ===> dataMap0 = " + dataMap.get(0));
                        Log.d(TAG, "getAllOrderList: ===> dataMap1 = " + dataMap.get(1));
                        List<OrderRequestAdmin> executingOrders = dataMap.getOrDefault(0, new ArrayList<>());
                        if (dataMap.get(1) != null) {
                            executingOrders.addAll(Objects.requireNonNull(dataMap.get(1)));
                        }
                        List<OrderRequestAdmin> completingOrders = dataMap.get(2);

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
    public void updateStatusOrder(String idOrder , int status ){
        Disposable disposable = iOrderAdminRepository.updateStatusOrder(idOrder, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _updateOrderLiveData.postValue(result);
                        },
                        error -> {
                            Log.d(TAG, "addDiscountModel: ====> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }
}
