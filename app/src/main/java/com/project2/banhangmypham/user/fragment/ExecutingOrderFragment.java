package com.project2.banhangmypham.user.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.OrderAdapter;
import com.project2.banhangmypham.common_screen.MonitorOrderActivity;
import com.project2.banhangmypham.databinding.FragmentExecutingOrderBinding;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.viewmodel.product.DetailProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExecutingOrderFragment extends Fragment implements OrderAdapter.IOrderEventListener {
    private  final List<OrderRequest> orderRequestList = new ArrayList<>();
    public static final String TAG = "ExecutingOrderFragment";
    public ExecutingOrderFragment() {
        // Required empty public constructor
    }
    FragmentExecutingOrderBinding binding;
    OrderAdapter orderAdapter;
    DetailProductViewModel detailProductViewModel ;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExecutingOrderBinding.inflate(getLayoutInflater());
        orderAdapter = new OrderAdapter(requireContext(), this);
        orderAdapter.setIsCompletingOrder(false);

        detailProductViewModel = new ViewModelProvider(requireActivity()).get(DetailProductViewModel.class);
        detailProductViewModel.getOrderExecutingResponseLiveData().observe((LifecycleOwner) requireContext(), this::showInfoProductIfValid);

        binding.rcvOrderExecuting.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.rcvOrderExecuting.hasFixedSize();
        binding.rcvOrderExecuting.setAdapter(orderAdapter);

        return binding.getRoot();
    }

    @Override
    public void onClickItemProduct(OrderRequest orderRequest, boolean isCompleting) {
        ArrayList<CartProductRequest> data =  new ArrayList<>(orderRequest.getItems());
        Intent intent = new Intent(requireActivity(), MonitorOrderActivity.class);
        Bundle bundle = new Bundle();
        Log.d(TAG, "onClickItemProduct: ===> orderRequest = " + orderRequest);
        bundle.putParcelable("order", orderRequest);
        bundle.putBoolean("isAdmin",false);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void showInfoProductIfValid(List<OrderRequest> dataProductList) {
        Toast.makeText(requireContext(), "data = "+dataProductList.size(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showInfoProductIfValid: ");
        if (dataProductList.isEmpty()) {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.rcvOrderExecuting.setVisibility(View.GONE);
        }else {
            binding.tvError.setVisibility(View.GONE);
            binding.rcvOrderExecuting.setVisibility(View.VISIBLE);
            orderAdapter.submitList(dataProductList);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        detailProductViewModel.clear();
    }
}