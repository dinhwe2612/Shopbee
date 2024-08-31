package com.example.shopbee.ui.productdetail.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.AboutProductItemBinding;

import java.util.ArrayList;
import java.util.List;

public class AboutProductAdapter extends RecyclerView.Adapter<AboutProductAdapter.AboutProductViewHolder> {
    List<String> abouts = new ArrayList<>();
    public void setAbouts(List<String> abouts) {
        this.abouts = abouts;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AboutProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AboutProductViewHolder(AboutProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AboutProductViewHolder holder, int position) {
        holder.binding.content.setText(abouts.get(position));
    }

    @Override
    public int getItemCount() {
        return abouts.size();
    }

    public static class AboutProductViewHolder extends RecyclerView.ViewHolder {
        AboutProductItemBinding binding;
        public AboutProductViewHolder(@NonNull AboutProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
