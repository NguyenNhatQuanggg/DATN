package com.project2.banhangmypham.common_screen;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.databinding.ActivityOrderDetailBinding;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderRequestAdmin;
import com.project2.banhangmypham.utils.Utils;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String TAG = "OrderDetailActivity";
    ActivityOrderDetailBinding binding ;
    boolean isAdmin = true;
    OrderRequestAdmin orderRequestAdmin;
    OrderRequest orderRequestUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            isAdmin = bundle.getBoolean("isAdmin", true);
            if (isAdmin){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    orderRequestAdmin = bundle.getParcelable("order", OrderRequestAdmin.class);
                }else {
                    orderRequestAdmin = bundle.getParcelable("order");
                }
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    orderRequestUser = bundle.getParcelable("order", OrderRequest.class);
                }else {
                    orderRequestUser = bundle.getParcelable("order");
                }
            }
        };

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (orderRequestAdmin != null) {
            binding.tvIdOrder.setText(orderRequestAdmin.get_id());
            Log.d(TAG, "onCreate: ===> time = "+orderRequestAdmin);
            binding.tvTime.setText(orderRequestAdmin.getCreateDate());
            binding.tvPriceTotalWithoutDiscount.setText(Utils.convertToMoneyVN(orderRequestAdmin.getAmount()));
            if (orderRequestAdmin.getDiscountModel() != null) {
                long priceOrder = orderRequestAdmin.getAmount() * orderRequestAdmin.getDiscountModel().getValue() / 100;
                binding.tvMoneyDiscount.setText(Utils.convertToMoneyVN(priceOrder));
            } else {
                binding.tvMoneyDiscount.setText(Utils.convertToMoneyVN(0));
            }
            binding.totalMoneyBothDiscount.setText(Utils.convertToMoneyVN(orderRequestAdmin.getDiscounted_amount()));
            switch (orderRequestAdmin.getMethodPayment()) {
                case 1: {
                    binding.tvPaymentMethod.setText("Thanh toan tien mat");
                    break;
                }
                case 2: {
                    binding.tvPaymentMethod.setText("Credit or debit card");

                    break;
                }
                case 3: {
                    binding.tvPaymentMethod.setText("Chuyen khoan ngan hang");

                    break;
                }
                case 4: {
                    binding.tvPaymentMethod.setText("ZaloPay");
                    break;
                }
            }
            Log.d(TAG, "address: ="+orderRequestAdmin.getAddressTransfer());
            if (orderRequestAdmin.getAddressTransfer() != null) {
                binding.tvNameReceive.setText(orderRequestAdmin.getAddressTransfer().getNameReceiver());
                binding.tvPhoneReceive.setText(orderRequestAdmin.getAddressTransfer().getPhoneReceiver());
                binding.tvAddress.setText(orderRequestAdmin.getAddressTransfer().getAddressReceiver());
            }
        }
        if (orderRequestUser != null) {
            binding.tvIdOrder.setText(orderRequestUser.get_id());
            binding.tvPriceTotalWithoutDiscount.setText(Utils.convertToMoneyVN(orderRequestUser.getAmount()));
            if (orderRequestUser.getDiscountModel() != null) {
                long priceOrder = orderRequestUser.getAmount() * orderRequestUser.getDiscountModel().getValue() / 100;
                binding.tvMoneyDiscount.setText(Utils.convertToMoneyVN(priceOrder));
            } else {
                binding.tvMoneyDiscount.setText(Utils.convertToMoneyVN(0));
            }
            binding.totalMoneyBothDiscount.setText(Utils.convertToMoneyVN(orderRequestUser.getDiscounted_amount()));
            switch (orderRequestUser.getMethodPayment()) {
                case 1: {
                    binding.tvPaymentMethod.setText("Thanh toan tien mat");
                    break;
                }
                case 2: {
                    binding.tvPaymentMethod.setText("Credit or debit card");

                    break;
                }
                case 3: {
                    binding.tvPaymentMethod.setText("Chuyen khoan ngan hang");

                    break;
                }
                case 4: {
                    binding.tvPaymentMethod.setText("ZaloPay");
                    break;
                }
            }
            Log.d(TAG, "address: ="+orderRequestUser.getAddressTransfer());
            if (orderRequestUser.getAddressTransfer() != null) {
                binding.tvNameReceive.setText(orderRequestUser.getAddressTransfer().getNameReceiver());
                binding.tvPhoneReceive.setText(orderRequestUser.getAddressTransfer().getPhoneReceiver());
                binding.tvAddress.setText(orderRequestUser.getAddressTransfer().getAddressReceiver());
            }
        }
    }
}