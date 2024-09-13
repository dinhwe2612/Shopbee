package com.example.shopbee.ui.my_review.adapter;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.ImageMyReviewsItemBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    public interface OnImageClickListener {
        void onImageClick(Bitmap image);
    }
    OnImageClickListener onImageClickListener;
    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }
    private List<Bitmap> images;
    public ImageAdapter(List<Bitmap> images) {
        this.images = images;
    }
    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ImageMyReviewsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        holder.binding.image.setImageBitmap(images.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onImageClickListener != null) {
                onImageClickListener.onImageClick(images.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("FirebaseImageService", "getItemCount: " + images.size());
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageMyReviewsItemBinding binding;
        public ViewHolder(@NonNull ImageMyReviewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
