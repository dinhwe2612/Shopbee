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
            "Refrigerators & Freezers",
            "Laundry Appliances",
            "Ranges, Ovens & Cooktops",
            "Microwave Ovens",
            "Dishwashers",
            "Small Kitchen Appliances",
            "Heating, Cooling & Air Quality",
            "Vacuums & Floor Care",
            "Garbage Disposals & Compactors"
    ));
    List<String> categoryImageUrl = new ArrayList<>(Arrays.asList(
            "https://m.media-amazon.com/images/I/71-fHgdoOiL.jpg",
            "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcQKdozrWx9puvplBhpgh-yA3DWNUPYDHI0tmEVo-U8At7D1dS41kPDABEdh-zmPEpO05PsfLpKZ9-l0xypKIRPP-8BKOS-eQJGNO6nDhia3YxSL-rXOEP2IYg&usqp=CAE",
            "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcT9VN7DohBlt7xldBlB5eUHfI7Xykxgcv255rKmbmRP3uY9tB22Dbcvn68z2LmeXC-Wgppud9BddHDE1sR59AJ2jxcm9SwAZOvUTIJrDtBC&usqp=CAE",
            "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcR-z91RFf2xvCUREulS_ZvGmWuSXqRa95TJ-sPUxOhOzYYk5YI8B52v85SaVtK--9ldEjj4UejV57STmdsYyvk8srzzApR54OOQQQ21qxTUuKhGFCt3UJTfzg&usqp=CAE",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQijG5pp4cBUTT7_mKy1Ytv3s47J6rHmXW4FA&s",
            "https://m.media-amazon.com/images/I/41Rz9mSUBaL._SR290,290_.jpg",
            "https://images-na.ssl-images-amazon.com/images/G/01/img18/home/Harmony/Nav_Tiles/SmallAppliances/Nav1._CB476888166_.jpg",
            "https://m.media-amazon.com/images/G/01/img22/Storefronts/HeatingCoolingAir/HMT_HeatingCoolingAir_Storefront2024_ShopTop_AirPurifiers_432x432._CB582873206_UC290,290_.jpg",
            "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/817742a4-be7a-44a1-a3f2-b5a5123ddcd4._SL480_.png",
            "https://m.media-amazon.com/images/I/71YX6If9RcL._AC_UF894,1000_QL80_.jpg"
    ));
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
