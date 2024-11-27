package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemCommentsBinding;
import com.project2.banhangmypham.model.EvaluationResponseAdmin;

import java.util.ArrayList;
import java.util.List;

public class CommentAdminAdapter extends RecyclerView.Adapter<CommentAdminAdapter.CommentViewHolder> {
    public static final String TAG = "CommentAdminAdapter";
    private Context mContext;
    private final List<EvaluationResponseAdmin> evaluationResponseAdminList = new ArrayList<>();
    private ICommentEvenListener callback;
    private long totalMoney = 0;

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentsBinding binding = ItemCommentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        EvaluationResponseAdmin evaluationResponseAdmin = evaluationResponseAdminList.get(position);
        holder.setData(evaluationResponseAdmin, callback, position);
    }

    public CommentAdminAdapter(ICommentEvenListener callback, Context mContext) {
        this.callback = callback;
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<EvaluationResponseAdmin> data) {
        evaluationResponseAdminList.clear();
        evaluationResponseAdminList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return evaluationResponseAdminList.size();
    }

    public interface ICommentEvenListener {
        void onAccept(EvaluationResponseAdmin data);

        void onReject(EvaluationResponseAdmin data);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemCommentsBinding binding;

        public CommentViewHolder(ItemCommentsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(EvaluationResponseAdmin data, ICommentEvenListener iOnItemEvent, int position) {
            binding.tvCommentName.setText(data.getUser().getUsername());
            binding.tvContentMessage.setText(data.getMessage());
            if (data.getStatus() == 0) {
                binding.rlControlComment.setVisibility(View.VISIBLE);
            } else {
                binding.rlControlComment.setVisibility(View.GONE);
            }
            binding.btnAccept.setOnClickListener(view -> {
                Log.d(TAG, "setData: ====>");
                iOnItemEvent.onAccept(data);
            });

            binding.btnReject.setOnClickListener(view -> {
                iOnItemEvent.onReject(data);
            });
        }
    }
}
