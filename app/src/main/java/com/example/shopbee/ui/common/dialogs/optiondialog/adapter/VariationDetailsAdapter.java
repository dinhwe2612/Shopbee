package com.example.shopbee.ui.common.dialogs.optiondialog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.databinding.VariationDetailsItemBinding;

import java.util.ArrayList;
import java.util.List;

public class VariationDetailsAdapter extends RecyclerView.Adapter<VariationDetailsAdapter.VariationDetailsViewHolder> {
    interface Listener {
        void onClick(int posPar, int posChild, boolean isUnselected);
    }
    Listener listener;
    List<AmazonProductDetailsResponse.Data.VariationDetail> variationDetails = new ArrayList<>();
    int curPos = -1;
    int posPar;
    public VariationDetailsAdapter(Listener listener, int posPar, List<AmazonProductDetailsResponse.Data.VariationDetail> variationDetails) {
        this.variationDetails = variationDetails;
        this.listener = listener;
        this.posPar = posPar;
    }
    @NonNull
    @Override
    public VariationDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VariationDetailsViewHolder(VariationDetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VariationDetailsViewHolder holder, int position) {
        holder.binding.content.setText(variationDetails.get(position).getValue());
        if (position == curPos) {
            holder.binding.selector.setVisibility(View.VISIBLE);
        } else {
            holder.binding.selector.setVisibility(View.GONE);
        }
        holder.binding.button.setOnClickListener(v->{
            int oldPos = curPos;
            curPos = position;
            boolean isUnselected = (oldPos == curPos);
            listener.onClick(posPar, curPos, isUnselected);
            if (isUnselected) {
                curPos = -1;
                notifyItemChanged(oldPos);
            } else {
                notifyItemChanged(oldPos);
                notifyItemChanged(curPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return variationDetails.size();
    }

    public static class VariationDetailsViewHolder extends RecyclerView.ViewHolder {
        VariationDetailsItemBinding binding;
        public VariationDetailsViewHolder(@NonNull VariationDetailsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
