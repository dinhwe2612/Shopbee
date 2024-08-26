package com.example.shopbee.ui.profile.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.MyOrdersDeliveredItemBinding;
import com.example.shopbee.data.model.OrderProductItem;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {
    List<OrderProductItem> orderProductItemList;

    public OrderProductAdapter(List<OrderProductItem> orderProductItemList) {
        this.orderProductItemList = orderProductItemList;
    }
    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyOrdersDeliveredItemBinding binding = MyOrdersDeliveredItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        OrderProductItem orderProductItem = orderProductItemList.get(position);
        holder.binding.orderNumber.setText(orderProductItem.getOrderNumber());
        holder.binding.date.setText(orderProductItem.getDate());
        holder.binding.trackingNumber.setText(orderProductItem.getTrackingNumber());
        holder.binding.quantity.setText(orderProductItem.getQuantity().toString());
        holder.binding.totalAmount.setText(orderProductItem.getTotalAmount());
    }

    @Override
    public int getItemCount() {
        return orderProductItemList.size();
    }
    static class OrderProductViewHolder extends RecyclerView.ViewHolder {
        MyOrdersDeliveredItemBinding binding;
        public OrderProductViewHolder(@NonNull MyOrdersDeliveredItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
