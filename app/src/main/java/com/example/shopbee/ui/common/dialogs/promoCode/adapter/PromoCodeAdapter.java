package com.example.shopbee.ui.common.dialogs.promoCode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.databinding.PromoCodesItemBinding;
import com.example.shopbee.databinding.SortItemBinding;
import com.example.shopbee.ui.common.dialogs.sortbydialog.DialogAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.ViewHolder> {
    public interface OnItemClick {
        void onItemClick(PromoCodeResponse promoCodeResponse);
        void onItemTransportClick(PromoCodeResponse promoCodeResponse);
    }
    OnItemClick onItemClick;
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    int currentItem = -1;
    int currentTransportItem = -1;

    public void setCurrentItem(PromoCodeResponse promoCodeResponse) {
        if (promoCodeResponse == null) return;
        for (int i = 0; i < promoCodeList.size(); i++) {
            if (promoCodeList.get(i).getCode().equals(promoCodeResponse.getCode())) {
                currentItem = i;
                notifyDataSetChanged();
                break;
            }
        }
    }

    public void setCurrentTransportItem(PromoCodeResponse promoCodeResponse) {
        if (promoCodeResponse == null) return;
        for (int i = 0; i < promoCodeList.size(); i++) {
            if (promoCodeList.get(i).getCode().equals(promoCodeResponse.getCode())) {
                currentTransportItem = i;
                notifyDataSetChanged();
                break;
            }
        }
    }
    
    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
//        notifyDataSetChanged();
    }
    public void setCurrentTransportItem(int currentTransportItem) {
        this.currentTransportItem = currentTransportItem;
//        notifyDataSetChanged();
    }
    List<PromoCodeResponse> promoCodeList = new ArrayList<>();
    public void setPromoCodeList(List<PromoCodeResponse> promoCodeList) {
        this.promoCodeList = promoCodeList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PromoCodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PromoCodesItemBinding binding = PromoCodesItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoCodeAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (promoCodeList == null) {
            return 0;
        }
        return promoCodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PromoCodesItemBinding binding;
        public ViewHolder(@NonNull PromoCodesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.dueto.setText(promoCodeList.get(position).getRemainingTime());
            if (promoCodeList.get(position).getRemainingTime().equals("Expired")) {
                binding.typeVoucher.setAlpha(0.45f);
                binding.saveVoucher.setVisibility(View.GONE);
                binding.dueto.setTextAppearance(R.style.Red_Regular_10dp);
            }
            binding.nameVoucher.setText(promoCodeList.get(position).getPercent() + "% OFF");
            switch (promoCodeList.get(position).getName()){
                case "freeship":
                    binding.iconVoucher.setBackgroundResource(R.drawable.shipping_icon);
                    binding.typeVoucher.setText("Freeship");
                    break;
                case "shopbee":
                    binding.iconVoucher.setBackgroundResource(R.drawable.shopbee_voucher_icon);
                    binding.typeVoucher.setText("Shopbee");
                    break;
                case "newbie":
                    binding.iconVoucher.setBackgroundResource(R.drawable.newbie_voucher_icon);
                    binding.typeVoucher.setText("Newbie");
                    break;
            }
            binding.code.setText(String.valueOf(promoCodeList.get(position).getCode()));
            binding.subVoucher.setText(" up to $" + promoCodeList.get(position).getMax_discount());
            if ((currentItem != -1 && promoCodeList.get(position).equals(promoCodeList.get(currentItem))) || (currentTransportItem != -1 && promoCodeList.get(position).equals(promoCodeList.get(currentTransportItem)))) {
                binding.saveVoucher.setText("Discard");
                binding.saveVoucher.setTextAppearance(R.style.White_Button_Black_Stroke_Black_Text);
                binding.saveVoucher.setBackgroundResource(R.drawable.rounded_white_rectangle_black_stroke);
            }
            else {
                binding.saveVoucher.setText("Apply");
                binding.saveVoucher.setTextAppearance(R.style.Red_Button_White_Text);
                binding.saveVoucher.setBackgroundResource(R.drawable.rounded_red_rectangle);
            }
            binding.saveVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if ((currentItem != -1 && promoCodeList.get(position).equals(promoCodeList.get(currentItem))) || (currentTransportItem != -1 && promoCodeList.get(position).equals(promoCodeList.get(currentTransportItem)))) {
                        if (Objects.equals(promoCodeList.get(position).getName(), "freeship")) {
                            onItemClick.onItemTransportClick(null);
                            notifyItemChanged(currentTransportItem);
                            currentTransportItem = -1;
//                            notifyItemChanged(currentTransportItem);
                        } else {
                            onItemClick.onItemClick(null);
                            notifyItemChanged(currentItem);
                            currentItem = -1;
//                            notifyItemChanged(currentItem);
                        }
                    }
                    else {
                        if (Objects.equals(promoCodeList.get(position).getName(), "freeship")) {
                            onItemClick.onItemTransportClick(promoCodeList.get(position));
                            if (currentTransportItem != -1) notifyItemChanged(currentTransportItem);
                            currentTransportItem = position;
                            notifyItemChanged(currentTransportItem);
                        } else {
                            onItemClick.onItemClick(promoCodeList.get(position));
                            if (currentItem != -1) notifyItemChanged(currentItem);
                            currentItem = position;
                            notifyItemChanged(currentItem);
                        }
                    }
                }
            });
        }
    }
}
