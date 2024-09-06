package com.example.shopbee.ui.productdetail.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.databinding.ProductphotosItemBinding;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductPhotosAdapter extends RecyclerView.Adapter<ProductPhotosAdapter.ProductPhotosViewHolder> {
    ImageView currentView;

    public ImageView getCurrentView() {
        return currentView;
    }

    List<String> urlImages;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ProductPhotosAdapter() {
        urlImages = new ArrayList<>();
    }
    public void setUrlImages(List<String> urlImages) {
        this.urlImages = urlImages;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductPhotosViewHolder(ProductphotosItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPhotosViewHolder holder, int position) {
        compositeDisposable.add(Observable.fromCallable(() -> {
                            FutureTarget<Bitmap> futureTarget = Glide.with(holder.binding.image.getContext())
                                    .asBitmap()
                                    .load(urlImages.get(position))
                                    .submit();
                            return futureTarget.get();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> {
                            holder.binding.image.setImageBitmap(bitmap);
                            currentView = holder.binding.image;
                        }, throwable -> {
                            Log.e("error", throwable.getMessage());
                        })
        );
    }

    @Override
    public int getItemCount() {
        return urlImages.size();
    }

    public static class ProductPhotosViewHolder extends RecyclerView.ViewHolder {
        ProductphotosItemBinding binding;
        public ProductPhotosViewHolder(@NonNull ProductphotosItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.clear();
    }
}
