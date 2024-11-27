package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.databinding.ItemCategoryBinding;
import com.project2.banhangmypham.model.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> mData = new ArrayList<>();
    IOnItemEvent callback ;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new MyViewHolder(binding);
    }
    public CategoryAdapter(IOnItemEvent callback){
        this.callback = callback ;
    }
    public interface IOnItemEvent {
        void onDelete(String id);
        void onEdit(Category category);
        void onClickItem(String id);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Category category = mData.get(position);
        holder.setData(category,callback);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Category> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemCategoryBinding binding;
        public MyViewHolder(ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(Category category, IOnItemEvent iOnItemEvent) {
            binding.tvName.setText(category.getName());
            binding.btnEdit.setOnClickListener(view -> iOnItemEvent.onEdit(category));
            binding.btnDelete.setOnClickListener(view -> iOnItemEvent.onDelete(category.getId()));
            binding.cardItem.setOnClickListener(view -> iOnItemEvent.onClickItem(category.getId()));
        }
    }
}
