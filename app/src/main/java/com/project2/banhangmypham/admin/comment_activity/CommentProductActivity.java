package com.project2.banhangmypham.admin.comment_activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.adapter.CommentAdminAdapter;
import com.project2.banhangmypham.admin.repository.CommentRepositoryImpl;
import com.project2.banhangmypham.admin.viewmodel.CommentViewModel;
import com.project2.banhangmypham.databinding.ActivityCommentProductBinding;
import com.project2.banhangmypham.model.EvaluationResponseAdmin;

public class CommentProductActivity extends AppCompatActivity implements CommentAdminAdapter.ICommentEvenListener {
    public static final int ACCEPT = 1 ;
    public static final int REJECT = 2 ;
    ActivityCommentProductBinding binding ;
    CommentAdminAdapter commentAdminAdapter ;
    CommentViewModel commentViewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentProductBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        commentAdminAdapter = new CommentAdminAdapter(this, this);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        commentViewModel.setICommentRepository(new CommentRepositoryImpl());
        setContentView(binding.getRoot());
        commentViewModel.getAllCommentsList();
        commentViewModel.getCommentListLiveData().observe(this, result -> {
            if (result != null) {
                commentAdminAdapter.submitList(result.getEvaluationResponseList());
            }
        });
        commentViewModel.getStateCommentLiveData().observe(this , result ->{
            if (result != null) {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.rcvComments.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvComments.hasFixedSize();
        binding.rcvComments.setAdapter(commentAdminAdapter);

    }

    @Override
    public void onAccept(EvaluationResponseAdmin data) {
        commentViewModel.updateStateComment(data.get_id(), ACCEPT);
    }

    @Override
    public void onReject(EvaluationResponseAdmin data) {
        commentViewModel.updateStateComment(data.get_id(), REJECT);

    }
}