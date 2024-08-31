package com.example.shopbee.data.model;

public class OrderProductItem {
    private int position;
    private String orderNumber;
    private String date;
    private String trackingNumber;
    private Integer quantity;
    private String totalAmount;
    private String status;

    public OrderProductItem(int position, String orderNumber, String date, String trackingNumber, Integer quantity, String totalAmount, String status) {
        this.position = position;
        this.orderNumber = orderNumber;
        this.date = date;
        this.trackingNumber = trackingNumber;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
}