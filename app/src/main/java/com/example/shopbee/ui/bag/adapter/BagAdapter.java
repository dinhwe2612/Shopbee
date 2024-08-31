package com.example.shopbee.ui.bag.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.databinding.FavoriteItemBinding;
import com.example.shopbee.databinding.MyBagItemBinding;
import com.example.shopbee.ui.favorites.adapter.FavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ViewHolder> {
    List<AmazonProductByCategoryResponse.Data.Product> products = new ArrayList<>();
    List<List<Pair<String, String>>> variations = new ArrayList<>();

    public void setVariations(List<List<Pair<String, String>>> variations) {
        this.variations = variations;
    }

    public void setProducts(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public BagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyBagItemBinding binding = MyBagItemBinding.inflate(inflater, parent, false);
        return new BagAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BagAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MyBagItemBinding binding;
        public ViewHolder(@NonNull MyBagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {

        }
    }
}
