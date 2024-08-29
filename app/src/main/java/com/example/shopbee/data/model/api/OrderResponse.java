package com.example.shopbee.data.model.api;

import java.util.List;

public class OrderResponse {
    private String user_name;
    private String date;
    private int quantity;
    private String status;
    private List<OrderDetailResponse> listOrderDetail;

    public OrderResponse(){
    }
    public OrderResponse(String user_name, String date, int quantity, String status, List<OrderDetailResponse> listOrderDetail) {
        this.user_name = user_name;
        this.date = date;
        this.quantity = quantity;
        this.status = status;
        this.listOrderDetail = listOrderDetail;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<OrderDetailResponse> getListOrderDetail() {
        return listOrderDetail;
    }

    public void setListOrderDetail(List<OrderDetailResponse> listOrderDetail) {
        this.listOrderDetail = listOrderDetail;
    }
}
