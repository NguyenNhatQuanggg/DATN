package com.project2.banhangmypham.admin.product_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.admin.repository.CategoryRepositoryImpl;
import com.project2.banhangmypham.admin.repository.ProductRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.CategoryViewModel;
import com.project2.banhangmypham.admin.viewmodel.ProductViewModel;
import com.project2.banhangmypham.databinding.ActivityAddProduct2Binding;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddProductActivity extends AppCompatActivity {

    ActivityAddProduct2Binding binding ;
    private ProductViewModel productViewModel ;
    private CategoryViewModel categoryViewModel ;
    private List<Category> categoryList = new ArrayList<>();
    private Product productCurrent ;
    private Category category;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddProduct2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        boolean isAdd = intent.getBooleanExtra("isAdd", true);
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                productCurrent = bundle.getParcelable("product", Product.class);
            }else {
                productCurrent = bundle.getParcelable("product");

            }
        }

        if (isAdd){
            binding.btnAddProduct.setText("Thêm");
            binding.tvTitle.setText("Thêm thể loại");
        }else {
            binding.btnAddProduct.setText("Cập nhật");
            binding.tvTitle.setText("Chỉnh sửa thể loại");
        }
        binding.ivBack.setOnClickListener(view -> finish());
        if (productCurrent != null){
            category = productCurrent.getCategory();
            binding.edtName.setText(productCurrent.getName());
            binding.cbNew.setChecked(productCurrent.isNew());
            binding.cbFamous.setChecked(productCurrent.isFamous());
            binding.edtLink.setText(productCurrent.getThumbnail());
            binding.edtPrice.setText(String.valueOf(productCurrent.getPrice()));
            binding.edtDiscount.setText(String.valueOf(productCurrent.getDiscount()));
            binding.edtDescription.setText(productCurrent.getDescription());
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        productViewModel.setICategoryRepository(new ProductRepositoryImpl());
        categoryViewModel.setICategoryRepository(new CategoryRepositoryImpl());


        categoryViewModel.getAllCategoryList();
        categoryViewModel.getCategoryListLiveData().observe(this, result ->{
            if (result != null && result.getData() != null && !result.getData().isEmpty()){
                categoryList.clear();
                categoryList.addAll(result.getData());
                List<String> categoryNameList = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    categoryNameList = categoryList.stream().map(it -> it.getName()).collect(Collectors.toList());
                }
                SpinnerAdapter cateSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNameList);
                binding.spinnerCategory.setAdapter(cateSpinner);
            }
        });
        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoryList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        productViewModel.getAddProductLiveData().observe(this, result ->{
            if (result != null) {
                if (result.getCode() == 200) {
                    Toast.makeText(this, "Tao thanh cong", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        productViewModel.getEditCategoryLiveData().observe(this, result -> {
            if (result != null){
                if (result.getCode() == 200) {
                    Toast.makeText(this, "Cập nhật thanh cong", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        binding.btnAddProduct.setOnClickListener(view ->{
            String name = binding.edtName.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();
            String link = binding.edtLink.getText().toString().trim();
            String priceStr = binding.edtPrice.getText().toString().trim();
            String discount = binding.edtDiscount.getText().toString().trim();
            if (name.isEmpty() || description.isEmpty() || link.isEmpty() || priceStr.isEmpty() || category == null){
                Toast.makeText(this, "Khong duoc de trong thong tin quan trong", Toast.LENGTH_SHORT).show();
            }else {

                Product product = new Product(name,link,description,category, Long.parseLong(priceStr),
                            Integer.parseInt(discount), binding.cbFamous.isChecked(), binding.cbNew.isChecked()
                        );
                if (isAdd) {
                    productViewModel.addProduct(product);
                }else {
                    product.set_id(productCurrent.get_id());
                    productViewModel.updateProduct(product);
                }
            }
        });

    }
}