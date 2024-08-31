package com.example.shopbee.ui.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopbee.data.model.OrderProductItem;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.databinding.MyOrdersDeliveredItemBinding;
import com.example.shopbee.databinding.OrderDetailsBinding;
import com.example.shopbee.databinding.OrderDetailsItemBinding;

import java.util.List;

public class OrderDetailProductAdapter extends RecyclerView.Adapter<OrderDetailProductAdapter.OrderDetailProductViewHolder> {
    List<OrderDetailResponse> orderDetailResponseList;
    public OrderDetailProductAdapter(List<OrderDetailResponse> orderDetailResponseList) {
        this.orderDetailResponseList = orderDetailResponseList;
    }
    @NonNull
    @Override
    public OrderDetailProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderDetailsItemBinding binding = OrderDetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderDetailProductViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull OrderDetailProductViewHolder holder, int position) {
        OrderDetailResponse orderDetailResponse = orderDetailResponseList.get(position);
        holder.binding.price.setText(orderDetailResponse.getPrice());
        Glide.with(holder.itemView.getContext())
                .load(orderDetailResponse.getUrlImage())
                .into(holder.binding.productImage);
        holder.binding.quantity.setText(String.valueOf(orderDetailResponse.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderDetailResponseList.size();
    }

    public static class OrderDetailProductViewHolder extends RecyclerView.ViewHolder {
        OrderDetailsItemBinding binding;
        public OrderDetailProductViewHolder(@NonNull OrderDetailsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
