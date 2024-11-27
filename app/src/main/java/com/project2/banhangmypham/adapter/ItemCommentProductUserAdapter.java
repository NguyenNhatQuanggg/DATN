package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemCommentProductUserBinding;
import com.project2.banhangmypham.model.EvaluationRequestModel;
import com.project2.banhangmypham.model.EvaluationResponse;
import com.project2.banhangmypham.model.EvaluationResponseAdmin;

import java.util.ArrayList;
import java.util.List;

public class ItemCommentProductUserAdapter extends RecyclerView.Adapter<ItemCommentProductUserAdapter.CommentProductUserViewHolder> {
    public static final String TAG = "CommentAdminAdapter";
    private final List<EvaluationRequestModel> evaluationRequestModelList = new ArrayList<>();
    public ItemCommentProductUserAdapter() {
    }

    @NonNull
    @Override
    public CommentProductUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentProductUserBinding binding = ItemCommentProductUserBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CommentProductUserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentProductUserViewHolder holder, int position) {
        holder.setData(evaluationRequestModelList.get(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<EvaluationRequestModel> data){
        evaluationRequestModelList.clear();
        evaluationRequestModelList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return evaluationRequestModelList.size();
    }


    public static class CommentProductUserViewHolder extends RecyclerView.ViewHolder {
        ItemCommentProductUserBinding binding;
        public CommentProductUserViewHolder(ItemCommentProductUserBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(EvaluationRequestModel data) {
            binding.tvRating.setText(data.getRating()+"");
            binding.tvNameUser.setText(data.getUser().getUsername());
            binding.tvMessage.setText(data.getMessage());
            binding.tvTimeComment.setText(data.getCreatedDate());
        }
    }
}
