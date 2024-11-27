package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemCategoryBinding;
import com.project2.banhangmypham.databinding.ItemCategoryUserBinding;
import com.project2.banhangmypham.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryUserAdapter extends RecyclerView.Adapter<CategoryUserAdapter.CategoryUserViewHolder> {
    private List<Category> mData = new ArrayList<>();
    IOnItemEvent callback ;

    private int selectedIndex = 0;
    @Override
    public CategoryUserAdapter.CategoryUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCategoryUserBinding binding = ItemCategoryUserBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new CategoryUserViewHolder(binding);
    }

    public CategoryUserAdapter(IOnItemEvent callback){
        this.callback = callback ;
    }
    public interface IOnItemEvent {
        void onClickItem(String id, int position);
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final CategoryUserAdapter.CategoryUserViewHolder holder, final int position) {
        Category category = mData.get(position);
        holder.setData(category,position,callback);
        if (selectedIndex == position){
            holder.itemView.setSelected(true);
        }else {
            holder.itemView.setSelected(false);
        }
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

    public static class CategoryUserViewHolder extends RecyclerView.ViewHolder {

        ItemCategoryUserBinding binding;
        public CategoryUserViewHolder(ItemCategoryUserBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(Category category,int position, IOnItemEvent iOnItemEvent) {
            binding.tvName.setText(category.getName());
            binding.cardItem.setOnClickListener(view -> {
                iOnItemEvent.onClickItem(category.getId(), position);
            });
        }
    }
}
