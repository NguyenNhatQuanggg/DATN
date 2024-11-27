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
import com.project2.banhangmypham.adapter.CategoryAdapter;
import com.project2.banhangmypham.admin.category_activity.AddAndEditCategoryActivity;
import com.project2.banhangmypham.admin.repository.CategoryRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.CategoryViewModel;
import com.project2.banhangmypham.databinding.FragmentCategoryBinding;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.repository.home.HomeRepositoryImpl;
import com.project2.banhangmypham.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements CategoryAdapter.IOnItemEvent {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentCategoryBinding binding ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private CategoryViewModel categoryViewModel ;
    private CategoryAdapter categoryAdapter ;
    private List<Category> categoryList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(getLayoutInflater());

        categoryAdapter = new CategoryAdapter(this);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.setICategoryRepository(new CategoryRepositoryImpl());

        categoryViewModel.getAllCategoryList();
        categoryViewModel.getCategoryListLiveData().observe((LifecycleOwner) requireContext(), result ->{
            if (result != null && result.getData() != null && !result.getData().isEmpty()){
                categoryList.clear();
                categoryList.addAll(result.getData());
                categoryAdapter.submitList(result.getData());
            }
        });

        categoryViewModel.getDeleteCategoryLiveData().observe((LifecycleOwner) requireContext(), result ->{
            if (result != null && !result.getMessage().isEmpty() ){
                Toast.makeText(requireContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                categoryAdapter.submitList(categoryList);
            }
        });

        binding.btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), AddAndEditCategoryActivity.class);
            intent.putExtra("isAdd", true);
            intent.putExtra("title", "Thêm mới thể loại");
            startActivity(intent);
        });
        binding.rcvCategory.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false));
        binding.rcvCategory.setAdapter(categoryAdapter);
        binding.rcvCategory.hasFixedSize();
        return binding.getRoot();
    }

    @Override
    public void onDelete(String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            categoryList.removeIf(it -> it.getId().equals(id));
        }
        categoryViewModel.deleteCategory(id);
    }

    @Override
    public void onEdit(Category category) {
        Intent intent = new Intent(requireActivity(), AddAndEditCategoryActivity.class);
        intent.putExtra("isAdd", false);
        intent.putExtra("title", "Cập nhật thể loại");
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", category);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClickItem(String id) {

    }

    @Override
    public void onResume() {
        super.onResume();
        categoryViewModel.getAllCategoryList();
    }
}