package com.project2.banhangmypham.user.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.OrderViewPager;
import com.project2.banhangmypham.databinding.FragmentHistoryOrderBinding;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.repository.detail_product.DetailProductRepositoryImpl;
import com.project2.banhangmypham.viewmodel.product.DetailProductViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryOrderFragment newInstance(String param1, String param2) {
        HistoryOrderFragment fragment = new HistoryOrderFragment();
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

    private FragmentHistoryOrderBinding binding ;
    private OrderViewPager orderViewPager ;
    private DetailProductViewModel detailProductViewModel ;
    private static String userId ;
    private HashMap<Integer, List<OrderRequest>> ordersMap = new HashMap<>();
    public static void setUserId(String data){
        userId = data ;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryOrderBinding.inflate(getLayoutInflater());
        detailProductViewModel = new ViewModelProvider(requireActivity()).get(DetailProductViewModel.class);
        detailProductViewModel.setIDetailProductRepository(new DetailProductRepositoryImpl());
        orderViewPager = new OrderViewPager(requireActivity());
        binding.viewPagerOrder.setAdapter(orderViewPager);

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
        detailProductViewModel.getAllOrderProductList(userId);
    }

    @Override
    public void onStop() {
        super.onStop();
        detailProductViewModel.clear();
    }
}