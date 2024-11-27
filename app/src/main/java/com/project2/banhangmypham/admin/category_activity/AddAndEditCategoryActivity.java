package com.project2.banhangmypham.admin.category_activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.project2.banhangmypham.admin.repository.CategoryRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.CategoryViewModel;
import com.project2.banhangmypham.databinding.ActivityAddCategoryBinding;
import com.project2.banhangmypham.model.Category;

public class AddAndEditCategoryActivity extends AppCompatActivity {

    ActivityAddCategoryBinding binding;
    public static final String TAG = "AddAndEditCategoryActivity";
    private CategoryViewModel categoryViewModel;
    private Category currentCategory ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.setICategoryRepository(new CategoryRepositoryImpl());
        Intent intent = getIntent();
        String title =  intent.getStringExtra("title");
        boolean isAdd =  intent.getBooleanExtra("isAdd", true);

        Bundle bundle = intent.getExtras();
        if (bundle != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                currentCategory = bundle.getParcelable("category", Category.class);
            }else {
                currentCategory = bundle.getParcelable("category");
            }
        }

        if (isAdd){
            binding.btnAddCategory.setText("Thêm");
            binding.tvTitle.setText("Thêm thể loại");
        }else {
            binding.btnAddCategory.setText("Cập nhật");
            binding.tvTitle.setText("Chỉnh sửa thể loại");
        }
        binding.ivBack.setOnClickListener(view -> finish());
        if (currentCategory != null){
            binding.edtNameCategory.setText(currentCategory.getName());
        }
        categoryViewModel.getAllCategoryList();
        categoryViewModel.getCategoryListLiveData().observe(this, result ->{
            Log.d(TAG, "onCreate: ===> data = "+ result.getData());
            if (result != null && result.getData() != null) {
                Toast.makeText(this, "size = " + result.getData().size(), Toast.LENGTH_SHORT).show();
            }
        });
        categoryViewModel.getAddCategoryLiveData().observe(this, result ->{
            if (result != null) {
                Toast.makeText(this, "Tạo "+result.getName() + " thành cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        binding.btnAddCategory.setOnClickListener(view ->{
            String name = binding.edtNameCategory.getText().toString().trim();
            if (!name.isEmpty()) {
                if (isAdd){
                    // add
                    categoryViewModel.addCategory(new Category(name));
                }else {
                    // update
                    currentCategory.setName(binding.edtNameCategory.getText().toString().trim());
                    categoryViewModel.updateCategory(currentCategory);
                }
            }
        });
    }
}