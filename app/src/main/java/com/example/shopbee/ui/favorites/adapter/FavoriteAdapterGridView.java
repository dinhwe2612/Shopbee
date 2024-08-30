package com.example.shopbee.ui.favorites.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.databinding.FavoriteGridItemBinding;
import com.example.shopbee.databinding.SaleItemBinding;
import com.example.shopbee.ui.search.adapter.ProductAdapterGridView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteAdapterGridView extends RecyclerView.Adapter<FavoriteAdapterGridView.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(String asin);
    }
    ProductAdapterGridView.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(ProductAdapterGridView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<AmazonProductByCategoryResponse.Data.Product> products;
    public FavoriteAdapterGridView(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }
    public void setProducts(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }
    @NonNull
    @Override
    public FavoriteAdapterGridView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FavoriteGridItemBinding binding = FavoriteGridItemBinding.inflate(inflater, parent, false);
        return new FavoriteAdapterGridView.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapterGridView.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(products.get(position).getAsin());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products == null) return 0;
        return products.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FavoriteGridItemBinding binding;
        public ViewHolder(@NonNull FavoriteGridItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.itemFavoriteName.setText(products.get(position).getProduct_title());
            if (products.get(position).getProduct_star_rating() != null) {
                binding.simpleRatingBar.setRating(Float.parseFloat(products.get(position).getProduct_star_rating()));
            } else {
                binding.simpleRatingBar.setRating(0);
            }
            binding.numRating.setText("(" + products.get(position).getProduct_num_ratings() + ")");
            binding.favoriteItemPrice.setText(products.get(position).getProduct_price());
            // variation
            if (products.get(position).getProduct_photo() != null) {
                compositeDisposable.add(Observable.fromCallable(() -> {
                                    FutureTarget<Bitmap> futureTarget = Glide.with(binding.image.getContext())
                                            .asBitmap()
                                            .load(products.get(position).getProduct_photo())
                                            .submit();
                                    return futureTarget.get();
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(bitmap -> {
                                    binding.image.setImageBitmap(bitmap);
                                }, throwable -> {
                                    Log.e("Exception", throwable.getMessage());
                                })
                );
            }
        }
    }
}
