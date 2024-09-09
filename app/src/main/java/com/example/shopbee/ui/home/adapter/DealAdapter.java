package com.example.shopbee.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.databinding.DealItemBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {
    public interface Listener {
        void onDealItemClick(String asin);
    }
    Listener listener;
    List<AmazonDealsResponse.Data.Deal> deals = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public DealAdapter(Listener listener) {
        this.listener = listener;
    }
    public void setProducts(List<AmazonDealsResponse.Data.Deal> products) {
        // limit 10 item
        if (products.size() > 10) {
            deals = products.subList(0, 10);
        } else {
            deals = products;
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DealItemBinding binding = DealItemBinding.inflate(inflater, parent, false);
        return new DealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        compositeDisposable.add(Completable.fromAction(() -> holder.binding.setDeal(deals.get(position)))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> holder.binding.progressBar.setVisibility(View.GONE))
        );
        holder.binding.dealItem.setOnClickListener(v -> {
            listener.onDealItemClick(deals.get(position).getProduct_asin());
        });
    }

    @Override
    public int getItemCount() {
        if (deals == null) {
            return 0;
        }
        return deals.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull DealViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        compositeDisposable.clear();
    }

    public static class DealViewHolder extends RecyclerView.ViewHolder {
        private final DealItemBinding binding;
        public DealViewHolder(@NonNull DealItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}