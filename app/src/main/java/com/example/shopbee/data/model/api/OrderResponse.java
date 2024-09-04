package com.example.shopbee.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderResponse implements Parcelable {
    private String date;
    private int quantity;
    private String status;
    private String order_number;
    private String tracking_number;
    private String payment;
    private String discount;
    private List<OrderDetailResponse> order_detail;

    public OrderResponse(){
    }
    public OrderResponse(String date, int quantity, String status, String order_number, String tracking_number, String payment, String discount, List<OrderDetailResponse> order_detail) {
        this.date = date;
        this.quantity = quantity;
        this.status = status;
        this.order_number = order_number;
        this.tracking_number = tracking_number;
        this.payment = payment;
        this.discount = discount;
        this.order_detail = order_detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public List<OrderDetailResponse> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetailResponse> order_detail) {
        this.order_detail = order_detail;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getOrderPrice(){
        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderDetailResponse orderDetailResponse : order_detail) {
            String numericString = orderDetailResponse.getPrice().replace("$", "");
            BigDecimal price = new BigDecimal(numericString);
            total = total.add(price.multiply(BigDecimal.valueOf(orderDetailResponse.getQuantity())));
        }
        return total.setScale(2, RoundingMode.HALF_UP).toString() + "$";
    }
    public String getTotal_amount() {
        BigDecimal total = BigDecimal.valueOf(10);
        for (OrderDetailResponse orderDetailResponse : order_detail) {
            String numericString = orderDetailResponse.getPrice().replace("$", "");
            BigDecimal price = new BigDecimal(numericString);
            total = total.add(price.multiply(BigDecimal.valueOf(orderDetailResponse.getQuantity())));
        }
        if (discount.isEmpty()) {
            return total.setScale(2, RoundingMode.HALF_UP).toString() + "$";
        }

        BigDecimal discountAmount = new BigDecimal(this.discount.replace("$", ""));
        BigDecimal finalAmount = total.subtract(discountAmount);

        return finalAmount.setScale(2, RoundingMode.HALF_UP).toString() + "$";
    }
    protected OrderResponse(Parcel in) {
        date = in.readString();
        quantity = in.readInt();
        status = in.readString();
        order_number = in.readString();
        tracking_number = in.readString();
        payment = in.readString();
        discount = in.readString();
        order_detail = in.createTypedArrayList(OrderDetailResponse.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeInt(quantity);
        dest.writeString(status);
        dest.writeString(order_number);
        dest.writeString(tracking_number);
        dest.writeString(payment);
        dest.writeString(discount);
        dest.writeTypedList(order_detail);
    }
    public static final Creator<OrderResponse> CREATOR = new Creator<OrderResponse>() {
        @Override
        public OrderResponse createFromParcel(Parcel in) {
            return new OrderResponse(in);
        }

        @Override
        public OrderResponse[] newArray(int size) {
            return new OrderResponse[size];
        }
    };
}

