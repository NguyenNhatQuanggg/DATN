package com.project2.banhangmypham.admin.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.admin.repository.ICategoryRepository;
import com.project2.banhangmypham.admin.repository.IDiscountRepository;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.DiscountModel;
import com.project2.banhangmypham.model.DiscountResponse;
import com.project2.banhangmypham.model.MessageResponse;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DiscountViewModel extends ViewModel {
    public static final String TAG = "CategoryViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IDiscountRepository iDiscountRepository ;
    private MutableLiveData<MessageResponse> _addDiscountLiveData = new MutableLiveData<>();
    LiveData<MessageResponse> addDiscountLiveData = _addDiscountLiveData;
    private MutableLiveData<MessageResponse> _editDiscountLiveData = new MutableLiveData<>();
    LiveData<MessageResponse> editDiscountLiveData = _editDiscountLiveData;
    private MutableLiveData<DiscountResponse> _discountListLiveData = new MutableLiveData<>();
    LiveData<DiscountResponse> discountListLiveData = _discountListLiveData;

    public LiveData<MessageResponse> getAddDiscountLiveData() {
        return addDiscountLiveData;
    }

    public LiveData<MessageResponse> getEditDiscountLiveData() {
        return editDiscountLiveData;
    }

    public LiveData<DiscountResponse> getDiscountListLiveData() {
        return discountListLiveData;
    }

    public void setIDiscountRepository(IDiscountRepository iDiscountRepository) {
        this.iDiscountRepository = iDiscountRepository;
    }

    public void addDiscountModel(DiscountModel discountModel){
        Disposable disposable = iDiscountRepository.addDiscountModel(discountModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _addDiscountLiveData.postValue(result);
                        },
                        error -> {
                            Log.d(TAG, "addDiscountModel: ====> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }
    public void updateDiscountModel(DiscountModel discountModel){
        Disposable disposable = iDiscountRepository.updateDiscountModel(discountModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _editDiscountLiveData.postValue(result);
                        },
                        error -> {
                            Log.d(TAG, "addDiscountModel: ====> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    public void getAllDiscountList(){
        Disposable disposable = iDiscountRepository.getAllDiscountModelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _discountListLiveData.postValue(result);
                        },
                        error -> {
                            Log.d(TAG, "addDiscountModel: ====> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
