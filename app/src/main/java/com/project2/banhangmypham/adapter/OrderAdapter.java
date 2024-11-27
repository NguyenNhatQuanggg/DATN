package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project2.banhangmypham.databinding.ItemOrderBinding;
import com.project2.banhangmypham.databinding.ItemProductBinding;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private final List<OrderRequest> productsList = new ArrayList<>();
    private IOrderEventListener callback ;
    private boolean isCompleting = false;
    public interface IOrderEventListener {
        void onClickItemProduct(OrderRequest orderRequest, boolean isCompleting);
    }

    public void setIsCompletingOrder(boolean data){
        isCompleting = data ;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<OrderRequest> data){
        productsList.clear();
        productsList.addAll(data);
        notifyDataSetChanged();
    }
    public OrderAdapter(Context mContext , IOrderEventListener listener) {
        this.mContext = mContext;
        this.callback = listener ;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderRequest orderRequest = productsList.get(position);
        holder.setData(orderRequest,callback, mContext, isCompleting);
        if (position == productsList.size() - 1){
            holder.showLine(false);
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;
        public OrderViewHolder(ItemOrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(OrderRequest orderRequest, IOrderEventListener iOnItemEvent, Context mContext, boolean isCompleting) {
            binding.tvPriceOrder.setText(Utils.convertToMoneyVN(orderRequest.getDiscounted_amount()));
            binding.tvIdOrder.setText(orderRequest.get_id());

            List<String> nameProductsList = orderRequest.getItems().stream().map(it -> it.getProduct().getName()).collect(Collectors.toList());
            binding.tvNameProductOrder.setText(String.join(",", nameProductsList));
            binding.tvNumberProductOrder.setText("("+ nameProductsList.size()+") sản phẩm");
            binding.btnMonitorOrder.setOnClickListener(view -> {
                iOnItemEvent.onClickItemProduct(orderRequest, isCompleting);
            });
            if (isCompleting){
                binding.llComments.setVisibility(View.GONE);
                binding.llSuccessStatus.setVisibility(View.VISIBLE);
                binding.tvMonitor.setText("Hóa đơn");
            }else {
                binding.llComments.setVisibility(View.GONE);
                binding.llSuccessStatus.setVisibility(View.INVISIBLE);
                binding.tvMonitor.setText("Theo dõi đơn hàng");
            }
        }
        public void showLine(boolean data){
            binding.line.setVisibility((data) ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
