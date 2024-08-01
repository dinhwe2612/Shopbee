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

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private List<String> tagItems = Arrays.asList(new String[]{"Summer", "T-shirt", "Winter", "Shirts", "Short", "Jeans"});
    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_favorite, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        String item = tagItems.get(position);
        holder.nameTag.setText(item);
    }
    @Override
    public int getItemCount() {
        return  tagItems.size();
    }
    class TagViewHolder extends RecyclerView.ViewHolder {
        TextView nameTag;
        public TagViewHolder(@NonNull View itemView){
            super(itemView);
            nameTag = itemView.findViewById(R.id.tag_name);
        }
    }
}
