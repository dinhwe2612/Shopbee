package com.example.shopbee.ui.search.dialog.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.filter.ProductCondition;
import com.example.shopbee.databinding.SelectSizeItemBinding;

import java.util.ArrayList;

public class ProductConditionAdapter extends RecyclerView.Adapter<ProductConditionAdapter.ViewHolder> {
    ArrayList<ProductCondition> productConditions;
    public ProductCondition getProductCondition() {
        return productConditions.get(currentPosition);
    }
    int currentPosition;
    ProductConditionAdapter(ProductCondition productCondition) {
        productConditions = new ArrayList<>();
        productConditions.add(ProductCondition.ALL);
        productConditions.add(ProductCondition.NEW);
        productConditions.add(ProductCondition.USED);
        productConditions.add(ProductCondition.RENEWED);
        productConditions.add(ProductCondition.COLLECTIBLE);
        for (int i = 0; i < productConditions.size(); i++) {
            if (productConditions.get(i).equals(productCondition)) {
                currentPosition = i;
                break;
            }
        }
    }
    @NonNull
    @Override
    public ProductConditionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SelectSizeItemBinding binding = SelectSizeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductConditionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductConditionAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(currentPosition);
                currentPosition = position;
                notifyItemChanged(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productConditions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SelectSizeItemBinding binding;
        public ViewHolder(@NonNull SelectSizeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(int position) {
            binding.textView.setText(String.valueOf(productConditions.get(position)));
            if (position == currentPosition) {
                binding.constraintLayout.setBackgroundResource(R.drawable.slight_rounded_red_rectangle);
            }
            else {
                binding.constraintLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_gray_stroke);
            }
        }
    }
}
