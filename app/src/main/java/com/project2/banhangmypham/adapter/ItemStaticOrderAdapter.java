package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemStaticOrderBinding;
import com.project2.banhangmypham.model.OrderRequestAdmin;
import com.project2.banhangmypham.model.StaticModel;
import com.project2.banhangmypham.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ItemStaticOrderAdapter extends RecyclerView.Adapter<ItemStaticOrderAdapter.StaticOrderViewHolder> {

   private List<OrderRequestAdmin> orderRequestAdminList = new ArrayList<>();

   @SuppressLint("NotifyDataSetChanged")
   public void submitList(List<OrderRequestAdmin> data){
       orderRequestAdminList.clear();
       orderRequestAdminList.addAll(data);
       notifyDataSetChanged();
   }

    @NonNull
    @Override
    public StaticOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       ItemStaticOrderBinding binding = ItemStaticOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new StaticOrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StaticOrderViewHolder holder, int position) {
        holder.setData(orderRequestAdminList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return orderRequestAdminList.size();
    }

    public static class StaticOrderViewHolder extends RecyclerView.ViewHolder {
        ItemStaticOrderBinding binding ;
        public StaticOrderViewHolder(@NonNull ItemStaticOrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(OrderRequestAdmin data, int position) {
            binding.tvIdOrder.setText(data.get_id());
            binding.tvCreateOrder.setText(data.getCreateDate());
            binding.tvTotalOrder.setText(Utils.convertToMoneyVN(data.getAmount()));
        }
    }
}
