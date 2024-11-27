package com.project2.banhangmypham.admin.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.admin.repository.IStaticRepository;
import com.project2.banhangmypham.model.ProductResponse;
import com.project2.banhangmypham.model.StaticResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StaticOrderViewModel extends ViewModel {
    public static final String TAG = "CategoryViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IStaticRepository iStaticRepository ;
    private MutableLiveData<StaticResponse> _staticProductMutableLiveData = new MutableLiveData<>();
    private LiveData<StaticResponse> staticProductMutableLiveData = _staticProductMutableLiveData;

    public LiveData<StaticResponse> getStaticProductMutableLiveData() {
        return staticProductMutableLiveData;
    }
    public void setiStaticRepository(IStaticRepository data){
        iStaticRepository = data;
    }
    public void getStaticAllProductByTime(Long from, Long to, boolean isAll) {
        Disposable disposable = iStaticRepository.getStaticProductByTime(from, to, isAll)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _staticProductMutableLiveData.postValue(result);
                        },
                        error -> {
                        });

        compositeDisposable.add(disposable);
    }

    public void clear(){
        compositeDisposable.clear();
    }

    @Override
    protected void onCleared() {
        clear();
        super.onCleared();
    }
}
