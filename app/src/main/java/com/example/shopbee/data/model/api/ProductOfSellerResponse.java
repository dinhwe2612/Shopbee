package com.example.shopbee.data.model.api;

import java.util.List;

public class ProductOfSellerResponse {
    private String seller_id;
    private List<OrderDetailResponse> listOrderDetail;

    public ProductOfSellerResponse(){
    }
    public ProductOfSellerResponse(String seller_id, List<OrderDetailResponse> listOrderDetail) {
        this.seller_id = seller_id;
        this.listOrderDetail = listOrderDetail;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public List<OrderDetailResponse> getListOrderDetail() {
        return listOrderDetail;
    }

    public void setListOrderDetail(List<OrderDetailResponse> listOrderDetail) {
        this.listOrderDetail = listOrderDetail;
    }
}
