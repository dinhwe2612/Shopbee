package com.example.shopbee.ui.bag.adapter;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.databinding.FavoriteItemBinding;
import com.example.shopbee.databinding.MyBagItemBinding;
import com.example.shopbee.ui.favorites.adapter.FavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.ViewHolder> {
    public interface onChangeQuantityListener {
        void onChangeQuantity(String asin, List<Pair<String, String>> variations, boolean increase);
        void onSaveToFavorites(String asin, List<Pair<String, String>> variations, ImageView imageView);
        void onDeleteFromList(String asin, List<Pair<String, String>> variations, int position);
    }
    onChangeQuantityListener onChangeQuantityListener;
    public void setOnChangeQuantityListener(onChangeQuantityListener onChangeQuantityListener) {
        this.onChangeQuantityListener = onChangeQuantityListener;
    }
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<Integer> quantities = new ArrayList<>();
    List<AmazonProductDetailsResponse> products = new ArrayList<>();
    List<List<Pair<String, String>>> variations = new ArrayList<>();
    public BagAdapter() {
    }
    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public void setVariations(List<List<Pair<String, String>>> variations) {
        this.variations = variations;
    }

    public void setProducts(List<AmazonProductDetailsResponse> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public BagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyBagItemBinding binding = MyBagItemBinding.inflate(inflater, parent, false);
        return new BagAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BagAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (products == null) {
            return 0;
        }
        return products.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MyBagItemBinding binding;
        public ViewHolder(@NonNull MyBagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView2.setText(products.get(position).getData().getProduct_title());
            if (products.get(position).getData().getProduct_price() != null) {
                binding.textView6.setText(products.get(position).getData().getProduct_price());
            }
            // variation
            if (products.get(position).getData().getProduct_photo() != null) {
                compositeDisposable.add(Observable.fromCallable(() -> {
                                    FutureTarget<Bitmap> futureTarget = Glide.with(binding.imageView.getContext())
                                            .asBitmap()
                                            .load(products.get(position).getData().getProduct_photo())
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
            if (variations.get(position).size() > 0) {
                binding.textView.setVisibility(View.VISIBLE);
                binding.textView3.setVisibility(View.VISIBLE);
                binding.textView.setText(variations.get(position).get(0).first);
                binding.textView3.setText(variations.get(position).get(0).second);
            }
            if (variations.get(position).size() > 1) {
                binding.textView4.setVisibility(View.VISIBLE);
                binding.textView7.setVisibility(View.VISIBLE);
                binding.textView4.setText(variations.get(position).get(1).first);
                binding.textView7.setText(variations.get(position).get(1).second);
            }
            if (variations.get(position).size() > 2) {
                binding.textView8.setVisibility(View.GONE);
            }
            binding.textView5.setText(String.valueOf(quantities.get(position)));
            binding.imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onChangeQuantityListener.onChangeQuantity(products.get(position).getData().getAsin(), variations.get(position), true);
                    quantities.set(position, quantities.get(position) + 1);
//                    Log.e("quantity", "quantity: " + quantities.get(position));
//                    notifyItemChanged(position);
                    binding.textView5.setText(String.valueOf(quantities.get(position)));
                }
            });
            binding.popupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.add_to_favorites_popup) {
                                onChangeQuantityListener.onSaveToFavorites(products.get(position).getData().getAsin(), variations.get(position), binding.imageView);
                                return true;
                            } else if (item.getItemId() == R.id.delete_from_list_popup) {
                                onChangeQuantityListener.onDeleteFromList(products.get(position).getData().getAsin(), variations.get(position), position);
                                quantities.remove(position);
                                products.remove(position);
                                variations.remove(position);
//                                notifyDataSetChanged();
                                notifyItemRemoved(position);
                                return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            binding.imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (quantities.get(position) > 1) {
                        onChangeQuantityListener.onChangeQuantity(products.get(position).getData().getAsin(), variations.get(position), false);
                        quantities.set(position, quantities.get(position) - 1);
//                        notifyItemChanged(position);
                        binding.textView5.setText(String.valueOf(quantities.get(position)));
                    }
                    else {
                        onChangeQuantityListener.onDeleteFromList(products.get(position).getData().getAsin(), variations.get(position), position);
                        quantities.remove(position);
                        products.remove(position);
                        variations.remove(position);
//                        notifyDataSetChanged();
                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, getItemCount());
                    }
                }
            });
        }
    }
}
