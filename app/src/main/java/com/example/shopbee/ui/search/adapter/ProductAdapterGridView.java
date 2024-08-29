package com.example.shopbee.ui.search.adapter;

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
import com.example.shopbee.databinding.SaleItemBinding;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductAdapterGridView extends RecyclerView.Adapter<ProductAdapterGridView.ViewHolder>{
    public interface OnItemClickListener {
        void onItemClick(String asin);
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<AmazonProductByCategoryResponse.Data.Product> products;
    public ProductAdapterGridView(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }
    public void setProducts(List<AmazonProductByCategoryResponse.Data.Product> products) {
        this.products = products;
    }
    @NonNull
    @Override
    public ProductAdapterGridView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SaleItemBinding binding = SaleItemBinding.inflate(inflater, parent, false);
        return new ProductAdapterGridView.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterGridView.ViewHolder holder, int position) {
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
        private SaleItemBinding binding;
        public ViewHolder(@NonNull SaleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView1.setText(products.get(position).getProduct_title());
            if (products.get(position).getProduct_star_rating() != null) {
                binding.simpleRatingBar.setRating(Float.parseFloat(products.get(position).getProduct_star_rating()));
            } else {
                binding.simpleRatingBar.setRating(0);
            }

//            Log.e("rating", "msg: " + position + " " + products.get(position).getProduct_star_rating());
            binding.textView3.setText("(" + products.get(position).getProduct_num_ratings() + ")");
            if (products.get(position).getProduct_original_price() != null) {
                binding.textView2.setText(products.get(position).getProduct_original_price());
            }
            else {
                binding.textView2.setVisibility(View.GONE);
                binding.imageView4.setVisibility(View.GONE);
            }
            binding.textView4.setText(products.get(position).getProduct_price());
            if (products.get(position).getProduct_photo() != null) {
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
    }
}
