package com.example.shopbee.ui.home.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.data.model.api.AmazonBestSellerResponse;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.databinding.BestSellerItemBinding;
import com.example.shopbee.databinding.DealItemBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder> {
    List<AmazonBestSellerResponse.Data.BestSeller> products = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public interface Listener {
        void onBestSellerItemClick(String asin);
    }
    Listener listener;
    public BestSellerAdapter(Listener listener) {
        this.listener = listener;
    }
    public void setProducts(List<AmazonBestSellerResponse.Data.BestSeller> products) {
        this.products = products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BestSellerItemBinding binding = BestSellerItemBinding.inflate(inflater, parent, false);
        return new BestSellerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, int position) {
        compositeDisposable.add(Observable.fromCallable(() -> {
            FutureTarget<Bitmap> futureTarget = Glide.with(holder.itemView.getContext())
                    .asBitmap()
                    .load(products.get(position).getProduct_photo())
                    .submit();
                return futureTarget.get();
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(holder.binding.productImg::setImageBitmap)
        );
        holder.binding.title.setText(products.get(position).getProduct_title());
        holder.binding.curPrice.setText(products.get(position).getProduct_price());
        holder.binding.numRatings.setText(products.get(position).getProduct_num_ratings());
        holder.binding.ratingBar.setRating(Float.parseFloat(products.get(position).getProduct_star_rating()));
        holder.binding.bestSellerItem.setOnClickListener(v -> {
            listener.onBestSellerItemClick(products.get(position).getAsin());
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BestSellerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        compositeDisposable.clear();
    }

    public static class BestSellerViewHolder extends RecyclerView.ViewHolder {
        private final BestSellerItemBinding binding;
        public BestSellerViewHolder(@NonNull BestSellerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
