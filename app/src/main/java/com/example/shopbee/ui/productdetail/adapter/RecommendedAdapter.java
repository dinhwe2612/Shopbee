package com.example.shopbee.ui.productdetail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.databinding.DealItemBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {
    List<AmazonDealsResponse.Data.Deal> deals = new ArrayList<>();
    final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public void setDeals(List<AmazonDealsResponse.Data.Deal> deals) {
        this.deals = deals;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedViewHolder(DealItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        holder.binding.setDeal(deals.get(position));
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder {
        DealItemBinding binding;

        public RecommendedViewHolder(@NonNull DealItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
