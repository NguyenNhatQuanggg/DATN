package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemAddressTransferBinding;
import com.project2.banhangmypham.databinding.ItemProductUserBinding;
import com.project2.banhangmypham.model.AddressTransfer;

import java.util.ArrayList;
import java.util.List;

public class AddressTransferAdapter extends RecyclerView.Adapter<AddressTransferAdapter.AddressTransferViewHolder> {
    private final List<AddressTransfer> addressList = new ArrayList<>();
    private IAddressTransfer callback ;
    private int selectedIndex = 0;
    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<AddressTransfer> data) {
        addressList.clear();
        addressList.addAll(data);
        notifyDataSetChanged();
    }

    public AddressTransferAdapter(IAddressTransfer callback) {
        this.callback = callback;
    }

    public void setSelectedIndex(int index){
        this.selectedIndex = index ;
    }

    public interface IAddressTransfer {
        void onClickItem(int position);
    }

    @NonNull
    @Override
    public AddressTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddressTransferBinding binding = ItemAddressTransferBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressTransferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressTransferViewHolder holder, int position) {
        AddressTransfer addressTransfer = addressList.get(position);
        holder.setData(addressTransfer,position,callback, selectedIndex);
    }


    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressTransferViewHolder extends RecyclerView.ViewHolder {
        ItemAddressTransferBinding binding;

        public AddressTransferViewHolder(ItemAddressTransferBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setData(AddressTransfer addressTransfer, int position , IAddressTransfer callback, int selectedIndex) {
            binding.tvAddressReceiver.setText(addressTransfer.getAddressReceiver());
            binding.tvNameReceiver.setText(addressTransfer.getNameReceiver());
            binding.tvPhoneReceiver.setText(addressTransfer.getPhoneReceiver());
            binding.rbChoose.setOnClickListener(view ->{
                callback.onClickItem(position);
            });
            binding.rbChoose.setChecked(selectedIndex == position);
        }
    }
}
