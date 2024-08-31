package com.example.shopbee.ui.user_search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.CategoriesShopNewItem1Binding;
import com.example.shopbee.databinding.SearchHistoryItemBinding;
import com.example.shopbee.generated.callback.OnClickListener;
import com.example.shopbee.ui.shop.adapter.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    public interface OnHistorySearchClick {
        void onHistorySearchClick(String product_name);
        void onHistoryDeleteClick(String product_name);
    }
    OnHistorySearchClick onHistorySearchClick;
    public void setOnHistorySearchClick(OnHistorySearchClick onHistorySearchClick) {
        this.onHistorySearchClick = onHistorySearchClick;
    }
    List<String> historyList;
    public void setHistoryList(List<String> historyList) {
        this.historyList = historyList;
    }
    public List<String> getHistoryList() {
        return historyList;
    }
    public HistoryAdapter() {
        historyList = new ArrayList<>();
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchHistoryItemBinding binding = SearchHistoryItemBinding.inflate(inflater, parent, false);
        return new HistoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clickAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.button_click_animation);
                holder.itemView.startAnimation(clickAnimation);
                onHistorySearchClick.onHistorySearchClick(historyList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (historyList == null) {
            return 0;
        }
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SearchHistoryItemBinding binding;
        public ViewHolder(@NonNull SearchHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView.setText(historyList.get(position));
            binding.deleteFromList.setOnClickListener(new View.OnClickListener() {
                // delete this string from adapter data and then delete on firebase or vice versa
                @Override
                public void onClick(View view) {
                    onHistorySearchClick.onHistoryDeleteClick(binding.textView.getText().toString());
                }
            });
        }
    }
}
