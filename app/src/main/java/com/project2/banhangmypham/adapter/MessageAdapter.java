package com.project2.banhangmypham.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.banhangmypham.databinding.ItemMessageLeftBinding;
import com.project2.banhangmypham.databinding.ItemMessageSenderBinding;
import com.project2.banhangmypham.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Message> messageList = new ArrayList<>();
    private String currentUserId = "";
    public static final int MYSELF = 0 ;
    public static final int OTHER = 1;

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message.getIdSender().equals(currentUserId)) return MYSELF;
        else return OTHER;
    }
    public void setCurrentUserId(String data) {
        this.currentUserId = data;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Message> data){
        messageList.clear();
        messageList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == OTHER) {
            ItemMessageLeftBinding bindingLeft = ItemMessageLeftBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MessageReceiverViewHolder(bindingLeft);
        }
        ItemMessageSenderBinding bindingDefault = ItemMessageSenderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MessageSenderViewHolder(bindingDefault);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageReceiverViewHolder) {
            ((MessageReceiverViewHolder) holder).setData(messageList.get(position));
        }
        if (holder instanceof MessageSenderViewHolder) {
            ((MessageSenderViewHolder) holder).setData(messageList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageSenderViewHolder extends RecyclerView.ViewHolder{
        ItemMessageSenderBinding binding ;
        public MessageSenderViewHolder(@NonNull ItemMessageSenderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void setData(Message message) {
            binding.tvMessage.setText(message.getMessage());
        }
    }

    public static class MessageReceiverViewHolder extends RecyclerView.ViewHolder {
        ItemMessageLeftBinding binding ;
        public MessageReceiverViewHolder(@NonNull ItemMessageLeftBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void setData(Message message) {
            binding.tvMessage.setText(message.getMessage());
        }
    }
}
