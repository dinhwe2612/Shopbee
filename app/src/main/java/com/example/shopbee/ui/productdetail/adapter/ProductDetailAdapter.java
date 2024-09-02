package com.example.shopbee.ui.productdetail.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.ProductDetailBinding;
import com.example.shopbee.databinding.ProductdetailItemBinding;
import com.example.shopbee.ui.productdetail.ProductDetailFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.ProductDetailViewHolder> {
    List<Pair<String, String>> productDetails = new ArrayList<>();
    public void setProductDetail(List<Pair<String, String>> productDetail) {
        this.productDetails = productDetail;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductDetailViewHolder(ProductdetailItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailViewHolder holder, int position) {
        holder.binding.title.setText(productDetails.get(position).first);
        holder.binding.content.setText(productDetails.get(position).second);
    }

    @Override
    public int getItemCount() {
        return productDetails.size();
    }

    public static class ProductDetailViewHolder extends RecyclerView.ViewHolder {
        ProductdetailItemBinding binding;
        public ProductDetailViewHolder(@NonNull ProductdetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
