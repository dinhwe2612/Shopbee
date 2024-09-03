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
            binding.textView.setText(String.valueOf(promoCodeList.get(position).getPercent()));
            binding.textView2.setText(String.valueOf(promoCodeList.get(position).getName()));
            binding.textView4.setText(String.valueOf(promoCodeList.get(position).getCode()));
            if (promoCodeList.get(position).equals(currentItem)) {
                binding.textView3.setText("Discard");
                binding.textView3.setTextAppearance(R.style.Red_Button_White_Text);
                binding.textView3.setBackgroundResource(R.drawable.rounded_red_rectangle);
            }
            else {
                binding.textView3.setText("Apply");
                binding.textView3.setTextAppearance(R.style.White_Button_Black_Stroke_Black_Text);
                binding.textView3.setBackgroundResource(R.drawable.rounded_white_rectangle_black_stroke);
            }
            binding.textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (promoCodeList.get(position).equals(currentItem)) {
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
