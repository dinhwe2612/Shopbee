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
import com.example.shopbee.data.model.api.OrderDetailResponse;
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
        void onChangeQuantity(String asin, List<Pair<String, String>> variations, boolean increase, int position);
        void onSaveToFavorites(int position, ImageView imageView);
        void onDeleteFromList(String asin, List<Pair<String, String>> variations, int position);
        void onMoreVariationOption(int position);
    }
    onChangeQuantityListener onChangeQuantityListener;
    public void setOnChangeQuantityListener(onChangeQuantityListener onChangeQuantityListener) {
        this.onChangeQuantityListener = onChangeQuantityListener;
    }
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<OrderDetailResponse> products = new ArrayList<>();
    public List<OrderDetailResponse> getProducts() {
        return products;
    }

    public BagAdapter() {
    }

    public void setProducts(List<OrderDetailResponse> products) {
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
        compositeDisposable.clear();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
//        compositeDisposable.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private void setOnClickForViews() {
            binding.imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    onChangeQuantityListener.onChangeQuantity(products.get(position).getProduct_id(), products.get(position).getVariation(), true, position);
                }
            });
            binding.textView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    onChangeQuantityListener.onMoreVariationOption(position);
                }
            });
            binding.popupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int position = getAdapterPosition();
                            if (item.getItemId() == R.id.add_to_favorites_popup) {
                                onChangeQuantityListener.onSaveToFavorites(position, binding.imageView);
                                return true;
                            } else if (item.getItemId() == R.id.delete_from_list_popup) {
                                onChangeQuantityListener.onDeleteFromList(products.get(position).getProduct_id(), products.get(position).getVariation(), position);
//                                quantities.remove(position);
//                                products.remove(position);
//                                variations.remove(position);
//                                notifyItemRemoved(position);
//                                notifyItemRangeChanged(position, getItemCount());
//                                notifyDataSetChanged();
//                                notifyItemRemoved(position);
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
                    int position = getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) return;
                    if (products.get(position).getQuantity() > 1) {
                        onChangeQuantityListener.onChangeQuantity(products.get(position).getProduct_id(), products.get(position).getVariation(), false, position);
                    }
                    else {
                        onChangeQuantityListener.onDeleteFromList(products.get(position).getProduct_id(), products.get(position).getVariation(), position);
//                        quantities.remove(position);
//                        products.remove(position);
//                        variations.remove(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, getItemCount());
//                        notifyDataSetChanged();
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, getItemCount());
                    }
                }
            });
        }
        private MyBagItemBinding binding;
        public ViewHolder(@NonNull MyBagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setOnClickForViews();
        }
        public void bindView(int position) {
            binding.textView2.setText(products.get(position).getProduct_name());
            if (products.get(position).getPrice() != null) {
                binding.textView6.setText(products.get(position).getPrice());
            }
            // variation
            if (products.get(position).getUrlImage() != null) {
                Glide.with(binding.imageView.getContext())
                                            .asBitmap()
                                            .load(products.get(position).getUrlImage())
                                            .timeout(600000)
                                            .into(binding.imageView);
            }
            if (products.get(position).getVariation().size() > 0) {
                binding.textView.setVisibility(View.VISIBLE);
                binding.textView3.setVisibility(View.VISIBLE);
                binding.textView.setText(products.get(position).getVariation().get(0).first);
                binding.textView3.setText(products.get(position).getVariation().get(0).second);
            }
            if (products.get(position).getVariation().size() > 1) {
                binding.textView4.setVisibility(View.VISIBLE);
                binding.textView7.setVisibility(View.VISIBLE);
                binding.textView4.setText(products.get(position).getVariation().get(1).first);
                binding.textView7.setText(products.get(position).getVariation().get(1).second);
            }
            if (products.get(position).getVariation().size() > 2) {
                binding.textView8.setVisibility(View.GONE);
            }
            binding.textView5.setText(String.valueOf(products.get(position).getQuantity()));
        }
    }
}
