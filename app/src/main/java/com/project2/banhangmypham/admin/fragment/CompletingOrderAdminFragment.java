package com.project2.banhangmypham.admin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.OrderAdapter;
import com.project2.banhangmypham.adapter.OrderAdminAdapter;
import com.project2.banhangmypham.admin.viewmodel.OrderViewModel;
import com.project2.banhangmypham.common_screen.MonitorOrderActivity;
import com.project2.banhangmypham.common_screen.OrderDetailActivity;
import com.project2.banhangmypham.databinding.FragmentCompletingOrderAdminBinding;
import com.project2.banhangmypham.databinding.FragmentCompletingOrderBinding;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderRequestAdmin;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletingOrderAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletingOrderAdminFragment extends Fragment implements OrderAdminAdapter.IOrderEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CompletingOrderAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletingOrderAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletingOrderAdminFragment newInstance(String param1, String param2) {
        CompletingOrderAdminFragment fragment = new CompletingOrderAdminFragment();
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

    FragmentCompletingOrderAdminBinding binding ;
    private OrderAdminAdapter orderAdapter ;
    OrderViewModel orderViewModel ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompletingOrderAdminBinding.inflate(getLayoutInflater());
        orderAdapter = new OrderAdminAdapter(requireContext(), this);
        orderAdapter.setIsCompletingOrder(true);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        orderViewModel.getOrderCompletingResponseLiveData().observe((LifecycleOwner) requireContext(), result ->{
            orderAdapter.submitList(result);
        });
        binding.rcvOrderCompleting.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.rcvOrderCompleting.hasFixedSize();
        binding.rcvOrderCompleting.setAdapter(orderAdapter);


        return binding.getRoot();
    }

    @Override
    public void onClickItemProduct(OrderRequestAdmin orderRequest, boolean isCompleting) {
        ArrayList<CartProductRequest> data =  new ArrayList<>(orderRequest.getItems());
        Intent intent = new Intent(requireActivity(), OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        Log.d("TAG", "onClickItemProduct: ====> "+orderRequest);
        bundle.putParcelable("order", orderRequest);
        bundle.putBoolean("isAdmin",true);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}