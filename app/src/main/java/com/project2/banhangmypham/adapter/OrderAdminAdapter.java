package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemOrderAdminBinding;
import com.project2.banhangmypham.databinding.ItemOrderBinding;
import com.project2.banhangmypham.model.OrderRequest;
import com.project2.banhangmypham.model.OrderRequestAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderAdminAdapter  extends RecyclerView.Adapter<OrderAdminAdapter.OrderAdminViewHolder> {

    private Context mContext;
    private final List<OrderRequestAdmin> productsList = new ArrayList<>();
    private IOrderEventListener callback ;
    private boolean isCompleting = false;
    public void setIsCompletingOrder(boolean data){
        isCompleting = data ;
    }

    public interface IOrderEventListener {
        void onClickItemProduct(OrderRequestAdmin orderRequest, boolean isCompleting);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<OrderRequestAdmin> data){
        productsList.clear();
        productsList.addAll(data);
        notifyDataSetChanged();
    }
    public OrderAdminAdapter(Context mContext , IOrderEventListener listener) {
        this.mContext = mContext;
        this.callback = listener ;
    }


    @NonNull
    @Override
    public OrderAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderAdminBinding binding = ItemOrderAdminBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OrderAdminViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdminViewHolder holder, int position) {
        OrderRequestAdmin orderRequest = productsList.get(position);
        holder.setData(orderRequest,callback, mContext, isCompleting);
    }


    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: ====> sizer = "+productsList.size() );
        return productsList.size();
    }

    public static class OrderAdminViewHolder extends RecyclerView.ViewHolder {
        ItemOrderAdminBinding binding;
        public OrderAdminViewHolder(ItemOrderAdminBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(OrderRequestAdmin orderRequest, IOrderEventListener iOnItemEvent, Context mContext, boolean isCompleting) {
            binding.tvPriceOrder.setText(String.valueOf(orderRequest.getAmount()));
            binding.tvIdOrder.setText(orderRequest.get_id());
            binding.tvNameUser.setText(orderRequest.getUser().getUsername());
            List<String> nameProductsList = orderRequest.getItems().stream().map(it -> it.getProduct().getName()).collect(Collectors.toList());
            binding.tvNameProductOrder.setText(String.join(",", nameProductsList));
            binding.tvNumberProductOrder.setText("("+ nameProductsList.size()+") sản phẩm");
            binding.btnMonitorOrder.setOnClickListener(view -> {
                iOnItemEvent.onClickItemProduct(orderRequest, isCompleting);
            });
            if (isCompleting){
                binding.llSuccessStatus.setVisibility(View.VISIBLE);
                binding.tvMonitor.setText("Hóa đơn");
            }else {
                binding.llSuccessStatus.setVisibility(View.INVISIBLE);
                binding.tvMonitor.setText("Theo dõi đơn hàng");
            }
        }
    }
}
