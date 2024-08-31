package com.example.shopbee.data.model.api;

import java.util.List;

public class OrderResponse {
    private String date;
    private int quantity;
    private String status;
    private String order_number;
    private String tracking_number;
    private List<OrderDetailResponse> order_detail;

    public OrderResponse(){
    }
    public OrderResponse(String date, int quantity, String status, String order_number, String tracking_number, List<OrderDetailResponse> order_detail) {
        this.date = date;
        this.quantity = quantity;
        this.status = status;
        this.order_number = order_number;
        this.tracking_number = tracking_number;
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
    public String getTotal_amount(){
        float total = 0;
        for (OrderDetailResponse orderDetailResponse : order_detail){
            String numericString = orderDetailResponse.getPrice().replace("$", "");
            float price = Float.parseFloat(numericString);
            total += price * orderDetailResponse.getQuantity();
        }
        return "$" + String.valueOf(total);
    }
}

