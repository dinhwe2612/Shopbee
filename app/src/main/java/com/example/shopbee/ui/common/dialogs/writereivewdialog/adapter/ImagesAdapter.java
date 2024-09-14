package com.example.shopbee.ui.common.dialogs.writereivewdialog.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.ImageReviewItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    public interface Listener {
        void onItemClick(Bitmap bitmap);
    }
    Listener listener;
    public ImagesAdapter(Listener listener) {
        this.listener = listener;
    }
    List<Bitmap> images = new ArrayList<>();
    public void addImages(List<Bitmap> images){
        int oldSize = this.images.size();
        this.images.addAll(images);
        int newSize = this.images.size();
        notifyItemRangeInserted(oldSize,newSize - oldSize);
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(ImageReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.binding.image.setImageBitmap(images.get(position));
        holder.binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                images.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                listener.onItemClick(images.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public List<Bitmap> getImages() {
        return images;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageReviewItemBinding binding;
        public ImageViewHolder(@NonNull ImageReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
