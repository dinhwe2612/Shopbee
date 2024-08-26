package com.example.shopbee.data.model.api;

import java.util.List;

public class AmazonProductByCategoryResponse {
    Data data;
    public class Data {
        List<Product> productList;
        public class Product {
            String product_photo;
            public String getProduct_photo() {
                return product_photo;
            }

            public void setProduct_photo(String product_photo) {
                this.product_photo = product_photo;
            }
        }
    }
}
