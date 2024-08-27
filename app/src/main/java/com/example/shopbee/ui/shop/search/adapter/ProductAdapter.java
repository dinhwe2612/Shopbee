package com.example.shopbee.ui.shop.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.databinding.CategoriesShopNewItem1Binding;
import com.example.shopbee.databinding.ShopItemBinding;
import com.example.shopbee.ui.shop.adapter.CategoriesAdapter;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<AmazonProductByCategoryResponse.Data.Product> products;
    public ProductAdapter(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }
    public void setProducts(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShopItemBinding binding = ShopItemBinding.inflate(inflater, parent, false);
        return new ProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShopItemBinding binding;
        public ViewHolder(@NonNull ShopItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView2.setText(products.get(position).getProduct_title());
        }
    }
}
