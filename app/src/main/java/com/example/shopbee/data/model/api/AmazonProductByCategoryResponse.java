package com.example.shopbee.data.model.api;

import java.util.List;

public class AmazonProductByCategoryResponse {
    Data data;
    public class Data {
        List<Product> products;
        public class Product {
            String asin;
            String product_title;
            String product_price;
            String product_original_price;
            String currency;
            String product_star_rating;
            String product_num_ratings;
            String product_url;
            String product_photo;
            String is_best_seller;

            public String getAsin() {
                return asin;
            }

            public void setAsin(String asin) {
                this.asin = asin;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public String getProduct_price() {
                return product_price;
            }

            public void setProduct_price(String product_price) {
                this.product_price = product_price;
            }

            public String getProduct_original_price() {
                return product_original_price;
            }

            public void setProduct_original_price(String product_original_price) {
                this.product_original_price = product_original_price;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getProduct_star_rating() {
                return product_star_rating;
            }

            public void setProduct_star_rating(String product_star_rating) {
                this.product_star_rating = product_star_rating;
            }

            public String getProduct_num_ratings() {
                return product_num_ratings;
            }

            public void setProduct_num_ratings(String product_num_ratings) {
                this.product_num_ratings = product_num_ratings;
            }

            public String getProduct_url() {
                return product_url;
            }

            public void setProduct_url(String product_url) {
                this.product_url = product_url;
            }

            public String getProduct_photo() {
                return product_photo;
            }

            public void setProduct_photo(String product_photo) {
                this.product_photo = product_photo;
            }

            public String getIs_best_seller() {
                return is_best_seller;
            }

            public void setIs_best_seller(String is_best_seller) {
                this.is_best_seller = is_best_seller;
            }
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
