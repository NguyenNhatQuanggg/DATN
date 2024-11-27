package com.project2.banhangmypham.admin.admin_profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project2.banhangmypham.admin.ProductHotSellerActivity;
import com.project2.banhangmypham.admin.StaticOrderActivity;
import com.project2.banhangmypham.admin.comment_activity.CommentProductActivity;
import com.project2.banhangmypham.admin.discount_admin_acitivity.DiscountAdminActivity;
import com.project2.banhangmypham.common_screen.LoginActivity;
import com.project2.banhangmypham.databinding.FragmentAdminProfileBinding;
import com.project2.banhangmypham.localstorage.LocalStorageManager;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProfileFragment newInstance(String param1, String param2) {
        AdminProfileFragment fragment = new AdminProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    FragmentAdminProfileBinding binding;
    User adminUser ;
    AccountViewModel accountViewModel;
    public void setAdminUser(User data){
        adminUser = data ;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProfileBinding.inflate(getLayoutInflater());
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());
        binding.accountManagement.setOnClickListener(view ->{
            Intent intent = new Intent(requireActivity(), UserManagementActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("adminState", adminUser);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        binding.discountManagement.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), DiscountAdminActivity.class);
            startActivity(intent);
        });

        binding.commentsManagement.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), CommentProductActivity.class);
            startActivity(intent);
        });

        binding.revenuaManagement.setOnClickListener(view ->{
            Intent intent = new Intent(requireActivity(), StaticOrderActivity.class);
            startActivity(intent);
        });

        binding.productSellerManagement.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), ProductHotSellerActivity.class);
            startActivity(intent);
        });

        binding.userDangxuat.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            accountViewModel.logOut();
            LocalStorageManager.getInstance().saveToken("");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        return binding.getRoot();
    }
}