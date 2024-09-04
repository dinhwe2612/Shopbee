package com.example.shopbee.ui.common.dialogs.writereivewdialog;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WriteReviewEvent {
    int starRating;
    String reviewTitle;
    String reviewContent;
    List<Bitmap> reviewImages;
    String reviewDate; // Format: "yyyy-MM-dd HH:mm:ss"
    WriteReviewEvent(int starRating, String reviewTitle, String reviewContent, List<Bitmap> reviewImages) {
        this.starRating = starRating;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewImages = reviewImages;
        this.reviewDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public List<Bitmap> getReviewImages() {
        return reviewImages;
    }

    public void setReviewImages(List<Bitmap> reviewImages) {
        this.reviewImages = reviewImages;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
