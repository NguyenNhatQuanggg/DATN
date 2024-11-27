package com.project2.banhangmypham.admin.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.ProductAdminAdapter;
import com.project2.banhangmypham.admin.product_activity.AddProductActivity;
import com.project2.banhangmypham.admin.repository.ProductRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.ProductViewModel;
import com.project2.banhangmypham.databinding.FragmentProductBinding;
import com.project2.banhangmypham.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment implements ProductAdminAdapter.IProductEvenListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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

    FragmentProductBinding binding ;
    ProductAdminAdapter productAdminAdapter ;
    List<Product> productsList = new ArrayList<>();
    ProductViewModel productViewModel ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(getLayoutInflater());
        productAdminAdapter = new ProductAdminAdapter(requireContext(), this);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.setICategoryRepository(new ProductRepositoryImpl());
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rcvProduct.setAdapter(productAdminAdapter);
        binding.rcvProduct.hasFixedSize();

        productViewModel.getProductListLiveData().observe((LifecycleOwner) requireContext(), result ->{
            if (result != null && result.getData() != null && !result.getData().isEmpty()) {
                productsList.clear();
                productsList.addAll(result.getData());
                productAdminAdapter.submitList(result.getData());
            }
        });

        productViewModel.getDeleteCategoryLiveData().observe((LifecycleOwner) requireContext(), result ->{
            if (result != null && result.getCode() == 200 ){
                Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                productAdminAdapter.submitList(productsList);
            }
        });

        binding.btnAdd.setOnClickListener(view ->{
            Intent intent = new Intent(requireActivity(), AddProductActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    @Override
    public void onEdit(Product product) {
        Intent intent = new Intent(requireActivity(), AddProductActivity.class);
        intent.putExtra("isAdd" , false);
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDelete(Product product) {
        productViewModel.deleteProduct(product.get_id());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            productsList.removeIf(it -> it.get_id().equals(product.get_id()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        productViewModel.getAllProductsList();
    }

    @Override
    public void onClickItemProduct(Product product) {

    }
}