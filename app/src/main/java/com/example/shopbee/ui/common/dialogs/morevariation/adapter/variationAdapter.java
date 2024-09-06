package com.example.shopbee.ui.common.dialogs.morevariation.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.DisplayVariationItemBinding;

import java.util.List;

public class variationAdapter extends RecyclerView.Adapter<variationAdapter.VariationViewHolder> {
    private List<Pair<String, String>> listVariation;
    public variationAdapter(List<Pair<String, String>> listVariation) {
        this.listVariation = listVariation;
    }
    @NonNull
    @Override
    public VariationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayVariationItemBinding binding = DisplayVariationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VariationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VariationViewHolder holder, int position) {
        Pair<String, String> pair = listVariation.get(position);
        holder.binding.nameVariation.setText(pair.first + ": ");
        holder.binding.valueVariation.setText(pair.second);
    }

    @Override
    public int getItemCount() {
        return listVariation.size();
    }
    public static class VariationViewHolder extends RecyclerView.ViewHolder{
        DisplayVariationItemBinding binding;
        public VariationViewHolder(@NonNull DisplayVariationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
