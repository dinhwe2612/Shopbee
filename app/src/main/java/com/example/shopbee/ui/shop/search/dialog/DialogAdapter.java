package com.example.shopbee.ui.shop.search.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.filter.SortByChoice;
import com.example.shopbee.databinding.ShopItemBinding;
import com.example.shopbee.databinding.SortItemBinding;
import com.example.shopbee.ui.shop.search.adapter.ProductAdapter;

import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {
    int currentPosition = 0;
    public interface OnSortByChoiceSelectedListener {
        void onSortByChoiceSelected(SortByChoice sortByChoice);
    }
    OnSortByChoiceSelectedListener onSortByChoiceSelectedListener;
    ArrayList<SortByChoice> sort_by_choices;
    public void setOnSortByChoiceSelectedListener(OnSortByChoiceSelectedListener onSortByChoiceSelectedListener) {
        this.onSortByChoiceSelectedListener = onSortByChoiceSelectedListener;
    }
//    public DialogAdapter(ArrayList<SortByChoice> sort_by_choices) {
//        this.sort_by_choices = sort_by_choices;
//    }
    public DialogAdapter() {
        sort_by_choices = new ArrayList<>();
        sort_by_choices.add(SortByChoice.RELEVANCE);
        sort_by_choices.add(SortByChoice.NEWEST);
        sort_by_choices.add(SortByChoice.HIGHEST_PRICE);
        sort_by_choices.add(SortByChoice.LOWEST_PRICE);
        sort_by_choices.add(SortByChoice.BEST_SELLERS);
        sort_by_choices.add(SortByChoice.REVIEWS);
    }
    @NonNull
    @Override
    public DialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SortItemBinding binding = SortItemBinding.inflate(inflater, parent, false);
        return new DialogAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(currentPosition);
                currentPosition = position;
                notifyItemChanged(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sort_by_choices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SortItemBinding binding;
        public ViewHolder(@NonNull SortItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView.setText(String.valueOf(sort_by_choices.get(position)));
            if (position == currentPosition) {
                binding.textView.setTextAppearance(R.style.SortOptionSelected);
                binding.textView.setBackgroundColor(binding.textView.getContext().getResources().getColor(R.color.red));
            }
            else {
                binding.textView.setTextAppearance(R.style.SortOptionUnselected);
                binding.textView.setBackgroundColor(binding.textView.getContext().getResources().getColor(R.color.white_light_theme));
            }
        }
    }
}
