package com.project2.banhangmypham.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.databinding.ActivityEvaluationProductBinding;
import com.project2.banhangmypham.model.EvaluationRequestModel;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.repository.evaluation.EvaluationRepositoryImpl;
import com.project2.banhangmypham.viewmodel.evaluation.EvaluationViewModel;

public class EvaluationProductActivity extends AppCompatActivity {

    public static final String TAG = "EvaluationProductActivity";
    ActivityEvaluationProductBinding binding ;
    EvaluationViewModel evaluationViewModel ;
    Product productCurrent ;
    String userId = "";
    float score = 5f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEvaluationProductBinding.inflate(getLayoutInflater());
        evaluationViewModel = new ViewModelProvider(this).get(EvaluationViewModel.class);
        evaluationViewModel.setiEvaluationRepository(new EvaluationRepositoryImpl());
        setContentView(binding.getRoot());
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
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Glide.with(this).load("https://www.perfecto.io/sites/default/files/styles/social_preview_image/public/image/2019-06/image-blog-resolution-and-ppi-affect-test-coverage-600x400.jpg?itok=le3D8KFn").into(binding.ivEvaluation);
        binding.ratingbarProduct.setNumStars(5);
        binding.ratingbarProduct.setRating(5);
        binding.ivBack.setOnClickListener(view -> finish());
        binding.ratingbarProduct.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = rating;
            }
        });
        evaluationViewModel.getEvaluationProductLiveData().observe(this, result ->{
            if (result != null) {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnSendEvaluation.setOnClickListener(view ->{
            EvaluationRequestModel evaluationRequestModel = new
                    EvaluationRequestModel(productCurrent,
                    score,binding.edtMessage.getText().toString().trim(), userId);

            evaluationViewModel.addEvaluationRequestModel(evaluationRequestModel);
        });
    }


}