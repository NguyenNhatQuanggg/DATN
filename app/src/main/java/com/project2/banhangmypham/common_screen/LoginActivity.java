package com.project2.banhangmypham.common_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.project2.banhangmypham.admin.HomeAdminActivity;
import com.project2.banhangmypham.animation.LoadingDialog;
import com.project2.banhangmypham.MainActivity;
import com.project2.banhangmypham.databinding.ActivityLoginBinding;
import com.project2.banhangmypham.localstorage.LocalStorageManager;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

public class LoginActivity extends AppCompatActivity {
    TextView forgot, register;
    Button login;
    EditText user, password;
    FirebaseAuth mAuth;
    LoadingDialog loadingDialog;
    private boolean doubleClick = false;
    private AccountViewModel accountViewModel ;
    private Handler uiHandler = new Handler(Looper.getMainLooper());
    private ActivityLoginBinding binding ;
    public static String token = "";
    public static User userCurrent ;

    public static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());
        binding.loginUser.setText("ducbh62cfvn@gmail.com");
//        binding.loginUser.setText("ducbh62tlbb@gmail.com");
//        binding.loginPass.setText("1234567");
        binding.loginPass.setText("123456789");
        accountViewModel.getLoggedLiveData().observe(this, loginState -> {
            if (loginState != null && loginState.getCode() == 200) {
                LocalStorageManager.getInstance().saveToken(loginState.getToken());
                ApiClientService.getInstance().setToken(loginState.getToken());
                String userInfo = new Gson().toJson(loginState.getUser());
                LocalStorageManager.getInstance().saveUserInfo(userInfo);
                userCurrent = loginState.getUser();
                if (loginState.getUser().isIs_admin()){

                    Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("loginState", loginState.getUser());
                    Log.d(TAG, "user: =" + loginState.getUser());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }else if (loginState != null ){
                Toast.makeText(this, "1: "+loginState.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.forgotPassword.setOnClickListener(view ->{
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = binding.loginUser.getText().toString().trim();
                String passWord = binding.loginPass.getText().toString().trim();
                if (passWord.length() > 6 ) {
                    accountViewModel.login(passWord, userName);
                }else {
                    Toast.makeText(LoginActivity.this, "Mat khau khong dung", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleClick)
                    finish();
                doubleClick = true;
                uiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleClick = false;
                    }
                }, 1500);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        uiHandler.removeCallbacksAndMessages(null);
        accountViewModel.clear();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiHandler = null;
    }
}

