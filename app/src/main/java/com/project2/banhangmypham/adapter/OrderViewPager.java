package com.project2.banhangmypham.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.user.fragment.CompletingOrderFragment;
import com.project2.banhangmypham.user.fragment.ExecutingOrderFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OrderViewPager extends FragmentStateAdapter {
    public static final String TAG = "OrderViewPager";
    private HashMap<Integer, List<OrderRequest>> dataMap = new HashMap<>();
    public OrderViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    private HashMap<Integer, Fragment> fragmentsMap = new HashMap<>();
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return new ExecutingOrderFragment();
            case 1:

                return new CompletingOrderFragment();
            default:

                return new ExecutingOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
