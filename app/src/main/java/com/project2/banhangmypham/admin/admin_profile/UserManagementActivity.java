package com.project2.banhangmypham.admin.admin_profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.UserManagementAdapter;
import com.project2.banhangmypham.admin.chat.AdminChatActivity;
import com.project2.banhangmypham.admin.repository.UserRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.UserViewModel;
import com.project2.banhangmypham.databinding.ActivityUserManagementBinding;
import com.project2.banhangmypham.model.User;

public class UserManagementActivity extends AppCompatActivity implements UserManagementAdapter.IUserEventItem {

    ActivityUserManagementBinding binding ;
    UserManagementAdapter userManagementAdapter;
    UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUserManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setUserRepository(new UserRepositoryImpl());
        userViewModel.getAllUsersList();
        userViewModel.getUsersListLiveData().observe(this, result ->{
            if (result != null && result.getData() != null){
                userManagementAdapter.submitList(result.getData());
            }
        });
        userManagementAdapter = new UserManagementAdapter(this, this);

        binding.rcvUser.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvUser.setHasFixedSize(true);
        binding.rcvUser.setAdapter(userManagementAdapter);

        binding.ivBack.setOnClickListener(view -> finish());
    }

    @Override
    public void onClickItem(User user) {
        Intent intentUser = new Intent(this , AdminChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("loginState", user);
        intentUser.putExtras(bundle);
        startActivity(intentUser);
    }

    @Override
    protected void onStop() {
        super.onStop();
        userViewModel.clear();
    }
}