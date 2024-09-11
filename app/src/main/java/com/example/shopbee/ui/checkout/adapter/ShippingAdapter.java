package com.example.shopbee.ui.checkout.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.example.shopbee.data.model.api.AddressResponse;
import com.example.shopbee.databinding.PaymentItemBinding;
import com.example.shopbee.databinding.ShippingItemBinding;

import java.util.List;

public class ShippingAdapter extends RecyclerView.Adapter<ShippingAdapter.ShippingViewHolder>{
    private List<AddressResponse> addressResponses;
    private int currentPosition;
    public interface Listener{
        void onClickItems(int position);
        void onEditItems(int position);
        void onClickDeleteItems(int position);
    }
    private Listener listener;
    public ShippingAdapter(Listener listener, List<AddressResponse> addressResponses, int currentPosition) {
        this.listener = listener;
        this.addressResponses = addressResponses;
        this.currentPosition = currentPosition;
    }
    @NonNull
    @Override
    public ShippingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShippingItemBinding binding = ShippingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShippingViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ShippingViewHolder holder, int position) {
        AddressResponse addressResponse = addressResponses.get(position);
        holder.binding.fullName.setText(addressResponse.getName());
        holder.binding.address.setText(addressResponse.toString());
        if (position == currentPosition){
            holder.binding.checkBoxCard.setChecked(true, false);
        } else {
            holder.binding.checkBoxCard.setChecked(false, false);
        }
        holder.binding.checkBoxCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.binding.checkBoxCard.isChecked()){
                    holder.binding.checkBoxCard.setChecked(false, true);
                    currentPosition = -1;
                    listener.onClickItems(currentPosition);
                    notifyDataSetChanged();
                } else {
                    holder.binding.checkBoxCard.setChecked(true, true);
                    currentPosition = position;
                    listener.onClickItems(currentPosition);
                    notifyDataSetChanged();
                }
            }
        });
        holder.binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditItems(position);
            }
        });
        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDeleteItems(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressResponses.size();
    }
    public class ShippingViewHolder extends RecyclerView.ViewHolder {
        ShippingItemBinding binding;
        public ShippingViewHolder(@NonNull ShippingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
