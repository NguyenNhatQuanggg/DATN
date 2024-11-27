package com.project2.banhangmypham.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.adapter.ProductCartAdapter;
import com.project2.banhangmypham.databinding.ActivityCartProductBinding;
import com.project2.banhangmypham.model.AddressTransfer;
import com.project2.banhangmypham.model.CartDeleteRequest;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.DiscountModel;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.repository.detail_product.DetailProductRepositoryImpl;
import com.project2.banhangmypham.utils.Utils;
import com.project2.banhangmypham.viewmodel.product.DetailProductViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartProductActivity extends AppCompatActivity implements ProductCartAdapter.ICartProductEventListener {

    public static final String TAG = "CartProductActivity";
    ActivityCartProductBinding binding ;
    DetailProductViewModel detailProductViewModel ;
    String userId = "";
    ProductCartAdapter productCartAdapter ;
    List<CartProductRequest> cartProductRequestList = new ArrayList<>();

    private static DiscountModel discountModel;
    private static AddressTransfer addressTransfer ;
    private static int methodPayment = -1;

    private long totalCart = 0;
    public static void setMethodPayment(int data){
        methodPayment = data ;
    }

    public static void setDiscountModel(DiscountModel data){
        discountModel = data ;
    }

    public static void setAddressTransfer(AddressTransfer data){
        addressTransfer = data;
    }

    private long amountMoneyWithoutDiscount = 0 ;
    private long amountMoneyDiscount = 0 ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCartProductBinding.inflate(getLayoutInflater());
        productCartAdapter = new ProductCartAdapter(this, this);
        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);
        detailProductViewModel.setIDetailProductRepository(new DetailProductRepositoryImpl());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        detailProductViewModel.getAllCartProductList(userId);
        detailProductViewModel.getListProductLiveData().observe(this, result -> {
            if (result != null && result.getData() != null){
                cartProductRequestList.clear();
                result.getData().getCartProductRequestList().forEach(it -> it.setUserId(userId));
                cartProductRequestList.addAll(result.getData().getCartProductRequestList());
                showInfoProductIfValid(cartProductRequestList);
                binding.tvTotalMoney.setText(Utils.convertToMoneyVN(result.getData().getTotalCart()));
                totalCart = result.getData().getTotalCart();
                binding.tvNumberProductForPrice.setText("("+cartProductRequestList.size() + " sản phẩm)");
                binding.tvPriceTotal.setText(Utils.convertToMoneyVN(result.getData().getTotalCart()));
                amountMoneyWithoutDiscount = totalCart;
            }
        });

        detailProductViewModel.getCreateOrderLiveData().observe(this, result -> {
            if (result != null) {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.getCode() == 200){
                    cartProductRequestList.clear();
                    productCartAdapter.submitList(cartProductRequestList);
                    finish();
                }
            }
        });
        binding.rcvCart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvCart.hasFixedSize();
        binding.rcvCart.setAdapter(productCartAdapter);
        binding.btnAddToCart.setOnClickListener(view ->{
            // go to home
            finish();
        });

        binding.ivBack.setOnClickListener(view -> finish());
        binding.btnPaymentScreen.setOnClickListener(view -> {
            // go to payment methoid
            Intent intentMethod = new Intent(this, MethodPaymentActivity.class);
            startActivity(intentMethod);
        });

        binding.btnGoToAddress.setOnClickListener(view -> {
            Intent intentAddress = new Intent(CartProductActivity.this, AddressTransferActivity.class);
            intentAddress.putExtra("userId", userId);
            startActivity(intentAddress);
        });
        binding.btnGoToDiscount.setOnClickListener(view -> {
            // go to discount screen
            Intent intentDiscount = new Intent(CartProductActivity.this, DiscountProductActivity.class);
            intentDiscount.putExtra("userId", userId);
            startActivity(intentDiscount);
        });
        binding.btnOrder.setOnClickListener(view -> {
            Toast.makeText(this, "amountMoneyWithoutDiscount = "+amountMoneyWithoutDiscount, Toast.LENGTH_SHORT).show();
            OrderRequest orderRequest = new OrderRequest(userId,cartProductRequestList,methodPayment,addressTransfer,discountModel,amountMoneyWithoutDiscount);
            detailProductViewModel.createOrderProductList(orderRequest);
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onIncrease(int number, int position) {
        productCartAdapter.notifyItemChanged(position);
        String data = Utils.convertToMoneyVN(productCartAdapter.getTotalMoney());
        binding.tvTotalMoney.setText(data);
        binding.tvPriceTotal.setText(data);
        totalCart = productCartAdapter.getTotalMoney();
        amountMoneyWithoutDiscount = totalCart;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDecrease(int number, int position) {
        productCartAdapter.notifyItemChanged(position);
        String data = Utils.convertToMoneyVN(productCartAdapter.getTotalMoney());
        binding.tvTotalMoney.setText(data);
        binding.tvPriceTotal.setText(data);
        totalCart = productCartAdapter.getTotalMoney();
        amountMoneyWithoutDiscount = totalCart;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDelete(Product product, int position) {
        Log.d(TAG, "onDelete: ====> before = " + cartProductRequestList.size());
        cartProductRequestList.removeIf(it -> it.getProduct().get_id().equals(product.get_id()));
        Log.d(TAG, "onDelete: ====> after = " + cartProductRequestList.size());
        productCartAdapter.submitList(cartProductRequestList);
        showInfoProductIfValid(cartProductRequestList);
        String data = Utils.convertToMoneyVN(productCartAdapter.getTotalMoney());
        binding.tvTotalMoney.setText(data);
        binding.tvPriceTotal.setText(data);
        totalCart = productCartAdapter.getTotalMoney();
        binding.tvNumberProductForPrice.setText("("+cartProductRequestList.size() + " sản phẩm)");
        amountMoneyWithoutDiscount = totalCart;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        if (discountModel != null){
            binding.tvNameDiscount.setText(discountModel.getTitleDiscount());
        }
        if (addressTransfer != null) {
            binding.tvNameAddress.setText(addressTransfer.getAddressReceiver());
        }
        if (methodPayment != -1 ) {
            switch (methodPayment){
                case 0 : {
                    binding.tvNamePayment.setText("Thanh toan tien mat");
                    break;
                }
                case 1 : {
                    binding.tvNamePayment.setText("Credit or debit card");
                    break;
                }
                case 2 : {
                    binding.tvNamePayment.setText("Chuyen khoan ngan hang");
                    break;
                }
                case 3 : {
                    binding.tvNamePayment.setText("ZaloPay");
                    break;
                }
            }
        }
        if (discountModel != null) {
            long moneyDiscount = totalCart * discountModel.getValue()/100;
            binding.tvContentDiscount.setText(discountModel.getSubTitleDiscount());
            binding.tvDiscountProduct.setText("-"+ Utils.convertToMoneyVN(moneyDiscount));
            binding.tvTotalMoney.setText(Utils.convertToMoneyVN(totalCart - moneyDiscount) );
        }
    }
    private void showInfoProductIfValid(List<CartProductRequest> dataProductList) {
        if (dataProductList.isEmpty()) {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.rcvCart.setVisibility(View.GONE);
        }else {
            binding.tvError.setVisibility(View.GONE);
            binding.rcvCart.setVisibility(View.VISIBLE);
            productCartAdapter.submitList(dataProductList);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        detailProductViewModel.updateAllCartProductList(new CartDeleteRequest(userId, cartProductRequestList));
    }
}