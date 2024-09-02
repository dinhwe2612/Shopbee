package com.example.shopbee.ui.productdetail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonSearchResponse;
import com.example.shopbee.databinding.DealItemBinding;
import com.example.shopbee.databinding.RecommendedItemBinding;
import com.example.shopbee.databinding.SaleItemBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {
    List<AmazonProductByCategoryResponse.Data.Product> products = new ArrayList<>();
    Context context;
    final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public RecommendedAdapter(Context context) {
        this.context = context;
    }
    public void setProducts(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedViewHolder(RecommendedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        compositeDisposable.add(Observable.fromCallable(() -> {
                            FutureTarget<Bitmap> futureTarget = Glide.with(holder.binding.productImg.getContext())
                                    .asBitmap()
                                    .load(products.get(position).getProduct_photo())
                                    .submit();
                            return futureTarget.get();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> {
                            holder.binding.productImg.setImageBitmap(bitmap);
                        }, throwable -> {
                            Log.e("error", throwable.getMessage());
                        })
        );
        // bind title
        String title = products.get(position).getProduct_title();
        if (title.length() > 20) title = title.substring(0, 19) + "...";
        holder.binding.title.setText(title);
        // bind number of ratings
        String numberOfRatings = products.get(position).getProduct_num_ratings();
        if (numberOfRatings == null) numberOfRatings = "0";
        holder.binding.numRatings.setText(numberOfRatings);
        // bind rating bar
        String rating = products.get(position).getProduct_star_rating();
        if (rating == null) rating = "0";
        holder.binding.ratingBar.setRating(Float.parseFloat(rating));
        // bind price
        holder.binding.curPrice.setText(products.get(position).getProduct_original_price());
        String oldPrice = products.get(position).getProduct_original_price();
        if (oldPrice == null) {
            holder.binding.curPrice.setTextColor(context.getResources().getColor(R.color.black_background));
            holder.binding.oldPrice.setVisibility(View.GONE);
        } else {
            holder.binding.oldPrice.setText(oldPrice);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder {
        RecommendedItemBinding binding;

        public RecommendedViewHolder(@NonNull RecommendedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
