package com.example.shopbee.utils;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public final class BindingUtils {
    private BindingUtils() {}
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }
    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resourceId) {
        imageView.setImageResource(resourceId);
    }
    @BindingAdapter("textAppearance")
    public static void setTextAppearance(TextView textView, int resource) {
        textView.setTextAppearance(resource);
    }
    @BindingAdapter("loadAnimation")
    public static void setLoadAnimation(TextView textView, int resource) {
        textView.startAnimation(AnimationUtils.loadAnimation(textView.getContext(), resource));
    }
}
