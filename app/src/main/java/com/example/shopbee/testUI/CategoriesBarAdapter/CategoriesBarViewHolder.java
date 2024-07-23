package com.example.shopbee.testUI.CategoriesBarAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.impl.categoriesbar.CategoriesBarUserReactionImplementation;

public class CategoriesBarViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public CategoriesBarViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }

}
