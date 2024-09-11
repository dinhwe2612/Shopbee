package com.example.shopbee.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.databinding.CategoryhomeItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<String> categoryTitle = new ArrayList<>(Arrays.asList(
            "Recommended for You",
            "Baskets, Bins & Containers",
            "Clothing & Closet Storage",
            "Calculators",
            "Office Lighting",
            "Toys",
            "Desktops",
            "Tablets",
            "Vacuums"
    ));
    List<String> categoryId = new ArrayList<>(Arrays.asList(
            "",
            "17921061011",
            "2423187011",
            "172518",
            "1068956",
            "2975413011",
            "565098",
            "1232597011",
            "3743521"
    ));
    List<String> categoryImageUrl = new ArrayList<>(Arrays.asList(
            "https://m.media-amazon.com/images/I/71-fHgdoOiL.jpg",
            "https://m.media-amazon.com/images/I/71OexVFpBwL._AC_UF894,1000_QL80_.jpg",
            "https://m.media-amazon.com/images/I/71X756iVmiL.jpg",
            "https://m.media-amazon.com/images/I/81gCddxjppL.jpg",
            "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/f1ce050f-bac1-4ee2-8f7a-7aa5868171b9._SL480_.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfVoJ-vuuOkMUhKTWDQlezV3QnvhHpnuHYWQ&s",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRMPvjd_sR0P-zbotAwYxtRZTcKTOWTbxFtA&s",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTU2YyTxCEV5m-ea5-9N8SwUXFyFEPLFjDL6Q&s",
            "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/cce996bc-0ab4-4f5d-a078-b7ba2ddcb579._SL480_.jpg"
    ));
    public interface Listener {
        void onCategoryClick(String categoryId);
    }
    Listener listener;
    public CategoryAdapter(Listener listener) {
        this.listener = listener;
    }
    public static class CategoryHomeList {
        String title;
        String imageUrl;
        public CategoryHomeList(String title, String imageUrl) {
            this.title = title;
            this.imageUrl = imageUrl;
        }
        public String getTitle() {
            return title;
        }
        public String getImageUrl() {
            return imageUrl;
        }
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryhomeItemBinding binding = CategoryhomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.binding.setCh(new CategoryHomeList(categoryTitle.get(position), categoryImageUrl.get(position)));
        if (position != 0) holder.binding.cardView.setOnClickListener(v -> {
            listener.onCategoryClick(categoryId.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return categoryTitle.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public CategoryhomeItemBinding binding;
        public CategoryViewHolder(@NonNull CategoryhomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
