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
        Log.d("TAG", "variation: " + variation.get(0).first + " " + variation.get(0).second);
        if (variation != null && !variation.isEmpty()) {
            if (variation.size() == 1) {
                holder.binding.variationText1.setVisibility(View.VISIBLE);
                holder.binding.variation1.setVisibility(View.VISIBLE);
                holder.binding.variationText2.setVisibility(View.GONE);
                holder.binding.variation2.setVisibility(View.GONE);
                holder.binding.variationText1.setText(variation.get(0).first + ":");
                holder.binding.variation1.setText(variation.get(0).second);
            }
            if (variation.size() >= 2) {
                holder.binding.variationText1.setVisibility(View.VISIBLE);
                holder.binding.variation1.setVisibility(View.VISIBLE);
                holder.binding.variationText2.setVisibility(View.VISIBLE);
                holder.binding.variation2.setVisibility(View.VISIBLE);
                for (Pair<String, String> pair : variation) {
                    Log.d("TAG", "onBindViewHolder: " + pair.first + " " + pair.second);
                }
                holder.binding.variationText1.setText(variation.get(0).first + ":");
                holder.binding.variation1.setText(variation.get(0).second);
                holder.binding.variationText2.setText(variation.get(1).first + ":");
                holder.binding.variation2.setText(variation.get(1).second);
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
