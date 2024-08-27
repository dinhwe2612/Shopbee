package com.example.shopbee.ui.shop.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.ui.shop.ShopNavigator;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BaseAdapter<T extends BaseAdapter.BaseViewHolder> extends RecyclerView.Adapter<T> {
    ShopNavigator navigator;
    
    public ShopNavigator getNavigator() {
        return navigator;
    }

    public BaseAdapter(ShopNavigator navigator) {
        this.navigator = navigator;
    }
    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
