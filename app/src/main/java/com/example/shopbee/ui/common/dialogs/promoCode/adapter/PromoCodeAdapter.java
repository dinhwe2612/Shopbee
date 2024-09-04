package com.example.shopbee.ui.common.dialogs.promoCode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.databinding.PromoCodeItemBinding;
import com.example.shopbee.databinding.SortItemBinding;
import com.example.shopbee.ui.common.dialogs.sortbydialog.DialogAdapter;

import java.util.ArrayList;
import java.util.List;

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.ViewHolder> {
    public interface OnItemClick {
        void onItemClick(PromoCodeResponse promoCodeResponse);
    }
    OnItemClick onItemClick;
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    PromoCodeResponse currentItem;
    public void setCurrentItem(PromoCodeResponse currentItem) {
        this.currentItem = currentItem;
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
        PromoCodeItemBinding binding = PromoCodeItemBinding.inflate(inflater, parent, false);
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
        PromoCodeItemBinding binding;
        public ViewHolder(@NonNull PromoCodeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            if (promoCodeList.get(position).getRemainingTime().equals("Expired")) {
                binding.promoCodeLayout.setAlpha(0.45f);
                binding.textView3.setText(promoCodeList.get(position).getRemainingTime());
                binding.textView5.setVisibility(View.GONE);
                binding.textView3.setTextAppearance(R.style.Red_Regular_10dp);
            }
            binding.textView.setText(String.valueOf(promoCodeList.get(position).getPercent()));
            binding.textView2.setText(String.valueOf(promoCodeList.get(position).getName()));
            binding.textView4.setText(String.valueOf(promoCodeList.get(position).getCode()));
            if (currentItem != null && promoCodeList.get(position).equals(currentItem)) {
                binding.textView5.setText("Discard");
                binding.textView5.setTextAppearance(R.style.White_Button_Black_Stroke_Black_Text);
                binding.textView5.setBackgroundResource(R.drawable.rounded_white_rectangle_black_stroke);
            }
            else {
                binding.textView5.setText("Apply");
                binding.textView5.setTextAppearance(R.style.Red_Button_White_Text);
                binding.textView5.setBackgroundResource(R.drawable.rounded_red_rectangle);
            }
            binding.textView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentItem != null && promoCodeList.get(position).equals(currentItem)) {
                        onItemClick.onItemClick(null);
                    }
                    else {
                        onItemClick.onItemClick(promoCodeList.get(position));
                    }
                }
            });
        }
    }
}
