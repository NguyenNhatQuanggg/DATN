package com.project2.banhangmypham.admin;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.admin.admin_profile.AdminProfileFragment;
import com.project2.banhangmypham.admin.fragment.CategoryFragment;
import com.project2.banhangmypham.admin.fragment.HistoryOrderAdminFragment;
import com.project2.banhangmypham.admin.fragment.ProductFragment;
import com.project2.banhangmypham.databinding.ActivityHomeAdminBinding;
import com.project2.banhangmypham.model.User;

public class HomeAdminActivity extends AppCompatActivity {

    ActivityHomeAdminBinding binding ;
    Fragment fragment;
    int newPosition, startingPosition;
    User adminUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                adminUser = bundle.getParcelable("loginState", User.class);
            }else {
                adminUser = bundle.getParcelable("loginState");
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            fragment = null;
            newPosition = 0;

            int itemId = menuItem.getItemId();
            if (itemId == R.id.nav_danhmuc) {
                fragment = new CategoryFragment();
                newPosition = 2;
            } else if (itemId == R.id.nav_product) {
                fragment = new ProductFragment();
                newPosition = 3;
            } else if (itemId == R.id.nav_order) {
                    fragment = new HistoryOrderAdminFragment();
                newPosition = 4;
            } else if (itemId == R.id.nav_nguoidung) {
                fragment = new AdminProfileFragment();
                newPosition = 5;
            }
            return loadFragment(fragment, newPosition);
        });
        loadFragment(new CategoryFragment(), 1);
    }
    private boolean loadFragment(Fragment fragment, int newPosition) {
        if (fragment != null) {
            if (startingPosition > newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
            if (startingPosition < newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.commit();
            }
            startingPosition = newPosition;
            return true;
        }
        return false;
    }
}