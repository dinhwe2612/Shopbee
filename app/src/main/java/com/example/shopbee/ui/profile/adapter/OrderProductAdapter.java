package com.example.shopbee.ui.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.databinding.MyOrdersDeliveredItemBinding;
import com.example.shopbee.data.model.OrderProductItem;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {
    private List<OrderProductItem> orderProductItemList;
    private String statusOfOrder;

    public interface Listener{
        public void onItemClicked(int position);
    }
    private Listener listener;
    public OrderProductAdapter(List<OrderProductItem> orderProductItemList, Listener listener) {
        this.orderProductItemList = orderProductItemList;
        this.listener = listener;
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
        String status = orderProductItem.getStatus();
        String newStatus = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        holder.binding.status.setText(newStatus);
        holder.binding.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(position);
            }
        });
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
