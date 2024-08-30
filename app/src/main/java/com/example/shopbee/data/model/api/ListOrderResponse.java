package com.example.shopbee.data.model.api;

import java.util.List;

public class ListOrderResponse {
    private String email;
    private List<OrderResponse> list_order;

    public ListOrderResponse(){}
    public ListOrderResponse(String email, List<OrderResponse> list_order) {
        this.email = email;
        this.list_order = list_order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderResponse> getList_order() {
        return list_order;
    }

    public void setList_order(List<OrderResponse> list_order) {
        this.list_order = list_order;
    }
}
