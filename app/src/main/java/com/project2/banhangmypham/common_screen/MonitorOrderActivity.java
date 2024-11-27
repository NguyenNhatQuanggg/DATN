package com.project2.banhangmypham.common_screen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.project2.banhangmypham.adapter.ProductCartAdapter;
import com.project2.banhangmypham.admin.repository.OrderAdminRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.OrderViewModel;
import com.project2.banhangmypham.databinding.ActivityMonitorOrderBinding;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderRequestAdmin;
import com.project2.banhangmypham.model.Product;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class MonitorOrderActivity extends AppCompatActivity {

    public static final String TAG = "MonitorOrderActivity";
    ActivityMonitorOrderBinding binding ;
    ProductCartAdapter productCartAdapter ;
    ArrayList<CartProductRequest> dataProductList = new ArrayList<>();
    String idOrder = "";
    OrderViewModel orderViewModel ;
    boolean isAdmin = true;
    OrderRequest orderRequestUser ;
    OrderRequestAdmin orderRequestAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMonitorOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        orderViewModel.setIOrderAdminRepository(new OrderAdminRepositoryImpl());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            isAdmin = bundle.getBoolean("isAdmin",true);
            if (isAdmin){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    orderRequestAdmin = bundle.getParcelable("order", OrderRequestAdmin.class);
                }else{
                    orderRequestAdmin = bundle.getParcelable("order");

                }
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    orderRequestUser = bundle.getParcelable("order", OrderRequest.class);
                }else{
                    orderRequestUser = bundle.getParcelable("order");
                }
            }
        }
        if (orderRequestAdmin != null) {
            idOrder = orderRequestAdmin.get_id();

            binding.btnWatchOrder.setVisibility(orderRequestAdmin.getStatus() >= 2 ? View.VISIBLE : View.GONE);
        }
        if (orderRequestUser != null) {
            idOrder = orderRequestUser.get_id();
            binding.btnWatchOrder.setVisibility(orderRequestUser.getStatus() >= 2 ? View.VISIBLE : View.GONE);

        }
        Log.d(TAG, "onCreate: ====> orderRequestAdmin = " + orderRequestAdmin);
        Log.d(TAG, "onCreate: ====> orderRequestUser = " + orderRequestUser);
        productCartAdapter = new ProductCartAdapter(this, null);
        productCartAdapter.setShowControlLayout(false);
        binding.rcvOrderProduct.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvOrderProduct.hasFixedSize();
        if (orderRequestAdmin != null && orderRequestAdmin.getItems() != null) {
            productCartAdapter.submitList(orderRequestAdmin.getItems());
        }
        if(orderRequestUser != null && orderRequestUser.getItems() != null) {
            productCartAdapter.submitList(orderRequestUser.getItems());
        }
        binding.rcvOrderProduct.setAdapter(productCartAdapter);

        if (isAdmin) {
            if( orderRequestAdmin.getStatus() == 1 ) {
                binding.viewSecond.setBackgroundResource(R.drawable.bg_circle_complete);
                binding.lineFirst.setBackgroundColor(getColor(R.color.colorBlueSelected));
            }
            else if (orderRequestAdmin.getStatus() == 2) {
                binding.viewSecond.setBackgroundResource(R.drawable.bg_circle_complete);
                binding.lineFirst.setBackgroundColor(getColor(R.color.colorBlueSelected));

                binding.lineSecond.setBackgroundColor(getColor(R.color.colorBlueSelected));
                binding.viewThird.setBackgroundResource(R.drawable.bg_circle_complete);
            }
        }
        else {
            if( orderRequestUser.getStatus() == 1 ) {
                binding.lineFirst.setBackgroundColor(getColor(R.color.colorBlueSelected));
                binding.viewSecond.setBackgroundResource(R.drawable.bg_circle_complete);
            }
            else if (orderRequestUser.getStatus() == 2) {
                binding.lineFirst.setBackgroundColor(getColor(R.color.colorBlueSelected));
                binding.viewSecond.setBackgroundResource(R.drawable.bg_circle_complete);
                binding.lineSecond.setBackgroundColor(getColor(R.color.colorBlueSelected));
                binding.viewThird.setBackgroundResource(R.drawable.bg_circle_complete);
            }
        }

        binding.ivBack.setOnClickListener(view -> finish());
        binding.viewSecond.setOnClickListener(view -> {
            if (isAdmin) {
                binding.lineFirst.setBackgroundColor(getColor(R.color.colorBlueSelected));
                binding.viewSecond.setBackgroundColor(getColor(R.color.colorBlueSelected));
                orderViewModel.updateStatusOrder(idOrder, 1);
            }
        });

        binding.viewThird.setOnClickListener(view -> {
            if (isAdmin) {
                binding.lineSecond.setBackgroundColor(getColor(R.color.colorBlueSelected));
                binding.viewThird.setBackgroundColor(getColor(R.color.colorBlueSelected));
                orderViewModel.updateStatusOrder(idOrder, 2);
            }
        });
        binding.btnOrder.setOnClickListener(view ->{
            Intent intent = new Intent(this, OrderDetailActivity.class);
            Bundle bundleOrder = new Bundle();
            if (isAdmin){
                bundleOrder.putParcelable("order", orderRequestAdmin);
                bundleOrder.putBoolean("isAdmin", true);
            }else {
                Log.d(TAG, "orderRequestUser: ===> "+orderRequestUser);
                bundleOrder.putParcelable("order", orderRequestUser);
                bundleOrder.putBoolean("isAdmin", false);
            }
            intent.putExtras(bundleOrder);
            startActivity(intent);
        });
    }
}