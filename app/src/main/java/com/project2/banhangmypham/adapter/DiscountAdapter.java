package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemDiscountProductBinding;
import com.project2.banhangmypham.model.DiscountModel;
import com.project2.banhangmypham.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> {

    private Context mContext;
    private final List<DiscountModel> discountModelList = new ArrayList<>();
    private IDiscountEvenListener callback;
    private long totalMoney = 0;
    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDiscountProductBinding binding = ItemDiscountProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DiscountViewHolder(binding);
    }

    public DiscountAdapter(IDiscountEvenListener callback, Context mContext, long totalMoney) {
        this.callback = callback;
        this.mContext = mContext;
        this.totalMoney = totalMoney;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<DiscountModel> data){
        discountModelList.clear();
        discountModelList.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        DiscountModel discountModel = discountModelList.get(position);
        holder.setData(discountModel,callback,totalMoney);
    }

    @Override
    public int getItemCount() {
        return discountModelList.size();
    }

    public interface IDiscountEvenListener {
        void onClickItemDiscount(DiscountModel discountModel);
    }

    public static class DiscountViewHolder extends RecyclerView.ViewHolder {
        ItemDiscountProductBinding binding;

        public DiscountViewHolder(ItemDiscountProductBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(DiscountModel discountModel, IDiscountEvenListener iOnItemEvent, Long total) {
            binding.titleDiscount.setText(discountModel.getTitleDiscount());
            binding.subTitleDiscount.setText(discountModel.getSubTitleDiscount());

            if (discountModel.isValid()){
                binding.titleDiscount.setTextColor(Color.BLACK);
                binding.subTitleDiscount.setTextColor(Color.BLACK);
            }else {
                binding.titleDiscount.setTextColor(Color.BLACK);
                binding.subTitleDiscount.setTextColor(Color.BLACK);
            }
            binding.cardItemDiscount.setOnClickListener(view -> iOnItemEvent.onClickItemDiscount(discountModel));
        }
    }
}

