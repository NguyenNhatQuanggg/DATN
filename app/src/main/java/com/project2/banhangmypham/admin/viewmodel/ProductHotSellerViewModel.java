package com.project2.banhangmypham.admin.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.admin.repository.IOrderAdminRepository;
import com.project2.banhangmypham.admin.repository.IProductHotSeller;
import com.project2.banhangmypham.model.ProductSellerRespsonse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductHotSellerViewModel extends ViewModel {
    public static final String TAG = "CategoryViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IProductHotSeller iProductHotSeller ;
    private MutableLiveData<ProductSellerRespsonse> _productSellerRespsonseMutableLiveData = new MutableLiveData<>();
    private LiveData<ProductSellerRespsonse> productSellerRespsonseLiveData = _productSellerRespsonseMutableLiveData;

    public LiveData<ProductSellerRespsonse> getProductSellerRespsonseLiveData() {
        return productSellerRespsonseLiveData;
    }
    public void setIProductHotSeller(IProductHotSeller data){
        iProductHotSeller = data;
    }
    public void getAllProductHotSeller(Long from, Long to) {
        Disposable disposable = iProductHotSeller.getProductHotSellerList(from, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _productSellerRespsonseMutableLiveData.postValue(result);
                        },
                        error -> {
                            Log.d(TAG, "getAllProductHotSeller: ====> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    public void onClear(){
        compositeDisposable.clear();
    }

    @Override
    protected void onCleared() {
        onClear();
        super.onCleared();
    }
}
