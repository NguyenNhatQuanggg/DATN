package com.project2.banhangmypham.user.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.project2.banhangmypham.common_screen.LoginActivity;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.databinding.FragmentUserBinding;
import com.project2.banhangmypham.localstorage.LocalStorageManager;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.user.ChatActivity;
import com.project2.banhangmypham.user.FavoriteProductUserActivity;
import com.project2.banhangmypham.user.UserActivity;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

public class UserFragment extends Fragment implements View.OnClickListener {

    Intent intent;
    private AccountViewModel accountViewModel ;
    private FragmentUserBinding binding ;
    User currentUser;
    public static final String TAG = "UserFragment";
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(getLayoutInflater());
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        currentUser = accountViewModel.getUserInfoLiveData().getValue();

        accountViewModel.getUserInfoLiveData().observe((LifecycleOwner) requireContext(), result ->{
            if (result != null) {
                Log.d(TAG, "onCreateView: ====> result = "+ result.getFull_name());
                Log.d(TAG, "onCreateView: ====> result = "+ result.getUsername());
                Log.d(TAG, "onCreateView: ====> result = "+ result.getProfile_image());

                binding.userName.setText(result.getFull_name());
                binding.userEmail.setText(result.getUsername());

                Glide.with(requireActivity()).load(result.getProfile_image())
                        .placeholder(R.drawable.noimage).into(binding.userImg1);
            }
        });
        binding.btnUserGiohang.setOnClickListener(this);
        binding.userYeuthich.setOnClickListener(this);
        binding.userDangxuat.setOnClickListener(this);
        binding.userHotro.setOnClickListener(this);
        binding.userDiachi.setOnClickListener(this);
        binding.userCaidat.setOnClickListener(this);
        binding.userChat.setOnClickListener(this);
        return binding.getRoot();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_user_giohang) {
           // intent = new Intent(requireActivity(), GioHangActivity.class);
            startActivity(intent);
        }  else if (id == R.id.user_yeuthich) {
            intent = new Intent(requireActivity(), FavoriteProductUserActivity.class);
            intent.putExtra("userId", currentUser.get_id());
            startActivity(intent);
        }  else if (id == R.id.user_dangxuat) {
            accountViewModel.logOut();
            requireActivity().finish();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
        } else if (id == R.id.user_diachi) {
            intent = new Intent(requireActivity(), UserActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("loginState", currentUser);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.user_chat) {
            intent = new Intent(requireActivity(), ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("loginState", currentUser);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.user_dangxuat) {
            accountViewModel.logOut();
            LocalStorageManager.getInstance().saveToken("");
            Intent loginIntent = new Intent(requireActivity(), LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accountViewModel.clear();
    }
}