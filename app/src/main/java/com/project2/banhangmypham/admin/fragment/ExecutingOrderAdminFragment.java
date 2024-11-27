package com.project2.banhangmypham.admin.fragment;

import android.content.Intent;
import android.os.Build;
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
import com.project2.banhangmypham.databinding.FragmentExecutingOrderAdminBinding;
import com.project2.banhangmypham.databinding.FragmentExecutingOrderBinding;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderRequestAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExecutingOrderAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExecutingOrderAdminFragment extends Fragment implements OrderAdminAdapter.IOrderEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExecutingOrderAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExecutingOrderAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExecutingOrderAdminFragment newInstance(String param1, String param2) {
        ExecutingOrderAdminFragment fragment = new ExecutingOrderAdminFragment();
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
    FragmentExecutingOrderAdminBinding binding ;
    OrderAdminAdapter orderAdapter;
    OrderViewModel orderViewModel ;
    public static final String TAG = "ExecutingOrderAdminFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExecutingOrderAdminBinding.inflate(getLayoutInflater());
        orderAdapter = new OrderAdminAdapter(requireContext(), this);
        orderAdapter.setIsCompletingOrder(false);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        orderViewModel.getOrderExecutingResponseLiveData().observe((LifecycleOwner) requireContext(), result ->{
            Log.d(TAG, "onCreateView: ===> result = " + result);
            orderAdapter.submitList(result);
        });
        binding.rcvOrderExecuting.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.rcvOrderExecuting.hasFixedSize();
        binding.rcvOrderExecuting.setAdapter(orderAdapter);
        return binding.getRoot();
    }

    @Override
    public void onClickItemProduct(OrderRequestAdmin orderRequest,boolean isCompleting) {
       ArrayList<CartProductRequest> data =  new ArrayList<>(orderRequest.getItems());
        Log.d(TAG, "onClickItemProduct: ===> data = " + data);

        Intent intent = new Intent(requireActivity(), MonitorOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("order", orderRequest);
        bundle.putBoolean("isAdmin", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}