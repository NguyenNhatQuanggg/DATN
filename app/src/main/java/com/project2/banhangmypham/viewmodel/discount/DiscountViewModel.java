package com.project2.banhangmypham.viewmodel.discount;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.model.DiscountResponse;
import com.project2.banhangmypham.repository.discount.IDiscountRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DiscountViewModel extends ViewModel {
    public static final String TAG = "DiscountViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<DiscountResponse> _discountResponseLiveData= new MutableLiveData<>();
    LiveData<DiscountResponse> discountResponseLiveData = _discountResponseLiveData ;

    public LiveData<DiscountResponse> getDiscountResponseLiveData() {
        return discountResponseLiveData;
    }

    IDiscountRepository iDiscountRepository ;
    public void setIDiscountRepository(IDiscountRepository data){
        iDiscountRepository = data ;
    }
    public void getAllDiscountsList(String uid) {
        Disposable disposable = iDiscountRepository.getAllDiscountList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _discountResponseLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }
    public void clear(){
        compositeDisposable.clear();
    }
}
