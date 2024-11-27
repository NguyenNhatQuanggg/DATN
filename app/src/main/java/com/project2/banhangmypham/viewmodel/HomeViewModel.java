package com.project2.banhangmypham.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.model.HomeResponse;
import com.project2.banhangmypham.repository.home.IHomeRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    public static final String TAG = "HomeViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<HomeResponse> _homeResponseLiveData = new MutableLiveData<>();
    private LiveData<HomeResponse> homeResponseLiveData = _homeResponseLiveData;

    private IHomeRepository iHomeRepository;

    public void setiHomeRepository(IHomeRepository data) {
        iHomeRepository = data;
    }
    public LiveData<HomeResponse> getHomeResponseLiveData() {
        return homeResponseLiveData;
    }

    public void getDataHome(){
        Disposable disposable = iHomeRepository.getHomeScreenData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _homeResponseLiveData.postValue(result), error ->{
                    Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage());
        });
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
