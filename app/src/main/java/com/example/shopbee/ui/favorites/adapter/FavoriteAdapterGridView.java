package com.example.shopbee.ui.favorites.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.databinding.FavoriteGridItemBinding;
import com.example.shopbee.databinding.SaleItemBinding;
import com.example.shopbee.ui.search.adapter.ProductAdapterGridView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteAdapterGridView extends RecyclerView.Adapter<FavoriteAdapterGridView.ViewHolder> {
    FavoriteAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(FavoriteAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<OrderDetailResponse> products;
//    public FavoriteAdapterGridView(List<AmazonProductDetailsResponse> products) {
//        this.products = products;
//    }

    public List<OrderDetailResponse> getProducts() {
        return products;
    }

    public void setProducts(List<OrderDetailResponse> products) {
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
                int position = holder.getAdapterPosition();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(products.get(position).getProduct_id());
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
//        compositeDisposable.clear();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        compositeDisposable.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private void setOnClickForViews() {
            binding.addToBag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    onItemClickListener.onAddToBagClick(position, binding.image);
                }
            });
            binding.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    onItemClickListener.onMoreVariationOption(position);
                }
            });
            binding.deleteFromList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    onItemClickListener.onItemDeleteClick(products.get(position).getProduct_id(), products.get(position).getVariation(), position);
//                    products.remove(position);
//                    variations.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, getItemCount());
//                    notifyItemRemoved(position);
//                    notifyDataSetChanged();
                }
            });
        }
        private FavoriteGridItemBinding binding;
        public ViewHolder(@NonNull FavoriteGridItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setOnClickForViews();
        }
        public void bindView(int position) {
            binding.itemFavoriteName.setText(products.get(position).getProduct_name());
            if (products.get(position).getProduct_star_rating() != null) {
                binding.simpleRatingBar.setRating(Float.parseFloat(products.get(position).getProduct_star_rating()));
            } else {
                binding.simpleRatingBar.setRating(0);
            }
            binding.numRating.setText("(" + products.get(position).getProduct_num_ratings() + ")");
            binding.favoriteItemPrice.setText(products.get(position).getPrice());
            // variation
            if (products.get(position).getUrlImage() != null) {
                Glide.with(binding.image.getContext())
                        .asBitmap()
                        .load(products.get(position).getUrlImage())
                        .timeout(60000)
                        .into(binding.image);
            }
            if (products.get(position).getVariation().size() > 0) {
                binding.colorLayout.setVisibility(View.VISIBLE);
                binding.colorTag.setText(products.get(position).getVariation().get(0).first);
                binding.colorDescription.setText(products.get(position).getVariation().get(0).second);
            }
            if (products.get(position).getVariation().size() > 1) {
                binding.layoutColors.setVisibility(View.VISIBLE);
                binding.sizeTag.setText(products.get(position).getVariation().get(1).first);
                binding.sizeDescription.setText(products.get(position).getVariation().get(1).second);
            }
            if (products.get(position).getVariation().size() > 2) {
                binding.more.setVisibility(View.GONE);
            }
        }
    }
}
