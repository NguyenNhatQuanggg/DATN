package com.project2.banhangmypham.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project2.banhangmypham.admin.fragment.CompletingOrderAdminFragment;
import com.project2.banhangmypham.admin.fragment.ExecutingOrderAdminFragment;

public class OrderAdminViewPager extends FragmentStateAdapter {
    public static final String TAG = "OrderViewPager";
    public OrderAdminViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return new ExecutingOrderAdminFragment();
            case 1:
                return new CompletingOrderAdminFragment();
            default:
                return new ExecutingOrderAdminFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}