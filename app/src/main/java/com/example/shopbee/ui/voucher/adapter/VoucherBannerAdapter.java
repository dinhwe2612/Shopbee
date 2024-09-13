package com.example.shopbee.ui.voucher.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.VoucherBannerItemBinding;

import java.util.ArrayList;
import java.util.List;

public class VoucherBannerAdapter extends RecyclerView.Adapter<VoucherBannerAdapter.VoucherBannerViewHolder> {
    int[] images = {
            R.drawable.banner_voucher,
            R.drawable.banner_game
    };
    public interface Listener {
        void onBannerGameClick();
    }
    Listener listener;
    public VoucherBannerAdapter(Listener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public VoucherBannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoucherBannerViewHolder(VoucherBannerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherBannerViewHolder holder, int position) {
        holder.binding.voucher.setImageResource(images[position]);
        if (position == 1) {
            holder.binding.voucher.setOnClickListener(v->listener.onBannerGameClick());
        }
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class VoucherBannerViewHolder extends RecyclerView.ViewHolder {
        VoucherBannerItemBinding binding;
        public VoucherBannerViewHolder(@NonNull VoucherBannerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
