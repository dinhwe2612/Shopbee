package com.example.shopbee.ui.common.dialogs.promoCode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.databinding.PromoCodeItemBinding;
import com.example.shopbee.databinding.SortItemBinding;
import com.example.shopbee.ui.common.dialogs.sortbydialog.DialogAdapter;

import java.util.ArrayList;
import java.util.List;

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.ViewHolder> {
    List<PromoCodeResponse> promoCodeList = new ArrayList<>();
    public void setPromoCodeList(List<PromoCodeResponse> promoCodeList) {
        this.promoCodeList = promoCodeList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PromoCodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PromoCodeItemBinding binding = PromoCodeItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoCodeAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return promoCodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PromoCodeItemBinding binding;
        public ViewHolder(@NonNull PromoCodeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {

        }
    }
}
