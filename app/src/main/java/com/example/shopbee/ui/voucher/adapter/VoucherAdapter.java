package com.example.shopbee.ui.voucher.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;

import com.example.shopbee.databinding.VoucherItemBinding;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private List<PromoCodeResponse> promoCodeResponseList;
    private String email;
    public VoucherAdapter(Listener listener, List<PromoCodeResponse> promoCodeResponseList, String email){
        this.promoCodeResponseList = promoCodeResponseList;
        this.listener = listener;
        this.email = email;
    }
    public interface Listener{
        void onSaveVoucher(int position);
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
                holder.binding.typeOfVoucher.setBackgroundResource(R.drawable.freeship_item);
                break;
            case "shopbee":
                holder.binding.typeOfVoucher.setBackgroundResource(R.drawable.voucher_item);
                break;
            case "newbie":
                holder.binding.typeOfVoucher.setBackgroundResource(R.drawable.newbie_item);
                break;
        }
        holder.binding.nameVoucher.setText(voucherResponse.getPercent() + "% OFF");
        holder.binding.subVoucher.setText(" up to $" + voucherResponse.getMax_discount());
        holder.binding.code.setText(voucherResponse.getCode());
        holder.binding.dueto.setText("Due to: " + voucherResponse.getDue_date());
        if (email != ""){
            //Saved or Save
        }
        holder.binding.saveVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSaveVoucher(position);
            }
        });
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
