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
import com.example.shopbee.databinding.FavoriteItemBinding;
import com.example.shopbee.databinding.ShopItemBinding;
import com.example.shopbee.ui.search.adapter.ProductAdapter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(String asin);
    }
    ProductAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ProductAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<AmazonProductByCategoryResponse.Data.Product> products;
    public FavoriteAdapter(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }
    public void setProducts(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FavoriteItemBinding binding = FavoriteItemBinding.inflate(inflater, parent, false);
        return new FavoriteAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
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
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FavoriteItemBinding binding;
        public ViewHolder(@NonNull FavoriteItemBinding binding) {
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

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.clear();
    }
}
