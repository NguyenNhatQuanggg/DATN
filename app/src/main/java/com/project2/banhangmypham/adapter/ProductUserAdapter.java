package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.project2.banhangmypham.databinding.ItemProductUserBinding;
import com.project2.banhangmypham.model.Product;
import com.project2.banhangmypham.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductUserAdapter extends RecyclerView.Adapter<ProductUserAdapter.ProductUserViewHolder> {

    private Context mContext;
    private final List<Product> productsList = new ArrayList<>();
    private IProductEvenListener callback;

    public interface IProductEvenListener {
        void onClickItemProduct(Product product);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Product> data) {
        productsList.clear();
        productsList.addAll(data);
        notifyDataSetChanged();
    }

    public ProductUserAdapter(Context mContext, IProductEvenListener listener) {
        this.mContext = mContext;
        this.callback = listener;
    }


    @NonNull
    @Override
    public ProductUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductUserBinding binding = ItemProductUserBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ProductUserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductUserViewHolder holder, int position) {
        Product product = productsList.get(position);
        holder.setData(product, this.callback, mContext);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class ProductUserViewHolder extends RecyclerView.ViewHolder {
        ItemProductUserBinding binding;

        public ProductUserViewHolder(ItemProductUserBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(Product product, IProductEvenListener iOnItemEvent, Context mContext) {
            Glide.with(mContext).load(product.getThumbnail()).into(binding.ivProduct);
            binding.tvNameProduct.setText(product.getName());
            binding.tvDescriptionProduct.setText(product.getDescription());
            long dataMoney =product.getPrice() - Math.round((float) (product.getPrice() * product.getDiscount()) / 100);

            if (product.getDiscount() == 0) {
                binding.tvPriceProduct.setVisibility(View.GONE);
                String inputMoney = Utils.convertToMoneyVN(product.getPrice());
                binding.tvDiscountPriceProduct.setText(inputMoney);
            }else {
                String input = Utils.convertToMoneyVN(product.getPrice());
                Spannable spannable = Utils.convertColorText(input,0,input.length());
                binding.tvPriceProduct.setText(spannable);
                binding.tvDiscountPriceProduct.setVisibility(View.VISIBLE);
                String inputMoney = Utils.convertToMoneyVN(dataMoney);
                binding.tvDiscountPriceProduct.setText(inputMoney);
            }
            binding.cardItemProduct.setOnClickListener(view -> iOnItemEvent.onClickItemProduct(product));
            binding.tvNumberStar.setText(product.getOverAllScore()+"");
            binding.llRating.bringToFront();
        }
    }
}
