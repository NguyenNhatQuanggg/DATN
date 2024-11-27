package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemProductHotSellerBinding;
import com.project2.banhangmypham.model.ProductHotSeller;
import com.project2.banhangmypham.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductHotSellerAdapter extends RecyclerView.Adapter<ProductHotSellerAdapter.ProductHotSellerViewHolder> {
    private final List<ProductHotSeller> productHotSellerList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<ProductHotSeller> data){
        productHotSellerList.clear();
        productHotSellerList.add(new ProductHotSeller());
        productHotSellerList.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductHotSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductHotSellerBinding binding = ItemProductHotSellerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductHotSellerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHotSellerViewHolder holder, int position) {
        holder.setData(productHotSellerList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return productHotSellerList.size();
    }

    public static class ProductHotSellerViewHolder extends RecyclerView.ViewHolder {
        ItemProductHotSellerBinding binding ;
        public ProductHotSellerViewHolder(@NonNull ItemProductHotSellerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(ProductHotSeller data, int position){
            if (position == 0) {
                binding.tvOrder.setText("STT");
                binding.tvNameProduct.setText("Ten san pham");
                binding.tvNumberProductSeller.setText("So luong");
                binding.tvTotalMoneySeller.setText("Tong tien");
            }else {
                binding.tvOrder.setText(""+position);
                binding.tvNameProduct.setText(data.getProduct().getName());
                binding.tvNumberProductSeller.setText(data.getNumberProductSeller()+" ");
                binding.tvTotalMoneySeller.setText(Utils.convertToMoneyVN(data.getTotalMoneyProductSeller()));
            }
        }
    }
}
