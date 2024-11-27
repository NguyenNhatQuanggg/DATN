package com.project2.banhangmypham.admin.discount_admin_acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.adapter.DiscountAdapter;
import com.project2.banhangmypham.admin.repository.DiscountRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.DiscountViewModel;
import com.project2.banhangmypham.databinding.ActivityDiscountAdminBinding;
import com.project2.banhangmypham.model.DiscountModel;

public class DiscountAdminActivity extends AppCompatActivity implements DiscountAdapter.IDiscountEvenListener{

    ActivityDiscountAdminBinding binding ;
    DiscountViewModel discountViewModel ;
    DiscountAdapter discountAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDiscountAdminBinding.inflate(getLayoutInflater());
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        discountViewModel.setIDiscountRepository(new DiscountRepositoryImpl());
        setContentView(binding.getRoot());
        discountAdapter = new DiscountAdapter(this, this, 0);

        discountViewModel.getDiscountListLiveData().observe(this, result -> {
            if (result != null) {
                Toast.makeText(this, "size = " + result.getDiscountModelList().size(), Toast.LENGTH_SHORT).show();
                discountAdapter.submitList(result.getDiscountModelList());
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.rcvDiscountAdmin.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvDiscountAdmin.hasFixedSize();
        binding.rcvDiscountAdmin.setAdapter(discountAdapter);

        binding.btnAdd.setOnClickListener(view -> {
            Intent discountIntent = new Intent(this, AddAndEditDiscountAdminActivity.class);
            startActivity(discountIntent);
        });
    }

    @Override
    public void onClickItemDiscount(DiscountModel discountModel) {
        Intent intent = new Intent(this, AddAndEditDiscountAdminActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("discount", discountModel);
        intent.putExtra("isAdd", false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        discountViewModel.getAllDiscountList();

    }
}