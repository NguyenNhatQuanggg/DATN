package com.project2.banhangmypham.admin.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.admin.repository.ICategoryRepository;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.MessageResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryViewModel extends ViewModel {
    public static final String TAG = "CategoryViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ICategoryRepository iCategoryRepository ;
    private MutableLiveData<Category> _addCategoryLiveData = new MutableLiveData<>();
     LiveData<Category> addCategoryLiveData = _addCategoryLiveData;
    private MutableLiveData<Category> _editCategoryLiveData = new MutableLiveData<>();
     LiveData<Category> editCategoryLiveData = _editCategoryLiveData;
    private MutableLiveData<MessageResponse> _deleteCategoryLiveData = new MutableLiveData<>();
     LiveData<MessageResponse> deleteCategoryLiveData = _deleteCategoryLiveData;
    private MutableLiveData<CategoryResponse> _categoryListLiveData = new MutableLiveData<>();
     LiveData<CategoryResponse> categoryListLiveData = _categoryListLiveData;
    public void setICategoryRepository(ICategoryRepository data){
        iCategoryRepository = data;
    }

    public LiveData<CategoryResponse> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public LiveData<Category> getAddCategoryLiveData() {
        return addCategoryLiveData;
    }

    public LiveData<Category> getEditCategoryLiveData() {
        return editCategoryLiveData;
    }

    public LiveData<MessageResponse> getDeleteCategoryLiveData() {
        return deleteCategoryLiveData;
    }

    public void getAllCategoryList(){
        Disposable disposable = iCategoryRepository.getAllCategoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _categoryListLiveData.postValue(result);
                        },
                        error -> {
                            _categoryListLiveData.postValue(new CategoryResponse(new ArrayList<>(), false, error.getMessage()));

                        });

        compositeDisposable.add(disposable);
    }
    public void addCategory(Category category) {
        Disposable disposable = iCategoryRepository.addCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> _addCategoryLiveData.postValue(result),
                        error -> {
                            Log.d(TAG, "addCategory:  error = " +error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }
    public void updateCategory(Category category) {
        Disposable disposable = iCategoryRepository.updateCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> _editCategoryLiveData.postValue(result),
                        error -> Log.d(TAG, "updateCategory: error = "+ error.getMessage()));

        compositeDisposable.add(disposable);
    }

    public void deleteCategory(String id) {
        Disposable disposable = iCategoryRepository.deleteCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> _deleteCategoryLiveData.postValue(result),
                        error -> {
                            Log.d(TAG, "deleteCategory: error = "+ error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
