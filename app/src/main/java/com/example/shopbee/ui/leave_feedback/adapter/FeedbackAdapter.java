package com.example.shopbee.ui.leave_feedback.adapter;

import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.databinding.FavoriteItemBinding;
import com.example.shopbee.databinding.LeaveFeedbackItemBinding;
import com.example.shopbee.ui.favorites.adapter.FavoriteAdapter;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    public interface OnReviewClickListener {
        void onReviewClick(int position, boolean reviewed);
    }
    public OnReviewClickListener onReviewClickListener;
    private List<OrderDetailResponse> orderDetailResponseList;
    private List<Boolean> isReviewedList;

    public void setOnReviewClickListener(OnReviewClickListener onReviewClickListener) {
        this.onReviewClickListener = onReviewClickListener;
    }

    public void setIsReviewedList(List<Boolean> isReviewedList) {
        this.isReviewedList = isReviewedList;
    }

    public void setOrderDetailResponseList(List<OrderDetailResponse> orderDetailResponseList) {
        this.orderDetailResponseList = orderDetailResponseList;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LeaveFeedbackItemBinding binding = LeaveFeedbackItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {
        OrderDetailResponse orderDetailResponse = orderDetailResponseList.get(position);

        holder.binding.price.setText(orderDetailResponse.getPrice());
        int sizeInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 104, holder.itemView.getContext().getResources().getDisplayMetrics());
        Glide.with(holder.itemView.getContext())
                .load(orderDetailResponse.getUrlImage())
                .timeout(60000)
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
                holder.binding.variationText1.setText(variation.get(0).first + ":");
                holder.binding.variation1.setText(variation.get(0).second);
            }
            if (variation.size() >= 2) {
                holder.binding.variationText1.setVisibility(View.VISIBLE);
                holder.binding.variation1.setVisibility(View.VISIBLE);
                holder.binding.variationText2.setVisibility(View.VISIBLE);
                holder.binding.variation2.setVisibility(View.VISIBLE);
                holder.binding.variationText1.setText(variation.get(0).first + ":");
                holder.binding.variation1.setText(variation.get(0).second);
                holder.binding.variationText2.setText(variation.get(1).first + ":");
                holder.binding.variation2.setText(variation.get(1).second);
                if (variation.size() > 2) {
                    holder.binding.moreText.setVisibility(View.VISIBLE);
                }
            }
        }
        if (isReviewedList.get(position)) {
            holder.binding.writeAReview.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.gray));
        }
        else {
            holder.binding.writeAReview.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.red));
        }
        holder.binding.writeAReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onReviewClickListener != null) {
                    onReviewClickListener.onReviewClick(position, isReviewedList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderDetailResponseList == null || isReviewedList == null) {
            return 0;
        }
        return orderDetailResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LeaveFeedbackItemBinding binding;
        public ViewHolder(@NonNull LeaveFeedbackItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
