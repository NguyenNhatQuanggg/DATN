package com.project2.banhangmypham.admin.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.admin.repository.ICategoryRepository;
import com.project2.banhangmypham.admin.repository.IProductRepository;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.model.ProductResponse;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductViewModel extends ViewModel {
    public static final String TAG = "CategoryViewModel";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ICategoryRepository iCategoryRepository ;
    private MutableLiveData<MessageResponse> _addProductLiveData = new MutableLiveData<>();
    LiveData<MessageResponse> addProductLiveData = _addProductLiveData;
    private MutableLiveData<MessageResponse> _editProductLiveData = new MutableLiveData<>();
    LiveData<MessageResponse> editCategoryLiveData = _editProductLiveData;
    private MutableLiveData<MessageResponse> _deleteCategoryLiveData = new MutableLiveData<>();
    LiveData<MessageResponse> deleteCategoryLiveData = _deleteCategoryLiveData;
    private MutableLiveData<ProductResponse> _productListLiveData = new MutableLiveData<>();
    LiveData<ProductResponse> productListLiveData = _productListLiveData;
    private IProductRepository productRepository;
    public LiveData<MessageResponse> getAddProductLiveData() {
        return addProductLiveData;
    }
    public LiveData<MessageResponse> getEditCategoryLiveData() {
        return editCategoryLiveData;
    }
    public LiveData<MessageResponse> getDeleteCategoryLiveData() {
        return deleteCategoryLiveData;
    }
    public LiveData<ProductResponse> getProductListLiveData() {
        return productListLiveData;
    }

    public void setICategoryRepository(IProductRepository data){
        productRepository = data;
    }

    public void getAllProductsList(){
        Disposable disposable = productRepository.getAllProductsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> {
                            _productListLiveData.postValue(result);
                        },
                        error -> {
//                            _categoryListLiveData.postValue(new CategoryResponse(new ArrayList<>(), false, error.getMessage()));
                            Log.d(TAG, "getAllProductsList: ====> error = " + error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }
    public void addProduct(Product product) {
        Disposable disposable = productRepository.addProduct(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> _addProductLiveData.postValue(result),
                        error -> {
                            Log.d(TAG, "addCategory:  error = " +error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }
    public void updateProduct(Product product) {
        Disposable disposable = productRepository.updateProduct(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result -> _editProductLiveData.postValue(result),
                        error -> Log.d(TAG, "updateCategory: error = "+ error.getMessage()));

        compositeDisposable.add(disposable);
    }

    public void deleteProduct(String id) {
        Disposable disposable = productRepository.deleteProduct(id)
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
