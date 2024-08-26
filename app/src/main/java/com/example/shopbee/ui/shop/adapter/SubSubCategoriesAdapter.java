package com.example.shopbee.ui.shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopbee.databinding.CategoriesShopNewItem2Binding;
import com.example.shopbee.databinding.CategoriesShopNewItem2ItemBinding;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
import com.example.shopbee.ui.shop.tree.CategoriesTree;
import com.example.shopbee.ui.shop.tree.CategoryNode;

import java.io.IOException;
import java.util.ArrayList;

public class SubSubCategoriesAdapter extends RecyclerView.Adapter<SubSubCategoriesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> subSubCategories;
    public SubSubCategoriesAdapter(Context context, String subCategory) throws IOException {
        this.context = context;
        subSubCategories = new ArrayList<>();
        for (CategoryNode subSubCategory: CategoriesTree.getInstance(context).findNode(subCategory).getChildren()) {
            subSubCategories.add(subSubCategory.getId());
        }
    }

    @NonNull
    @Override
    public SubSubCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoriesShopNewItem2ItemBinding binding = CategoriesShopNewItem2ItemBinding.inflate(inflater, parent, false);
        return new SubSubCategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubSubCategoriesAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return subSubCategories.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CategoriesShopNewItem2ItemBinding binding;
        public ViewHolder(@NonNull CategoriesShopNewItem2ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView.setText(CategoriesHashMap.getInstance().getCategories().get(subSubCategories.get(position)));
            Glide.with(binding.imageView.getContext()).load(CategoriesHashMap.getInstance().getCategoriesLink(subSubCategories.get(position))).into(binding.imageView);
        }
    }
}
