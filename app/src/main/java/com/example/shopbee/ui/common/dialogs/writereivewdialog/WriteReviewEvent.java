package com.example.shopbee.ui.common.dialogs.writereivewdialog;

import android.graphics.Bitmap;

import com.example.shopbee.data.model.api.OrderDetailResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WriteReviewEvent {
    String order_number;

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    OrderDetailResponse orderDetailResponse;
    public void setOrderDetailResponse(OrderDetailResponse orderDetailResponse) {
        this.orderDetailResponse = orderDetailResponse;
    }

    public OrderDetailResponse getOrderDetailResponse() {
        return orderDetailResponse;
    }
    Integer starRating;
    String reviewTitle;
    String reviewContent;
    List<Bitmap> reviewImages;
    String reviewDate; // Format: "yyyy-MM-dd HH:mm:ss"
    public WriteReviewEvent(Integer starRating, String reviewTitle, String reviewContent, List<Bitmap> reviewImages) {
        this.starRating = starRating;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewImages = reviewImages;
        this.reviewDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
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
