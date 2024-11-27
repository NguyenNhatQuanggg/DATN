package com.project2.banhangmypham;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project2.banhangmypham.user.CartProductActivity;
import com.project2.banhangmypham.user.DetailProductActivity;
import com.project2.banhangmypham.user.SearchActivity;
import com.project2.banhangmypham.adapter.CategoryUserAdapter;
import com.project2.banhangmypham.adapter.ProductUserAdapter;
import com.project2.banhangmypham.animation.ViewPagerAdapter;
import com.project2.banhangmypham.databinding.FragmentHomeBinding;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.repository.home.HomeRepositoryImpl;
import com.project2.banhangmypham.viewmodel.HomeViewModel;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements View.OnClickListener,
        ProductUserAdapter.IProductEvenListener, CategoryUserAdapter.IOnItemEvent {
    List<Category> categoryList = new ArrayList<>();
    List<Product> productsList = new ArrayList<>();
    Intent intent;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    FragmentHomeBinding binding;
    ProductUserAdapter productUserAdapter;
    CategoryUserAdapter categoryUserAdapter;
    private HomeViewModel homeViewModel;
    private AccountViewModel accountViewModel;
    private final HashMap<String,List<Product>> productMap = new HashMap<>();
    private Category categoryCurrent ;
    public static final String TAG = "HomeFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        homeViewModel.setiHomeRepository(new HomeRepositoryImpl());

        productUserAdapter = new ProductUserAdapter(requireContext(), this);
        categoryUserAdapter = new CategoryUserAdapter(this);

        homeViewModel.getDataHome();
        homeViewModel.getHomeResponseLiveData().observe(requireActivity(), result -> {
            if (result != null) {
                Toast.makeText(requireContext(), "result = " + result.getProductFamousList().size(), Toast.LENGTH_LONG).show();
                productsList.addAll(result.getProductsList());
                categoryList.addAll(result.getCategoryList());
                categoryUserAdapter.submitList(result.getCategoryList());
                List<Product> dataProductList = result.getProductMap().getOrDefault(categoryList.get(0).getId(), new ArrayList<>());
                assert dataProductList != null;
                showInfoProductIfValid(dataProductList);
                productMap.putAll(result.getProductMap());
                categoryCurrent = categoryList.get(0);
            }
        });
        binding.homeSearch.setOnClickListener(this);
        setViewPager();
        loadData();
        setLoadMoreAction();

        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.rcvProduct.hasFixedSize();
        binding.rcvProduct.setAdapter(productUserAdapter);

        binding.btnFilterAll.setSelected(true);

        binding.rcvCategory.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.rcvCategory.hasFixedSize();
        binding.rcvCategory.setAdapter(categoryUserAdapter);


        binding.btnFilterAll.setOnClickListener(view -> {
            binding.btnFilterAll.setSelected(true);
            binding.btnNewAll.setSelected(false);
            binding.btnDiscountAll.setSelected(false);
            binding.btnFamousAll.setSelected(false);
            List<Product> dataProductList = productsList.stream().filter(it -> it.getCategory().getId().equals(categoryCurrent.getId())).collect(Collectors.toList());
            showInfoProductIfValid(dataProductList);

        });
        binding.btnNewAll.setOnClickListener(view -> {
            binding.btnNewAll.setSelected(true);
            binding.btnFilterAll.setSelected(false);
            binding.btnDiscountAll.setSelected(false);
            binding.btnFamousAll.setSelected(false);
            List<Product> dataProductList = productsList.stream()
                    .filter(it -> it.getCategory().getId().equals(categoryCurrent.getId()))
                    .filter(Product::isNew).collect(Collectors.toList());
            showInfoProductIfValid(dataProductList);
        });
        binding.btnDiscountAll.setOnClickListener(view -> {
            binding.btnDiscountAll.setSelected(true);
            binding.btnFilterAll.setSelected(false);
            binding.btnNewAll.setSelected(false);
            binding.btnFamousAll.setSelected(false);

            List<Product> dataProductList = productsList.stream()
                    .filter(it -> it.getCategory().getId().equals(categoryCurrent.getId()))
                    .filter(it -> it.getDiscount() > 0).collect(Collectors.toList());
            showInfoProductIfValid(dataProductList);
        });
        binding.btnFamousAll.setOnClickListener(view -> {
            binding.btnFamousAll.setSelected(true);
            binding.btnFilterAll.setSelected(false);
            binding.btnNewAll.setSelected(false);
            binding.btnDiscountAll.setSelected(false);
            List<Product> dataProductList = productsList.stream()
                    .filter(it -> it.getCategory().getId().equals(categoryCurrent.getId()))
                    .filter(Product::isFamous).collect(Collectors.toList());
            showInfoProductIfValid(dataProductList);
        });

        binding.btnCart.setOnClickListener(view ->{
            Intent intent = new Intent(requireActivity(), CartProductActivity.class);
            intent.putExtra("userId", Objects.requireNonNull(accountViewModel.getUserInfoLiveData().getValue()).get_id());
            startActivity(intent);
        });
        return binding.getRoot();
    }

    private void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        binding.viewPager.setAdapter(viewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4) {
                    currentPage = 0;
                }
                binding.viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void loadData() {


    }

    private void setLoadMoreAction() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.home_search) {
            intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClickItemProduct(Product product) {
        Intent intent = new Intent(requireActivity(), DetailProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        bundle.putString("userId", Objects.requireNonNull(accountViewModel.getUserInfoLiveData().getValue()).get_id());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClickItem(String id, int position) {
        categoryUserAdapter.setSelectedIndex(position);
        categoryUserAdapter.notifyDataSetChanged();
        List<Product> tmpProductList = Objects.requireNonNull(productMap.getOrDefault(id, new ArrayList<>()));
        Log.d(TAG, "onClickItem: ====> product = "+ tmpProductList);
        if (binding.btnDiscountAll.isSelected()){
            List<Product> dataProductList = tmpProductList.stream().filter(it -> !(it.getDiscount() <= 0)).collect(Collectors.toList());
            showInfoProductIfValid(dataProductList);
        }else if (binding.btnNewAll.isSelected()) {
            List<Product> dataProductList = tmpProductList.stream().filter(it -> !it.isNew()).collect(Collectors.toList());
            showInfoProductIfValid(dataProductList);
        }else if (binding.btnFamousAll.isSelected()) {
            List<Product> dataProductList = tmpProductList.stream().filter(it -> !it.isFamous()).collect(Collectors.toList());
            showInfoProductIfValid(dataProductList);

        }else {
            showInfoProductIfValid(tmpProductList);
        }
        categoryCurrent = categoryList.get(position);
    }

    private void showInfoProductIfValid(List<Product> dataProductList) {
        if (dataProductList.isEmpty()) {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.rcvProduct.setVisibility(View.GONE);
        }else {
            binding.tvError.setVisibility(View.GONE);
            binding.rcvProduct.setVisibility(View.VISIBLE);
            productUserAdapter.submitList(dataProductList);
        }
    }

    @Override
    public void onStop() {
        homeViewModel.clear();
        super.onStop();
    }
}
