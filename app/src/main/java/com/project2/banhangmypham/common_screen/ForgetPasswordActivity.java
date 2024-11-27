package com.project2.banhangmypham.common_screen;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.databinding.ActivityForgetPasswordBinding;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

public class ForgetPasswordActivity extends AppCompatActivity {

    ActivityForgetPasswordBinding binding ;
    private AccountViewModel accountViewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());

        accountViewModel.getResetPasswordLiveData().observe(this, result -> {
            if (result != null) {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnSendEmail.setOnClickListener(view ->{
            String email = binding.edtEmail.getText().toString().trim();
            accountViewModel.resetPassword(email);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        accountViewModel.clear();
    }
}