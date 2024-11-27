package com.project2.banhangmypham.admin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.OrderAdminViewPager;
import com.project2.banhangmypham.adapter.OrderViewPager;
import com.project2.banhangmypham.admin.repository.OrderAdminRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.OrderViewModel;
import com.project2.banhangmypham.databinding.FragmentHistoryOrderAdminBinding;
import com.project2.banhangmypham.databinding.FragmentHistoryOrderBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryOrderAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryOrderAdminFragment extends Fragment {
    public HistoryOrderAdminFragment() {
        // Required empty public constructor
    }
    FragmentHistoryOrderAdminBinding binding ;
    OrderAdminViewPager orderAdminViewPager ;
    OrderViewModel orderViewModel ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryOrderAdminBinding.inflate(getLayoutInflater());
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        orderViewModel.setIOrderAdminRepository(new OrderAdminRepositoryImpl());
        orderAdminViewPager = new OrderAdminViewPager(requireActivity());
        binding.viewPagerOrder.setAdapter(orderAdminViewPager);


        new TabLayoutMediator(binding.tlOrder, binding.viewPagerOrder, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0 :
                        tab.setText("Đang xử lý");
                        break;
                    case 1:
                        tab.setText("Hoàn thành");
                        break;
                }
            }
        }).attach();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        orderViewModel.getAllOrderList();

    }
}