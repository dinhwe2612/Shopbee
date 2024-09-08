package com.example.shopbee.ui.review.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.data.model.api.AmazonProductReviewResponse;
import com.example.shopbee.databinding.ReviewItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> implements ReviewImageAdapter.Listener{
    List<AmazonProductReviewResponse.Data.Review> reviews = new ArrayList<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<ReviewImageAdapter> reviewImageAdapters = new ArrayList<>();

    @Override
    public void showImage(Bitmap bitmap) {
        listener.showImage(bitmap);
    }

    public interface Listener {
        void showImage(Bitmap bitmap);
    }
    Listener listener;
    public ReviewAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(ReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        compositeDisposable.add(Observable.fromCallable(() -> {
                FutureTarget<Bitmap> futureTarget = Glide.with(holder.binding.userAvtar.getContext())
                        .asBitmap()
                        .load(reviews.get(position).getReview_author_avatar())
                        .submit();
                return futureTarget.get();
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(bitmap -> {
                holder.binding.userAvtar.setImageBitmap(bitmap);
            }, throwable -> {
                Log.e("error", throwable.getMessage());
            })
        );
        // bind comment
        holder.binding.content.setText(reviews.get(position).getReview_comment());
        // bind review date
        String regex = "\\b[A-Z][a-z]+ \\d{1,2}, \\d{4}\\b"; // Month Day, Year
        String date = reviews.get(position).getReview_date();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            date = matcher.group(0);
        }
        holder.binding.reviewDate.setText(date);
        // bind images
        holder.binding.imagesRCV.setAdapter(reviewImageAdapters.get(position));
        // bind rating
        String rating = reviews.get(position).getReview_star_rating();
        if (rating == null) rating = "0";
        holder.binding.ratingBar.setRating(Float.parseFloat(rating));
        // bind user name
        holder.binding.userName.setText(reviews.get(position).getReview_author());
        // bind title
        holder.binding.title.setText(reviews.get(position).getReview_title());

        holder.binding.content.setOnClickListener(v->{
            if (holder.binding.content.getMaxLines() == 6) {
                holder.binding.content.setMaxLines(Integer.MAX_VALUE);
            } else {
                holder.binding.content.setMaxLines(6);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addReviews(AmazonProductReviewResponse reviews) {
        int oldSize = this.reviews.size();
        int addSize = reviews.getData().getReviews().size();
        this.reviews.addAll(reviews.getData().getReviews());
        for (int i = 0; i < addSize; i++) {
            reviewImageAdapters.add(new ReviewImageAdapter(this.reviews.get(oldSize + i).getReview_images(), this));
        }
        notifyItemRangeInserted(oldSize, addSize);
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ReviewItemBinding binding;
        public ReviewViewHolder(@NonNull ReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
