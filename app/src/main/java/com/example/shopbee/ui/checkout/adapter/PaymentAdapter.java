package com.example.shopbee.ui.checkout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PaymentResponse;
import com.example.shopbee.databinding.CountryItemBinding;
import com.example.shopbee.databinding.PaymentItemBinding;
import com.example.shopbee.ui.profile.adapter.CountryAdapter;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private List<PaymentResponse> listPaymentResponse;
    private int currentPosition;
    public interface Listener{
        void onClickItems(int position);
    }
    private Listener listener;
    public PaymentAdapter(Listener listener, List<PaymentResponse> listPaymentResponse, int currentPosition) {
        this.listener = listener;
        this.listPaymentResponse = listPaymentResponse;
        this.currentPosition = currentPosition;
    }
    @NonNull
    @Override
    public PaymentAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaymentItemBinding binding = PaymentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PaymentAdapter.PaymentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.PaymentViewHolder holder, int position) {
        PaymentResponse paymentResponse = listPaymentResponse.get(position);
        switch (paymentResponse.getType()){
            case "shopbee":
                holder.binding.layoutCard.setVisibility(View.GONE);
                holder.binding.layoutWallet.setVisibility(View.VISIBLE);
                break;
            case "visa":
                holder.binding.paymentIcon.setBackgroundResource(R.drawable.visa);
                holder.binding.cardName.setText(paymentResponse.getName());
                holder.binding.cardNumber.setText("* * * *  * * * *  * * * *  " + paymentResponse.getNumber().substring(12, 15));
                holder.binding.expiryDate.setText(paymentResponse.getExpiryDate());
                break;
            case "master":
                holder.binding.paymentIcon.setBackgroundResource(R.drawable.master);
                holder.binding.cardName.setText(paymentResponse.getName());
                holder.binding.cardNumber.setText("* * * *  * * * *  * * * *  " + paymentResponse.getNumber().substring(12, 15));
                holder.binding.expiryDate.setText(paymentResponse.getExpiryDate());
                break;
        }
        holder.binding.checkBoxCard.setChecked(currentPosition == position, false);
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
    }

    @Override
    public int getItemCount() {
        return listPaymentResponse.size();
    }
    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        PaymentItemBinding binding;
        public PaymentViewHolder(@NonNull PaymentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
