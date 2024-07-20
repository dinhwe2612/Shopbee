package com.example.shopbee.testUI.CategoriesBarAdapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.impl.categoriesbar.CategoriesBarUserReactionImplementation;

public class CategoriesBarAdapter extends RecyclerView.Adapter<CategoriesBarViewHolder> {
    View animatedLine;
    CategoriesBarUserReactionImplementation impl;
    String[] categories = {"Women", "Men", "Kids", "Accessories"};

    public CategoriesBarAdapter(View animatedLine) {
        this.animatedLine = animatedLine;
        impl = new CategoriesBarUserReactionImplementation();
    }

    @NonNull
    @Override
    public CategoriesBarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesBarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesBarViewHolder holder, int position) {
        holder.textView.setText(categories[position]);
        if (position == impl.position) {
            impl.UISelected(holder.textView);
            animateAfterLayout(holder.textView);
        } else {
            impl.UIUnselected(holder.textView);
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processClick(position, holder.textView);
            }
        });
    }

    private void animateAfterLayout(final TextView tag) {
        tag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tag.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                impl.animateLine(tag, animatedLine);
            }
        });
    }

    void processClick(int position, TextView view) {
        notifyItemChanged(impl.position);
        impl.position = position;
        notifyItemChanged(position);
        // Delay the animation slightly to ensure the layout is complete
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }
}
