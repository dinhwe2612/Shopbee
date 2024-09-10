package com.example.shopbee.ui.my_vouchers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.databinding.MyVouchersBinding;
import com.example.shopbee.databinding.MyVouchersItemBinding;
import com.example.shopbee.databinding.VoucherItemBinding;
import com.example.shopbee.ui.voucher.adapter.VoucherAdapter;

import java.util.List;

public class MyVouchersAdapter extends RecyclerView.Adapter<MyVouchersAdapter.ViewHolder> {
    private List<PromoCodeResponse> promoCodeResponseList;

    public void setPromoCodeResponseList(List<PromoCodeResponse> promoCodeResponseList) {
        this.promoCodeResponseList = promoCodeResponseList;
    }

    @NonNull
    @Override
    public MyVouchersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyVouchersItemBinding binding = MyVouchersItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVouchersAdapter.ViewHolder holder, int position) {
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
        holder.binding.dueto.setText("Valid until " + voucherResponse.getDue_date());
    }

    @Override
    public int getItemCount() {
        if (promoCodeResponseList == null) {
            return 0;
        }
        return promoCodeResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyVouchersItemBinding binding;
        public ViewHolder(@NonNull MyVouchersItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
