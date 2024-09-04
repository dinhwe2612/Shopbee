package com.example.shopbee.ui.user_search.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.SearchSuggestionBinding;
import com.example.shopbee.databinding.ShopItemBinding;
import com.example.shopbee.generated.callback.OnClickListener;
import com.example.shopbee.ui.search.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {
    public interface OnSearchClickListener {
        void onSearchClick(String query);
    }
    OnSearchClickListener onSearchClickListener;

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }

    List<String> suggestions = new ArrayList<>();

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
        Log.d("Suggestions", suggestions.toString());
    }

    @NonNull
    @Override
    public SuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchSuggestionBinding binding = SearchSuggestionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchClickListener.onSearchClick(suggestions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchSuggestionBinding binding;
        public ViewHolder(@NonNull SearchSuggestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView.setText(suggestions.get(position));
        }
    }
}
