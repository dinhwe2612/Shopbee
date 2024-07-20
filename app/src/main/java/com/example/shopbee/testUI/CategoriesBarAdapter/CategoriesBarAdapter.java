package com.example.shopbee.testUI.CategoriesBarAdapter;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.impl.categoriesbar.CategoriesBarUserReactionImplementation;

public class CategoriesBarAdapter extends RecyclerView.Adapter<CategoriesBarViewHolder> implements ViewTreeObserver.OnGlobalLayoutListener {
    public interface getViewX {
        float getViewX(int position);
    }
    getViewX viewX;
    public void setViewX(getViewX viewX) {
        this.viewX = viewX;
    }
    View animatedLine;
    TextView currentTag;
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
            currentTag = holder.textView;
            impl.UISelected(holder.textView);
            animateAfterLayout(holder.textView);
        } else {
            impl.UIUnselected(holder.textView);
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processClick(position, holder.textView);
//                animateAfterLayout();
            }
        });
    }
    private void animateAfterLayout(TextView view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.d("TAG", "onGlobalLayout: " + view.getWidth());
                impl.animateLine(view, animatedLine, viewX.getViewX(impl.position));
            }
        });
    }

//    private void animateAfterLayout(final TextView tag) {
//        tag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                tag.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                impl.animateLine(tag, animatedLine);
//            }
//        });
//    }
//    private void animateAfterLayout(final TextView tag) {
//        tag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                tag.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                impl.animateLine(tag, animatedLine);
//            }
//        });
//    }
//    public void animateAfterLayout() {
//        currentTag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                currentTag.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                impl.animateLine(currentTag, animatedLine);
//            }
//        });
//    }

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

    @Override
    public void onGlobalLayout() {
//        impl.animateLine(currentTag, animatedLine);
    }
}
