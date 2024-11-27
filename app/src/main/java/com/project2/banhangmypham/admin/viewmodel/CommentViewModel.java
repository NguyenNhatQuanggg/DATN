package com.project2.banhangmypham.admin.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.admin.repository.ICategoryRepository;
import com.project2.banhangmypham.admin.repository.ICommentRepository;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.CommentResponse;
import com.project2.banhangmypham.model.MessageResponse;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CommentViewModel extends ViewModel {
    public static final String TAG = "CategoryViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<MessageResponse> _stateCommentLiveData = new MutableLiveData<>();
    LiveData<MessageResponse> stateCommentLiveData = _stateCommentLiveData;
    private MutableLiveData<CommentResponse> _commentListLiveData = new MutableLiveData<>();
    LiveData<CommentResponse> commentListLiveData = _commentListLiveData;
    private ICommentRepository iCommentRepository ;
    public LiveData<MessageResponse> getStateCommentLiveData() {
        return stateCommentLiveData;
    }

    public LiveData<CommentResponse> getCommentListLiveData() {
        return commentListLiveData;
    }

    public void setICommentRepository(ICommentRepository iCommentRepository) {
        this.iCommentRepository = iCommentRepository;
    }

    public void updateStateComment(String id , int status){
        Disposable disposable = iCommentRepository.updateStateComment(id, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _stateCommentLiveData.postValue(result);
                        },
                        error -> {
                            Log.d(TAG, "updateStateComment: ===> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    public void getAllCommentsList() {
        Disposable disposable = iCommentRepository.getALlCommentsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _commentListLiveData.postValue(result);
                        },
                        error -> {
                            Log.d(TAG, "updateStateComment: ===> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }
}
