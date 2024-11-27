package com.project2.banhangmypham.user;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.project2.banhangmypham.databinding.ActivityUserBinding;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.utils.Utils;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;
import com.project2.banhangmypham.viewmodel.account.LoginState;

import java.io.IOException;
import java.util.Base64;

public class UserActivity extends AppCompatActivity {

    public static final String TAG = "UserActivity";
    ActivityUserBinding binding ;
    private AccountViewModel accountViewModel ;
    User userCurrent;
    int image = 0;
    String uriImage;
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    binding.avatar.setImageURI(uri);
                    try {
                        Bitmap bitmapUri = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        byte[] data = Utils.convertBitmapToByteArray(bitmapUri);
                        uriImage = Base64.getEncoder().encodeToString(data);
                        image = 1;
                    } catch (IOException e) {
                        Log.d(TAG, "Exception = : " + e.getMessage());
                    }
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                userCurrent = bundle.getParcelable("loginState", User.class);
            }else {
                userCurrent = bundle.getParcelable("loginState");
            }
            accountViewModel.setLoginState(userCurrent);
        }
        accountViewModel.getUserInfoLiveData().observe(this, result ->{
                if (result != null) {
                    binding.textDiachi.setText(result.getAddress());
                    binding.textEmail.setText(result.getUsername());
                    binding.textName.setText(result.getFull_name());
                    binding.textPhone.setText(result.getPhone());
                    uriImage = result.getProfile_image();
                    image = 1;
                    Glide.with(this).load(result.getProfile_image())
                            .placeholder(R.drawable.noimage).into(binding.avatar);
                }
        });

        accountViewModel.getChangePasswordLiveData().observe(this, result -> {
            if (result != null) {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        accountViewModel.getUpdateInfoLiveData().observe(this, result -> {
            if (result != null) {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.avatar.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                    .build());
        });

        binding.btnSaveChangePass.setOnClickListener(view -> {
            String newPassword = binding.edtNewPassword.getText().toString().trim();
            String oldPassword = binding.edtPassword.getText().toString().trim();
            String confirmPassword = binding.edtNewPasswordConfirm.getText().toString().trim();
            if (newPassword.length() > 6 && newPassword.equals(confirmPassword)) {
                accountViewModel.changePassword(newPassword,oldPassword,userCurrent.get_id());
            }else {
                Toast.makeText(this, "Vui lòng nhập nhiều hơn 6 ký tự cho mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivBack.setOnClickListener(view -> finish());
        binding.btnUpdateAccount.setOnClickListener(view -> {
            String address = binding.textDiachi.getText().toString();
            String phone = binding.textPhone.getText().toString();
            if (!address.isEmpty() && !phone.isEmpty()) {
                if (userCurrent != null) {
                    User user = userCurrent;
                    user.setAddress(address);
                    user.setPhone(phone);
                    accountViewModel.updateInfoAccount(user);
                }
            } else {
                Toast.makeText(this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
