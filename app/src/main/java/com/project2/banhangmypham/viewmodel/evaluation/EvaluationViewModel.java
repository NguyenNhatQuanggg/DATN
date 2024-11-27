package com.project2.banhangmypham.viewmodel.evaluation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.model.AddressResponse;
import com.project2.banhangmypham.model.EvaluationRequestModel;
import com.project2.banhangmypham.model.EvaluationResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.repository.evaluation.IEvaluationRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EvaluationViewModel extends ViewModel {
    public static final String TAG = "HomeViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<MessageResponse> _evaluationProductLiveData = new MutableLiveData<>();
    private LiveData<MessageResponse> evaluationProductLiveData = _evaluationProductLiveData;

    private MutableLiveData<EvaluationResponse> _allEvaluationLiveData = new MutableLiveData<>();
    private LiveData<EvaluationResponse> allEvaluationLiveData = _allEvaluationLiveData;

    private IEvaluationRepository iEvaluationRepository;

    public void setiEvaluationRepository(IEvaluationRepository data) {
        iEvaluationRepository = data;
    }
    public LiveData<MessageResponse> getEvaluationProductLiveData() {
        return evaluationProductLiveData;
    }

    public LiveData<EvaluationResponse> getAllEvaluationLiveData() {
        return allEvaluationLiveData;
    }

    public void addEvaluationRequestModel(EvaluationRequestModel data){
        Disposable disposable = iEvaluationRepository.addEvaluationRepository(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _evaluationProductLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
        compositeDisposable.add(disposable);
    }
    public void getAllEvaluationRequestModelById(String idProduct){
        Disposable disposable = iEvaluationRepository.getEvaluationProductById(idProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> _allEvaluationLiveData.postValue(result), error -> Log.d(TAG, "getDataHome: ====> error = "+ error.getMessage()));
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
