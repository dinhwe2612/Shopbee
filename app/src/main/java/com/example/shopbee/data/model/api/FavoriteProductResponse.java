package com.example.shopbee.data.model.api;

import java.util.List;

public class FavoriteProductResponse {
    private String user_name;
    private List<OrderDetailResponse> listFavoriteProduct;
    public FavoriteProductResponse(){
    }
    public FavoriteProductResponse(String user_name, List<OrderDetailResponse> listFavoriteProduct) {
        this.user_name = user_name;
        this.listFavoriteProduct = listFavoriteProduct;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<OrderDetailResponse> getListFavoriteProduct() {
        return listFavoriteProduct;
    }

    public void setListFavoriteProduct(List<OrderDetailResponse> listFavoriteProduct) {
        this.listFavoriteProduct = listFavoriteProduct;
    }
}
