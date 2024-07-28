package com.example.shopbee.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.databinding.DealItemBinding;
import com.example.shopbee.databinding.SaleItemBinding;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {
    List<AmazonDealsResponse.Data.Deal> deals;
    public DealAdapter(List<AmazonDealsResponse.Data.Deal> products) {
        this.deals = products;
    }
    public void setProducts(List<AmazonDealsResponse.Data.Deal> products) {
        this.deals = products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DealItemBinding binding = DealItemBinding.inflate(inflater, parent, false);
        return new DealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        holder.binding.setDeal(deals.get(position));
    }

    @Override
    public int getItemCount() {
        if (deals == null) {
            return 0;
        }
        return deals.size();
    }

    public static class DealViewHolder extends RecyclerView.ViewHolder {
        private final DealItemBinding binding;
        public DealViewHolder(@NonNull DealItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
