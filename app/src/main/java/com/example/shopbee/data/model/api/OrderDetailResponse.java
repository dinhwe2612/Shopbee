package com.example.shopbee.data.model.api;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailResponse {
    private String product_id;
    private String price;
    private int quantity;
    private String urlImage;
    private List<Pair<String, String>> variation;
    public OrderDetailResponse(){
    }
    public OrderDetailResponse(String product_id, int quantity, String price, String urlImage) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.urlImage = urlImage;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getUrlImage() {
        return urlImage;
    }
    public List<Pair<String, String>> getVariation() {
        return variation;
    }
    public void setVariation(Map<String, String> variationMap) {
        this.variation = new ArrayList<>();
        if (variationMap != null) {
            for (Map.Entry<String, String> entry : variationMap.entrySet()) {
                Pair<String, String> pair = new Pair<>(entry.getKey(), entry.getValue());
                this.variation.add(pair);
            }
        }
    }
    public String getTotalPrice(){
        String numericString = price.replace("$", "");
        float price = Float.parseFloat(numericString);
        return "$" + String.valueOf(price * quantity);
    }
}
