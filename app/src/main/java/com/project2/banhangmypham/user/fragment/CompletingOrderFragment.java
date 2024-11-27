package com.project2.banhangmypham.user.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.OrderAdapter;
import com.project2.banhangmypham.common_screen.MonitorOrderActivity;
import com.project2.banhangmypham.common_screen.OrderDetailActivity;
import com.project2.banhangmypham.databinding.FragmentCompletingOrderBinding;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.repository.detail_product.DetailProductRepositoryImpl;
import com.project2.banhangmypham.viewmodel.product.DetailProductViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletingOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletingOrderFragment extends Fragment implements OrderAdapter.IOrderEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CompletingOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletingOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletingOrderFragment newInstance(String param1, String param2) {
        CompletingOrderFragment fragment = new CompletingOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private  OrderAdapter orderAdapter ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    FragmentCompletingOrderBinding binding ;
    DetailProductViewModel detailProductViewModel ;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompletingOrderBinding.inflate(getLayoutInflater());
        detailProductViewModel = new ViewModelProvider(requireActivity()).get(DetailProductViewModel.class);
        orderAdapter = new OrderAdapter(requireContext(), this);
        orderAdapter.setIsCompletingOrder(true);

        detailProductViewModel.getOrderCompletingResponseLiveData().observe((LifecycleOwner) requireContext(), this::showInfoProductIfValid);
        binding.rcvOrderCompleting.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.rcvOrderCompleting.hasFixedSize();
        binding.rcvOrderCompleting.setAdapter(orderAdapter);
        return binding.getRoot();
    }

    @Override
    public void onClickItemProduct(OrderRequest orderRequest, boolean isCompleting) {
        ArrayList<CartProductRequest> data =  new ArrayList<>(orderRequest.getItems());
        Intent intent = new Intent(requireActivity(), OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("order", orderRequest);
        bundle.putBoolean("isAdmin",false);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void showInfoProductIfValid(List<OrderRequest> dataProductList) {
        if (dataProductList.isEmpty()) {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.rcvOrderCompleting.setVisibility(View.GONE);
        }else {
            binding.tvError.setVisibility(View.GONE);
            binding.rcvOrderCompleting.setVisibility(View.VISIBLE);
            orderAdapter.submitList(dataProductList);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        detailProductViewModel.clear();
    }
}