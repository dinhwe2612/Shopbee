package com.example.shopbee.ui.checkout.adapter;

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
    private String fullName;
    public interface Listener{
        void onClickItems(int position);
        void onEditItems(int position);
    }
    private Listener listener;
    public ShippingAdapter(Listener listener, List<AddressResponse> addressResponses, int currentPosition, String fullName) {
        this.listener = listener;
        this.addressResponses = addressResponses;
        this.currentPosition = currentPosition;
        this.fullName = fullName;
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
        holder.binding.fullName.setText(fullName);
        holder.binding.address.setText(addressResponse.toString());
        if (position == currentPosition){
            holder.binding.checkBoxCard.setChecked(true, true);
        } else {
            holder.binding.checkBoxCard.setChecked(false, true);
        }
        holder.binding.checkBoxCard.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                notifyItemChanged(currentPosition);
                currentPosition = position;
                notifyItemChanged(currentPosition);
                listener.onClickItems(position);
            }
        });
        holder.binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditItems(position);
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
