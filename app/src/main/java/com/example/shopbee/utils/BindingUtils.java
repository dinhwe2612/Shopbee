package com.example.shopbee.utils;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public final class BindingUtils {
    private BindingUtils() {}
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }
    @BindingAdapter("imageUrl")
    public static void setImageResource(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
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
