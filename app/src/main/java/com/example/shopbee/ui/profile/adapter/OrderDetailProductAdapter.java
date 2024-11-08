package com.example.shopbee.ui.profile.adapter;

import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopbee.data.model.OrderProductItem;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.databinding.MyOrdersDeliveredItemBinding;
import com.example.shopbee.databinding.OrderDetailsBinding;
import com.example.shopbee.databinding.OrderDetailsItemBinding;

import java.util.List;

public class OrderDetailProductAdapter extends RecyclerView.Adapter<OrderDetailProductAdapter.OrderDetailProductViewHolder> {
    private List<OrderDetailResponse> orderDetailResponseList;
    public interface Listener{
        void onItemClicked(int position);
    }
    private Listener listener;
    public OrderDetailProductAdapter(List<OrderDetailResponse> orderDetailResponseList, Listener listener) {
        this.orderDetailResponseList = orderDetailResponseList;
        this.listener = listener;
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
        int sizeInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 104, holder.itemView.getContext().getResources().getDisplayMetrics());
        Glide.with(holder.itemView.getContext())
                .load(orderDetailResponse.getUrlImage())
                .override(sizeInPixels, sizeInPixels)
                .into(holder.binding.productImage);
        holder.binding.quantity.setText(String.valueOf(orderDetailResponse.getQuantity()));
        holder.binding.productName.setText(orderDetailResponse.getProduct_name());
        List<Pair<String, String>> variation = orderDetailResponse.getVariation();
        if (variation != null && !variation.isEmpty()) {
            if (variation.size() == 1) {
                holder.binding.variationText1.setVisibility(View.VISIBLE);
                holder.binding.variation1.setVisibility(View.VISIBLE);
                holder.binding.variationText2.setVisibility(View.GONE);
                holder.binding.variation2.setVisibility(View.GONE);
                String newVarFirst = variation.get(0).first.substring(0, 1).toUpperCase() + variation.get(0).first.substring(1).toLowerCase();
                String newVarSecond = variation.get(0).second.substring(0, 1).toUpperCase() + variation.get(0).second.substring(1).toLowerCase();
                holder.binding.variationText1.setText(newVarFirst + ":");
                holder.binding.variation1.setText(newVarSecond);
            }
            if (variation.size() >= 2) {
                holder.binding.variationText1.setVisibility(View.VISIBLE);
                holder.binding.variation1.setVisibility(View.VISIBLE);
                holder.binding.variationText2.setVisibility(View.VISIBLE);
                holder.binding.variation2.setVisibility(View.VISIBLE);
                String newVarFirst1 = variation.get(0).first.substring(0, 1).toUpperCase() + variation.get(0).first.substring(1).toLowerCase();
                String newVarSecond1 = variation.get(0).second.substring(0, 1).toUpperCase() + variation.get(0).second.substring(1).toLowerCase();
                String newVarFirst2 = variation.get(1).first.substring(0, 1).toUpperCase() + variation.get(1).first.substring(1).toLowerCase();
                String newVarSecond2 = variation.get(1).second.substring(0, 1).toUpperCase() + variation.get(1).second.substring(1).toLowerCase();
                holder.binding.variationText1.setText(newVarFirst1 + ":");
                holder.binding.variation1.setText(newVarSecond1);
                holder.binding.variationText2.setText(newVarFirst2 + ":");
                holder.binding.variation2.setText(newVarSecond2);
                if (variation.size() > 2) {
                    holder.binding.moreText.setVisibility(View.VISIBLE);
                    holder.binding.moreText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onItemClicked(position);
                        }
                    });
                }
            }
        }
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
