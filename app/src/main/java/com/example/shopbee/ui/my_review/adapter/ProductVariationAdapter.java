package com.example.shopbee.ui.my_review.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.DetailsItemBinding;
import com.example.shopbee.databinding.MyReviewItemBinding;

import java.util.List;

public class ProductVariationAdapter extends RecyclerView.Adapter<ProductVariationAdapter.ViewHolder> {
    private List<Pair<String, String>> variation;

    public void setVariation(List<Pair<String, String>> variation) {
        this.variation = variation;
    }

    public ProductVariationAdapter(List<Pair<String, String>> variation) {
        this.variation = variation;
    }

    @NonNull
    @Override
    public ProductVariationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVariationAdapter.ViewHolder holder, int position) {
        holder.binding.variationType.setText(variation.get(position).first + ": ");
        holder.binding.variationDetails.setText(variation.get(position).second);
    }

    @Override
    public int getItemCount() {
        if (variation != null) {
            return variation.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private DetailsItemBinding binding;
        public ViewHolder(@NonNull DetailsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
