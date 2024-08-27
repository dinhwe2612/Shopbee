package com.example.shopbee.ui.shop.search.adapter;

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
import com.example.shopbee.databinding.CategoriesShopNewItem1Binding;
import com.example.shopbee.databinding.ShopItemBinding;
import com.example.shopbee.ui.shop.adapter.CategoriesAdapter;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<AmazonProductByCategoryResponse.Data.Product> products;
    public ProductAdapter(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }
    public void setProducts(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShopItemBinding binding = ShopItemBinding.inflate(inflater, parent, false);
        return new ProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShopItemBinding binding;
        public ViewHolder(@NonNull ShopItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView2.setText(products.get(position).getProduct_title());
            binding.simpleRatingBar.setRating(Float.parseFloat(products.get(position).getProduct_star_rating()));
            binding.textView3.setText(products.get(position).getProduct_num_ratings());
            binding.textView1.setText(products.get(position).getProduct_price());
            compositeDisposable.add(Observable.fromCallable(() -> {
                                FutureTarget<Bitmap> futureTarget = Glide.with(binding.imageView.getContext())
                                        .asBitmap()
                                        .load(products.get(position).getProduct_photo())
                                        .submit();
                                return futureTarget.get();
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(bitmap -> {
                                binding.imageView.setImageBitmap(bitmap);
                            }, throwable -> {
                                Log.e("Exception", throwable.getMessage());
                            })
            );
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        compositeDisposable.clear();
    }
}
