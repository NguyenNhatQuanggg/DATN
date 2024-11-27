package com.project2.banhangmypham.viewmodel.address;

import android.os.Message;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.model.AddressResponse;
import com.project2.banhangmypham.model.AddressTransfer;
import com.project2.banhangmypham.model.HomeResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.repository.address_transfer.IAddressTransferRepository;
import com.project2.banhangmypham.repository.home.IHomeRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddressViewModel extends ViewModel {
    public static final String TAG = "HomeViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<MessageResponse> _addAddressTransferLiveData = new MutableLiveData<>();
    private LiveData<MessageResponse> addAddressTransferLiveData = _addAddressTransferLiveData;

    private MutableLiveData<AddressResponse> _allAddressTransferLiveData = new MutableLiveData<>();
    private LiveData<AddressResponse> allAddressTransferLiveData = _allAddressTransferLiveData;

    private IAddressTransferRepository iAddressTransferRepository;

    public void setAddressTransferRepository(IAddressTransferRepository data) {
        iAddressTransferRepository = data;
    }
    public LiveData<MessageResponse> getAddAddressTransferLiveData() {
        return addAddressTransferLiveData;
    }

    public LiveData<AddressResponse> getAllAddressTransferLiveData() {
        return allAddressTransferLiveData;
    }
    public void addAddressTransfer(AddressTransfer data){
        Disposable disposable = iAddressTransferRepository.addAddressTransfer(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _addAddressTransferLiveData.postValue(result), error ->{
                    Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage());
                });
        compositeDisposable.add(disposable);
    }
    public void getAllAddressTransferList(String uid){
        Disposable disposable = iAddressTransferRepository.getAllAddressTransferList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _allAddressTransferLiveData.postValue(result), error ->{
                    Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage());
                });
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
