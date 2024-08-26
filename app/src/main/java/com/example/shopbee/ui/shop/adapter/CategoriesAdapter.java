package com.example.shopbee.ui.shop.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.CategoriesShopNewItem1Binding;
import com.example.shopbee.databinding.DealItemBinding;
import com.example.shopbee.ui.home.adapter.DealAdapter;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
import com.example.shopbee.ui.shop.tree.CategoriesTree;
import com.example.shopbee.ui.shop.tree.CategoryNode;

import java.io.IOException;
import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    public interface OnCategoryClickListener {
        void onCategoryClick(String category) throws IOException;
    }
    private OnCategoryClickListener onCategoryClickListener;
    private int currentPosition = 0;
    private Context context;
    private ArrayList<String> categories;
    public CategoriesAdapter(Context context, OnCategoryClickListener onCategoryClickListener) throws IOException {
        this.context = context;
        this.onCategoryClickListener = onCategoryClickListener;
        categories = new ArrayList<>();
        for (CategoryNode categoryNode : CategoriesTree.getInstance(context).getHead().getChildren()) {
            categories.add(categoryNode.getId());
        }
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoriesShopNewItem1Binding binding = CategoriesShopNewItem1Binding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(currentPosition);
                currentPosition = position;
                notifyItemChanged(currentPosition);
                if (onCategoryClickListener != null) {
                    try {
                        onCategoryClickListener.onCategoryClick(categories.get(position));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CategoriesShopNewItem1Binding binding;
        public ViewHolder(@NonNull CategoriesShopNewItem1Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView.setText(CategoriesHashMap.getInstance().getCategories().get(categories.get(position)));
            int[] attrs = new int[] {
                    android.R.attr.src,
            };
            TypedArray typedArray = context.obtainStyledAttributes(CategoriesHashMap.getInstance().getCategoriesImage().get(categories.get(position)), attrs);
            int drawableResourceId = typedArray.getResourceId(0, -1);
            if (drawableResourceId != -1) {
                binding.imageView.setImageResource(drawableResourceId);
            }
            typedArray.recycle();
            if (position == currentPosition) {
                binding.relativeLayout.setBackgroundResource(R.drawable.categories_selected_background);
            } else {
                binding.relativeLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle);
            }
        }
    }
}
