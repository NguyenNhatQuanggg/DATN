package com.project2.banhangmypham.user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.AddressTransferAdapter;
import com.project2.banhangmypham.databinding.ActivityAddressTransferBinding;
import com.project2.banhangmypham.databinding.DialogAddAddressBinding;
import com.project2.banhangmypham.model.AddressTransfer;
import com.project2.banhangmypham.repository.address_transfer.AddressTransferRepository;
import com.project2.banhangmypham.viewmodel.address.AddressViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddressTransferActivity extends AppCompatActivity implements AddressTransferAdapter.IAddressTransfer {

    ActivityAddressTransferBinding binding ;
    AddressTransferAdapter addressTransferAdapter ;
    List<AddressTransfer> addressTransferList = new ArrayList<>();
    AddressViewModel addressViewModel ;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddressTransferBinding.inflate(getLayoutInflater());
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        addressViewModel.setAddressTransferRepository(new AddressTransferRepository());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(view -> finish());
        userId = getIntent().getStringExtra("userId");
        addressTransferAdapter = new AddressTransferAdapter(this);
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addressViewModel.getAllAddressTransferList(userId);

        addressViewModel.getAllAddressTransferLiveData().observe(this, result ->{
            if (result != null){
                addressTransferList.clear();
                addressTransferList.addAll(result.getAddressResponseList());
                addressTransferAdapter.submitList(result.getAddressResponseList());
            }
        });

        binding.rcvAddressTransfer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.rcvAddressTransfer.hasFixedSize();
        binding.rcvAddressTransfer.setAdapter(addressTransferAdapter);

        binding.btnAddAddress.setOnClickListener(view ->{
            showDialog();
        });
    }
    void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogAddAddressBinding binding1 = DialogAddAddressBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        Window window = getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        binding1.btnAdd.setOnClickListener(view ->{
            String name = binding1.edtName.getText().toString().trim();
            String phone = binding1.edtPhone.getText().toString().trim();
            String address = binding1.edtAddress.getText().toString().trim();
            AddressTransfer addressTransfer = new AddressTransfer(name,phone,address);
            addressTransfer.setUid(userId);
            addressViewModel.addAddressTransfer(addressTransfer);
            dialog.dismiss();
        });

        binding1.btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClickItem(int position) {
        CartProductActivity.setAddressTransfer(addressTransferList.get(position));
        addressTransferAdapter.setSelectedIndex(position);
        addressTransferAdapter.notifyDataSetChanged();
    }
}