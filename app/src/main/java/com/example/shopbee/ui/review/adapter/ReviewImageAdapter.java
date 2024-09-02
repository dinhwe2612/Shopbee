package com.example.shopbee.ui.review.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.databinding.ImageReviewItemBinding;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageAdapter.ReviewImageViewHolder> {
    List<String> images;
    public ReviewImageAdapter(List<String> images) {
        this.images = images;
    }
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    @Override
    public ReviewImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewImageViewHolder(ImageReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewImageViewHolder holder, int position) {
        compositeDisposable.add(Observable.fromCallable(() -> {
                FutureTarget<Bitmap> futureTarget = Glide.with(holder.binding.image.getContext())
                        .asBitmap()
                        .load(images.get(position))
                        .submit();
                return futureTarget.get();
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(bitmap -> {
                holder.binding.image.setImageBitmap(bitmap);
            }, throwable -> {
                Log.e("error", throwable.getMessage());
            })
        );
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.clear();
    }

    static public class ReviewImageViewHolder extends RecyclerView.ViewHolder {
        ImageReviewItemBinding binding;
        public ReviewImageViewHolder(ImageReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
