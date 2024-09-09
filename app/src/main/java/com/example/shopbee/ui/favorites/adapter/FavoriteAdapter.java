package com.example.shopbee.ui.favorites.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
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
        void onItemDeleteClick(String asin, List<Pair<String, String>> variation, int position);
        void onAddToBagClick(String asin, List<Pair<String, String>> variation, ImageView imageView);
        void onMoreVariationOption(int position);
    }
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<AmazonProductDetailsResponse> products;
    List<List<Pair<String, String>>> variations;
//    public FavoriteAdapter(List<AmazonProductDetailsResponse> products) {
//        this.products = products;
//    }

    public List<AmazonProductDetailsResponse> getProducts() {
        return products;
    }

    public List<List<Pair<String, String>>> getVariations() {
        return variations;
    }

    public void setProducts(List<AmazonProductDetailsResponse> products) {
        this.products = products;
    }
    public void setVariations(List<List<Pair<String, String>>> variations) {
        this.variations = variations;
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
                    onItemClickListener.onItemClick(products.get(position).getData().getAsin());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products == null) return 0;
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private void setOnClickForViews() {
            binding.deleteFromList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    onItemClickListener.onItemDeleteClick(products.get(position).getData().getAsin(), variations.get(position), position);
//                    products.remove(position);
//                    variations.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, getItemCount());
//                    notifyItemRemoved(position);
//                    notifyDataSetChanged();
                }
            });
            binding.addToBag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    onItemClickListener.onAddToBagClick(products.get(position).getData().getAsin(), variations.get(position), binding.image);
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
        }
        private FavoriteItemBinding binding;
        public ViewHolder(@NonNull FavoriteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setOnClickForViews();
        }
        public void bindView(int position) {
            binding.itemFavoriteName.setText(products.get(position).getData().getProduct_title());
            if (products.get(position).getData().getProduct_star_rating() != null) {
                binding.simpleRatingBar.setRating(Float.parseFloat(products.get(position).getData().getProduct_star_rating()));
            } else {
                binding.simpleRatingBar.setRating(0);
            }
            binding.numRating.setText("(" + products.get(position).getData().getProduct_num_ratings() + ")");
            binding.favoriteItemPrice.setText(products.get(position).getData().getProduct_price());
            // variation
            if (products.get(position).getData().getProduct_photo() != null) {
                compositeDisposable.add(Observable.fromCallable(() -> {
                                    FutureTarget<Bitmap> futureTarget = Glide.with(binding.image.getContext())
                                            .asBitmap()
                                            .load(products.get(position).getData().getProduct_photo())
                                            .timeout(60000)
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
            if (variations.get(position).size() > 0) {
                binding.layoutColor.setVisibility(View.VISIBLE);
                binding.colorTag.setText(variations.get(position).get(0).first);
                binding.colorDescription.setText(variations.get(position).get(0).second);
            }
            if (variations.get(position).size() > 1) {
                binding.layoutColors.setVisibility(View.VISIBLE);
                binding.sizeTag.setText(variations.get(position).get(1).first);
                binding.sizeDescription.setText(variations.get(position).get(1).second);
            }
            if (variations.get(position).size() <= 2) {
                binding.more.setVisibility(View.GONE);
            }
        }
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
}
