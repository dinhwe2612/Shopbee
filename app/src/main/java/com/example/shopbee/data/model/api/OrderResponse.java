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
    private String address;
    private String discount;
    private String freeship;
    private List<OrderDetailResponse> order_detail;

    public OrderResponse(){
    }
    public OrderResponse(String date, int quantity, String status, String order_number, String tracking_number, String payment, String address, String discount, List<OrderDetailResponse> order_detail, String freeship) {
        this.date = date;
        this.quantity = quantity;
        this.status = status;
        this.order_number = order_number;
        this.tracking_number = tracking_number;
        this.payment = payment;
        this.address = address;
        this.discount = discount;
        this.order_detail = order_detail;
        this.freeship = freeship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFreeship() {
        return freeship;
    }

    public void setFreeship(String freeship) {
        this.freeship = freeship;
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
        float total = 10;
        for (OrderDetailResponse orderDetailResponse : order_detail) {
            String numericString = orderDetailResponse.getPrice().replace("$", "");
            float price = Float.parseFloat(numericString);
            total += price * orderDetailResponse.getQuantity();
        }
        if (!discount.isEmpty()){
            total = total - Float.parseFloat(discount.replace("$", ""));
        }
        if (!freeship.isEmpty()){
            total = total - Float.parseFloat(freeship.replace("$", ""));
        }
        return roundToTwoDecimalPlaces(total) + "$";
    }
    protected OrderResponse(Parcel in) {
        date = in.readString();
        quantity = in.readInt();
        status = in.readString();
        order_number = in.readString();
        tracking_number = in.readString();
        payment = in.readString();
        address = in.readString();
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
        dest.writeString(address);
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
    public static float roundToTwoDecimalPlaces(float value) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}

