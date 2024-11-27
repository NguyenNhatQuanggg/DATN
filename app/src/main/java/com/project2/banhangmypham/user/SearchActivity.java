package com.project2.banhangmypham.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.ProductUserAdapter;
import com.project2.banhangmypham.admin.repository.ProductRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.ProductViewModel;
import com.project2.banhangmypham.databinding.ActivitySearchBinding;
import com.project2.banhangmypham.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements ProductUserAdapter.IProductEvenListener {
    public static final String TAG = "SearchActivity";
    private Disposable searchDisposable;
    private ActivitySearchBinding binding ;
    private ProductViewModel productViewModel;
    private final List<Product> productsList= new ArrayList<>();
    private ProductUserAdapter productUserAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        productUserAdapter = new ProductUserAdapter(this, this);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.setICategoryRepository(new ProductRepositoryImpl());
        productViewModel.getAllProductsList();

        productViewModel.getProductListLiveData().observe(this, result ->{
            if (result != null && result.getData() != null){
                productsList.clear();
                productsList.addAll(result.getData());
                showInfoProductIfValid(productsList);
            }
        });
        binding.searchActivityButtonGoBack.setOnClickListener(view -> finish());

        setupSearchView();
        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerViewSearch.hasFixedSize();
        binding.recyclerViewSearch.setAdapter(productUserAdapter);
    }
    @SuppressLint("CheckResult")
    private void setupSearchView() {
        Observable<Object> searchObservable = Observable.create(emitter ->
                        binding.searchActivitySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        emitter.onNext(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        emitter.onNext(newText);
                        return true;
                    }
                }))
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        searchDisposable = searchObservable.subscribe(query -> {
            performSearch(String.valueOf(query));
        });
    }

    private void performSearch(String query) {
        Log.d(TAG, "performSearch: query = "+ query);
        if (query.isEmpty()){
            showInfoProductIfValid(productsList);
            return;
        }
        List<Product> dataList = productsList.stream().filter(it -> it.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
        showInfoProductIfValid(dataList);
    }
    @Override
    protected void onDestroy() {
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClickItemProduct(Product product) {

    }
    private void showInfoProductIfValid(List<Product> dataProductList) {
        if (dataProductList.isEmpty()) {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.recyclerViewSearch.setVisibility(View.GONE);
        }else {
            binding.tvError.setVisibility(View.GONE);
            binding.recyclerViewSearch.setVisibility(View.VISIBLE);
            productUserAdapter.submitList(dataProductList);
        }
    }
}
