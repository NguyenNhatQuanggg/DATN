package com.project2.banhangmypham.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.FavoriteProductAdapter;
import com.project2.banhangmypham.databinding.ActivityFavoriteProductUserBinding;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.repository.detail_product.DetailProductRepositoryImpl;
import com.project2.banhangmypham.viewmodel.product.DetailProductViewModel;

import java.util.List;

public class FavoriteProductUserActivity extends AppCompatActivity implements FavoriteProductAdapter.IFavoriteProduct {

    ActivityFavoriteProductUserBinding binding ;
    FavoriteProductAdapter favoriteProductAdapter ;
    String userId = "";
    DetailProductViewModel detailProductViewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFavoriteProductUserBinding.inflate(getLayoutInflater());
        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);
        detailProductViewModel.setIDetailProductRepository(new DetailProductRepositoryImpl());
        favoriteProductAdapter = new FavoriteProductAdapter(this, this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userId = getIntent().getStringExtra("userId");
        binding.ivBack.setOnClickListener(view -> finish());
        detailProductViewModel.getAllProductFavoriteLiveData().observe(this, result ->{
            if (result != null && result.getData() != null) {
//                favoriteProductAdapter.submitList(result.getData());
                showInfoProductIfValid(result.getData());
            }
        });

        binding.rcvFavoriteProduct.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvFavoriteProduct.hasFixedSize();
        binding.rcvFavoriteProduct.setAdapter(favoriteProductAdapter);
    }

    @Override
    public void onClickItemFavoriteProduct(Product product) {
         Intent intentProduct = new Intent(this, DetailProductActivity.class);
         Bundle bundle = new Bundle();
         bundle.putParcelable("product", product);
         bundle.putString("userId", userId);
         intentProduct.putExtras(bundle);
         startActivity(intentProduct);
    }
    private void showInfoProductIfValid(List<Product> dataProductList) {
        if (dataProductList.isEmpty()) {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.rcvFavoriteProduct.setVisibility(View.GONE);
        }else {
            binding.tvError.setVisibility(View.GONE);
            binding.rcvFavoriteProduct.setVisibility(View.VISIBLE);
            favoriteProductAdapter.submitList(dataProductList);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        detailProductViewModel.getAllFavoriteProductsList(userId);
    }
}