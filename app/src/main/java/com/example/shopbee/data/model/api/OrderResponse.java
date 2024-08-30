package com.example.shopbee.data.model.api;

import java.util.List;

public class OrderResponse {
    private String date;
    private int quantity;
    private String status;
    private List<OrderDetailResponse> order_detail;

    public OrderResponse(){
    }
    public OrderResponse(String date, int quantity, String status, List<OrderDetailResponse> order_detail) {
        this.date = date;
        this.quantity = quantity;
        this.status = status;
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

    public List<OrderDetailResponse> getListOrderDetail() {
        return order_detail;
    }

    public void setListOrderDetail(List<OrderDetailResponse> order_detail) {
        this.order_detail = order_detail;
    }
}
