package com.example.shopbee.data.model.api;

public class OrderDetailResponse {
    private String product_id;
    private String price;
    private int quantity;
    private String urlImage;

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
}
