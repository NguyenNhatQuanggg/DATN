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
import com.project2.banhangmypham.databinding.ItemProductBinding;
import com.project2.banhangmypham.databinding.ItemProductCartBinding;
import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.CartViewHolder> {

    private Context mContext;
    private final List<CartProductRequest> productsList = new ArrayList<>();
    private ICartProductEventListener callback ;
    public interface ICartProductEventListener {
       void onIncrease(int number, int position);
       void onDecrease(int number, int position);
       void onDelete(Product product, int position);
    }
    private boolean isShowControlLayout = true ;

    public void setShowControlLayout(boolean data) {
        isShowControlLayout = data ;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<CartProductRequest> data){
        productsList.clear();
        productsList.addAll(data);
        notifyDataSetChanged();
    }
    public ProductCartAdapter(Context mContext , ICartProductEventListener listener) {
        this.mContext = mContext;
        this.callback = listener ;
    }

    public long getTotalMoney() {
        long dataResult = 0;
        for (CartProductRequest data : productsList) {
            dataResult += (data.getProduct().getDiscount() == 0) ? data.getProduct().getPrice() * data.getNumberChoice() :
                    ((data.getProduct().getPrice() * data.getNumberChoice()) *(100 - data.getProduct().getDiscount())/100);
        }
        return dataResult;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductCartBinding binding = ItemProductCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProductRequest product = productsList.get(position);
        holder.setData(product, this.callback,mContext, position);
        holder.showLayout(isShowControlLayout);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ItemProductCartBinding binding;
        public CartViewHolder(ItemProductCartBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(CartProductRequest cartProductRequest, ICartProductEventListener iOnItemEvent, Context mContext, int position) {
            Glide.with(mContext).load(cartProductRequest.getProduct().getThumbnail()).into(binding.profileImage);
            binding.tvNameProduct.setText(cartProductRequest.getProduct().getName());
            binding.tvPriceProduct.setText(Utils.convertToMoneyVN(cartProductRequest.getProduct().getPrice()));
            binding.tvNumberProduct.setText(String.valueOf(cartProductRequest.getNumberChoice()));
            binding.ivDecrease.setOnClickListener(view -> {
                if (cartProductRequest.getNumberChoice() - 1 < 1) return;
                cartProductRequest.setNumberChoice(cartProductRequest.getNumberChoice() - 1);
                iOnItemEvent.onDecrease(cartProductRequest.getNumberChoice(), position);
            });
            binding.tvNumber.setText(String.valueOf(cartProductRequest.getNumberChoice()));
            binding.ivIncrease.setOnClickListener(view -> {
                cartProductRequest.setNumberChoice(cartProductRequest.getNumberChoice() + 1);
                iOnItemEvent.onIncrease(cartProductRequest.getNumberChoice(), position);
            });
            binding.remove.setOnClickListener(view -> iOnItemEvent.onDelete(cartProductRequest.getProduct(), position));
        }
        public void showLayout(boolean data){
            binding.llControlProduct.setVisibility(data ? View.VISIBLE : View.INVISIBLE);
            binding.remove.setVisibility(data ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
