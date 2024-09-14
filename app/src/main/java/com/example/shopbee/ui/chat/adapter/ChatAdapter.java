package com.example.shopbee.ui.chat.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.ChatBinding;
import com.example.shopbee.databinding.ChatItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    List<Pair<String, Boolean>> list = new ArrayList<>();
    public void addChat(String chat, Boolean isUser) {
        list.add(new Pair<>(chat, isUser));
        notifyItemChanged(list.size() - 1);
    }
    public void addCurrentChat(String chat) {
        int sz = list.size();
        StringBuilder sb = new StringBuilder();
        sb.append(list.get(sz - 1).first);
        sb.append(chat);
        list.set(sz - 1, new Pair<>(sb.toString(), false));
        notifyItemChanged(sz - 1);
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(ChatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        if (list.get(position).second) {
            holder.binding.leftChatLayout.setVisibility(View.GONE);
            holder.binding.rightChatLayout.setVisibility(View.VISIBLE);
            holder.binding.rightChatTextview.setText(list.get(position).first);
        } else {
            holder.binding.rightChatLayout.setVisibility(View.GONE);
            holder.binding.leftChatLayout.setVisibility(View.VISIBLE);
            holder.binding.leftChatTextview.setText(list.get(position).first);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        ChatItemBinding binding;
        public ChatViewHolder(@NonNull ChatItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
