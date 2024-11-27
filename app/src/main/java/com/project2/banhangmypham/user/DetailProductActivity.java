package com.project2.banhangmypham.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.CommentAdminAdapter;
import com.project2.banhangmypham.adapter.ItemCommentProductUserAdapter;
import com.project2.banhangmypham.databinding.ActivityDetailProductBinding;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.FavoriteRequest;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.repository.detail_product.DetailProductRepositoryImpl;
import com.project2.banhangmypham.repository.evaluation.EvaluationRepositoryImpl;
import com.project2.banhangmypham.utils.Utils;
import com.project2.banhangmypham.viewmodel.evaluation.EvaluationViewModel;
import com.project2.banhangmypham.viewmodel.product.DetailProductViewModel;

import java.util.Objects;

public class DetailProductActivity extends AppCompatActivity {
    ActivityDetailProductBinding binding ;
    public static final String TAG = "DetailProductActivity";
    private Product productCurrent ;
    private int numberProductChoice = 1;
    private String userId = "";
    private DetailProductViewModel detailProductViewModel ;
    private EvaluationViewModel evaluationViewModel ;
    private ItemCommentProductUserAdapter commentProductUserAdapter;
    private boolean isFavorite = false ;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        commentProductUserAdapter = new ItemCommentProductUserAdapter();
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);
        evaluationViewModel = new ViewModelProvider(this).get(EvaluationViewModel.class);
        detailProductViewModel.setIDetailProductRepository(new DetailProductRepositoryImpl());
        evaluationViewModel.setiEvaluationRepository(new EvaluationRepositoryImpl());
        binding.rcvComments.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.rcvComments.hasFixedSize();
        binding.rcvComments.setAdapter(commentProductUserAdapter);


        detailProductViewModel.getOneProductFavoriteLiveData().observe(this, result ->{
            if (result != null && result.getCode() == 200) {
                Toast.makeText(this, "favorite = " + result.isData(), Toast.LENGTH_SHORT).show();
                isFavorite = result.isData();
                binding.btnFavorite.setImageResource(result.isData() ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
            }
        });
        detailProductViewModel.getDetailProductLiveData().observe(this, result -> {
            if (result != null && result.getCode() == 200){
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailProductActivity.this, CartProductActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });

        evaluationViewModel.getAllEvaluationLiveData().observe(this, result -> {
            if (result != null) {
                commentProductUserAdapter.submitList(result.getEvaluationRequestModelList());
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                productCurrent = bundle.getParcelable("product", Product.class);
            }else {
                productCurrent = bundle.getParcelable("product");
            }
            userId = bundle.getString("userId");
        }
        Log.d(TAG, "onCreate: ===> productCurrent =  "+productCurrent);
        Log.d(TAG, "onCreate: ===> userId =  "+userId);
        if (productCurrent != null ) {
            binding.nameProduct.setText(productCurrent.getName());
            Glide.with(this).load(productCurrent.getThumbnail()).into(binding.ivProduct);
            binding.tvNameProduct.setText(productCurrent.getName());
            binding.tvPriceProduct.setText(Utils.convertToMoneyVN(productCurrent.getPrice()));
            binding.tvDescription.setText(productCurrent.getDescription());
            binding.tvContentDescriptionProduct.setText(productCurrent.getDescription());
            detailProductViewModel.getFavoriteById(productCurrent.get_id(), userId);
            binding.tvNumberStar.setText(productCurrent.getOverAllScore() +"");
        }
        binding.ivBack.setOnClickListener(view -> finish());
        binding.btnEvaluation.setOnClickListener(view -> {
            Intent intentEvaluation = new Intent(this, EvaluationProductActivity.class);
            Bundle bundleEvaluation = new Bundle();
            bundleEvaluation.putParcelable("product", productCurrent);
            bundleEvaluation.putString("userId", userId);
            intentEvaluation.putExtras(bundleEvaluation);
            startActivity(intentEvaluation);
        });

        binding.ivIncrease.setOnClickListener(view -> {
            numberProductChoice++;
            binding.tvNumber.setText(String.valueOf(numberProductChoice));

           long totalMoney =
                   (productCurrent.getDiscount() == 0) ? (long) numberProductChoice * productCurrent.getPrice() :
                           (long) ((numberProductChoice * productCurrent.getPrice()) * (100 - productCurrent.getDiscount()))/100;
           binding.tvTotalMoney.setText(Utils.convertToMoneyVN(totalMoney));
        });

        binding.ivDecrease.setOnClickListener(view ->{
            if (numberProductChoice > 1) numberProductChoice--;
            binding.tvNumber.setText(String.valueOf(numberProductChoice));
            long totalMoney = (productCurrent.getDiscount() == 0) ? (long) numberProductChoice * productCurrent.getPrice() :
                    (long) ((numberProductChoice * productCurrent.getPrice()) * (100 - productCurrent.getDiscount()))/100;
            binding.tvTotalMoney.setText(Utils.convertToMoneyVN(totalMoney));
        });

        binding.btnAddCart.setOnClickListener(view ->{
            CartProductRequest cartProductRequest = new CartProductRequest(
                    productCurrent,
                    productCurrent.getCategory(),
                    userId,
                    numberProductChoice
            );
            detailProductViewModel.addCartProduct(cartProductRequest);
        });

        binding.tvTotalMoney.setText(Utils.convertToMoneyVN(productCurrent.getPrice()));
        binding.btnFavorite.setOnClickListener(view ->{
            if (!isFavorite) {
                binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_24);
            }else {
                binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
            }
            isFavorite = !isFavorite ;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        evaluationViewModel.getAllEvaluationRequestModelById(productCurrent.get_id());

    }

    @Override
    protected void onPause() {
        FavoriteRequest favoriteRequest = new FavoriteRequest(productCurrent.get_id(),userId, isFavorite);
        detailProductViewModel.updateFavoriteProduct(favoriteRequest);
        super.onPause();
    }
}