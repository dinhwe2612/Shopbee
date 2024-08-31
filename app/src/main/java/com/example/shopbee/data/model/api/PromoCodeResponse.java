package com.example.shopbee.data.model.api;

public class PromoCodeResponse {
    String due_date;
    String name;
    String code;
    public float processPrice(float price) {
        return price;
    }
    public void syncDataFirebase() {

    }
}
