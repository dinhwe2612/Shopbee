package com.example.shopbee.testUI.FavoriteAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;

import java.util.Arrays;
import java.util.List;

public class FavoriteAdapter extends  RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{
    private List<String> favoriteItems = Arrays.asList(new String[]{"Shirt", "Jeans", "Shoes", "Watch", "Bag", "Shoes", "Watch", "Bag", "Shoes", "Watch", "Bag"});
    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        String item = favoriteItems.get(position);
        holder.name.setText(item);
    }
    @Override
    public int getItemCount() {
        return  favoriteItems.size();
    }
    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public FavoriteViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.item_favorite_name);
        }
    }

}
