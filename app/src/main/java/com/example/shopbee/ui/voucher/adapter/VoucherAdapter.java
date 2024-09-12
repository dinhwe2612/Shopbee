package com.example.shopbee.ui.voucher.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;

import com.example.shopbee.databinding.VoucherItemBinding;

import java.util.List;
import java.util.Objects;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private List<PromoCodeResponse> promoCodeResponseList;
    private List<PromoCodeResponse> promoCodeOfUser;
    private String email;
    public VoucherAdapter(Listener listener, List<PromoCodeResponse> promoCodeResponseList, String email, List<PromoCodeResponse> promoCodeOfUser){
        this.promoCodeResponseList = promoCodeResponseList;
        this.listener = listener;
        this.email = email;
        this.promoCodeOfUser = promoCodeOfUser;
    }
    public interface Listener{
        void onSaveVoucherFreeShip(int position);
        void onSaveVoucherShopbee(int position);
        void onSaveVoucherNewbie(int position);
    }
    private Listener listener;
    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VoucherItemBinding binding = VoucherItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VoucherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.VoucherViewHolder holder, int position) {
        PromoCodeResponse voucherResponse = promoCodeResponseList.get(position);
        switch (voucherResponse.getName()){
            case "freeship":
                holder.binding.iconVoucher.setBackgroundResource(R.drawable.shipping_icon);
                holder.binding.typeVoucher.setText("Freeship");
                break;
            case "shopbee":
                holder.binding.iconVoucher.setBackgroundResource(R.drawable.shopbee_voucher_icon);
                holder.binding.typeVoucher.setText("Shopbee");
                break;
            case "newbie":
                holder.binding.iconVoucher.setBackgroundResource(R.drawable.newbie_voucher_icon);
                holder.binding.typeVoucher.setText("Newbie");
                break;
        }
        holder.binding.nameVoucher.setText(voucherResponse.getPercent() + "% OFF");
        holder.binding.subVoucher.setText(" up to $" + voucherResponse.getMax_discount());
        holder.binding.code.setText(voucherResponse.getCode());
        holder.binding.dueto.setText("Due to: " + voucherResponse.getDue_date().substring(0, 10));
        boolean isSaved = false;
        if (!email.isEmpty()) {
            for (PromoCodeResponse promoCodeResponse : promoCodeOfUser) {
                if (promoCodeResponse.getCode().equals(voucherResponse.getCode())) {
                    isSaved = true;
                    break;
                }
            }
        }
        if (isSaved == true){
            holder.binding.saveVoucher.setText("Saved");
            holder.binding.saveVoucher.setBackgroundResource(R.drawable.rounded_darkred_rectangle);
        } else {
            holder.binding.saveVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (voucherResponse.getName()){
                        case "freeship":
                            listener.onSaveVoucherFreeShip(position);
                            break;
                        case "shopbee":
                            listener.onSaveVoucherShopbee(position);
                            break;
                        case "newbie":
                            listener.onSaveVoucherNewbie(position);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return promoCodeResponseList.size();
    }
    public static class VoucherViewHolder extends RecyclerView.ViewHolder{
        VoucherItemBinding binding;
        public VoucherViewHolder(@NonNull VoucherItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
