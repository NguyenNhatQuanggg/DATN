package com.project2.banhangmypham.user;

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

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.DiscountAdapter;
import com.project2.banhangmypham.databinding.ActivityDiscountProductBinding;
import com.project2.banhangmypham.model.DiscountModel;
import com.project2.banhangmypham.repository.discount.DiscountRepositoryImpl;
import com.project2.banhangmypham.viewmodel.discount.DiscountViewModel;

public class DiscountProductActivity extends AppCompatActivity implements DiscountAdapter.IDiscountEvenListener {

   ActivityDiscountProductBinding binding ;
   DiscountAdapter discountAdapter ;
   DiscountViewModel discountViewModel ;
   String userId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDiscountProductBinding.inflate(getLayoutInflater());
        discountAdapter = new DiscountAdapter(this,this,0);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userId = getIntent().getStringExtra("userId");

        binding.ivBack.setOnClickListener(view -> finish());
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        discountViewModel.setIDiscountRepository(new DiscountRepositoryImpl());
        discountViewModel.getAllDiscountsList(userId);

        discountViewModel.getDiscountResponseLiveData().observe(this, result -> {
            if (result != null && result.getDiscountModelList() != null){
                Toast.makeText(this, "result = "+ result.getDiscountModelList().size(), Toast.LENGTH_SHORT).show();
                discountAdapter.submitList(result.getDiscountModelList());
            }
        });
        binding.rcvDiscount.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.rcvDiscount.hasFixedSize();
        binding.rcvDiscount.setAdapter(discountAdapter);

    }

    @Override
    public void onClickItemDiscount(DiscountModel discountModel) {
        CartProductActivity.setDiscountModel(discountModel);
        finish();
    }
}