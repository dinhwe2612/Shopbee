package com.example.shopbee.ui.my_review.adapter;

import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.AboutProductItemBinding;
import com.example.shopbee.databinding.MyReviewItemBinding;
import com.example.shopbee.ui.common.dialogs.writereivewdialog.WriteReviewEvent;
import com.example.shopbee.ui.productdetail.adapter.AboutProductAdapter;

import java.util.List;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder> {
    public interface DetailsClickListener {
        void onDetailsClick(int position);
        void onImageClick(Bitmap image);
    }
    DetailsClickListener detailsClickListener;

    public void setDetailsClickListener(DetailsClickListener detailsClickListener) {
        this.detailsClickListener = detailsClickListener;
    }

    public List<WriteReviewEvent> getReviewList() {
        return reviewList;
    }

    private List<WriteReviewEvent> reviewList;

    public void setReviewList(List<WriteReviewEvent> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public MyReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MyReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyReviewAdapter.ViewHolder holder, int position) {
        WriteReviewEvent event = reviewList.get(position);

        holder.binding.productName.setText(event.getOrderDetailResponse().getProduct_name());

        holder.binding.variationRCV.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        holder.binding.variationRCV.setAdapter(new ProductVariationAdapter(event.getOrderDetailResponse().getVariation()));

        holder.binding.ratingBar.setRating(event.getStarRating());

        holder.binding.title.setText(event.getReviewTitle());

        holder.binding.reviewDate.setText(event.getReviewDate());

        holder.binding.content.setText(event.getReviewContent());

        holder.binding.imagesRCV.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        ImageAdapter imageAdapter = new ImageAdapter(event.getReviewImages());
        imageAdapter.setOnImageClickListener(image -> detailsClickListener.onImageClick(image));
        holder.binding.imagesRCV.setAdapter(imageAdapter);
        holder.binding.details.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (detailsClickListener != null && currentPosition != RecyclerView.NO_POSITION) {
                detailsClickListener.onDetailsClick(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (reviewList != null) {
            return reviewList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MyReviewItemBinding binding;
        public ViewHolder(@NonNull MyReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
