package com.example.shopbee.ui.shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.CategoriesShopNewItem1Binding;
import com.example.shopbee.databinding.CategoriesShopNewItem2Binding;
import com.example.shopbee.ui.shop.CustomLinearLayoutManager.CustomLinearLayoutManager;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
import com.example.shopbee.ui.shop.tree.CategoriesTree;
import com.example.shopbee.ui.shop.tree.CategoryNode;

import java.io.IOException;
import java.util.ArrayList;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private Context context;
    private ArrayList<String> subCategories;
    public SubCategoriesAdapter(Context context) throws IOException {
        this.context = context;
        subCategories = new ArrayList<>();
        for (CategoryNode subCategory: CategoriesTree.getInstance(context).getHead().getChildren().get(0).getChildren()) {
            subCategories.add(subCategory.getId());
        }
    }
    public void notifyCategoryChanged(String category) throws IOException {
        subCategories.clear();
        for (CategoryNode subCategory: CategoriesTree.getInstance(context).findNode(category).getChildren()) {
            subCategories.add(subCategory.getId());
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SubCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoriesShopNewItem2Binding binding = CategoriesShopNewItem2Binding.inflate(inflater, parent, false);
        return new SubCategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoriesAdapter.ViewHolder holder, int position) {
        try {
            holder.bindView(position);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CategoriesShopNewItem2Binding binding;
        public ViewHolder(@NonNull CategoriesShopNewItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) throws IOException {
            binding.textView.setText(CategoriesHashMap.getInstance().getCategories().get(subCategories.get(position)));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            linearLayoutManager.setInitialPrefetchItemCount(CategoriesTree.getInstance(context).findNode(subCategories.get(position)).getChildren().size());

            SubSubCategoriesAdapter subSubCategoriesAdapter = new SubSubCategoriesAdapter(context, subCategories.get(position));
            binding.recyclerView.setLayoutManager(linearLayoutManager);
            binding.recyclerView.setAdapter(subSubCategoriesAdapter);
//            binding.recyclerView.setRecycledViewPool(viewPool);

//            binding.recyclerView.setLayoutManager(new CustomLinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));

//            binding.recyclerView.setAdapter(new SubSubCategoriesAdapter(context, subCategories.get(position)));
        }
    }
}
