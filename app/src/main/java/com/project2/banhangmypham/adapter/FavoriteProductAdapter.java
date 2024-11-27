package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.databinding.CardviewGiohangBinding;
import com.project2.banhangmypham.model.Product;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProductAdapter extends RecyclerView.Adapter<FavoriteProductAdapter.FavoriteViewHolder> {
    private Context mContext ;
    private List<Product> mData = new ArrayList<>();
    private IFavoriteProduct callback ;
    public interface IFavoriteProduct {
        void onClickItemFavoriteProduct(Product product);
    }
    public FavoriteProductAdapter(Context mContext, IFavoriteProduct data) {
        this.mContext = mContext;
        callback = data;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Product> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Product product = mData.get(position);
        holder.setData(product,mContext,callback);
    }


    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewGiohangBinding binding = CardviewGiohangBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FavoriteViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Product item, int position) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        CardviewGiohangBinding binding ;
        public FavoriteViewHolder(CardviewGiohangBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(Product product, Context context, IFavoriteProduct callback){
            binding.giohangTen.setText(product.getName());
            binding.giohangNhasx.setText("Loai: "+product.getCategory().getName());
            binding.giohangGia.setText(String.valueOf(product.getPrice()));
            Glide.with(context).load(product.getThumbnail()).placeholder(R.drawable.noimage).into(binding.imageViewGiohang);

            binding.cardGiohang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   callback.onClickItemFavoriteProduct(product);
                }
            });
        }
    }
}
