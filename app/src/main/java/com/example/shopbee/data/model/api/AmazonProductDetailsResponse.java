package com.example.shopbee.data.model.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AmazonProductDetailsResponse {
    public static class Data {
        String asin;
        String product_title;
        String product_price;
        String product_original_price;
        String product_byline;
        String product_star_rating;
        Integer product_num_ratings;
        String product_url;
        String product_photo;
        String product_availability;
        List<String> about_product = new ArrayList<>();
        String product_description;
        HashMap<String, String> product_information = new HashMap<>();
        List<String> product_photos = new ArrayList<>();
        HashMap<String, String> product_details = new HashMap<>();
        String customers_say;
        class Category {
            String id;
            String name;
            String link;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
        List<Category> category_path = new ArrayList<>();
        HashMap<String, List<String>> product_variations = new HashMap<>();

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

        public String getProduct_byline() {
            return product_byline;
        }

        public void setProduct_byline(String product_byline) {
            this.product_byline = product_byline;
        }

        public String getProduct_star_rating() {
            return product_star_rating;
        }

        public void setProduct_star_rating(String product_star_rating) {
            this.product_star_rating = product_star_rating;
        }

        public Integer getProduct_num_ratings() {
            return product_num_ratings;
        }

        public void setProduct_num_ratings(Integer product_num_ratings) {
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

        public String getProduct_availability() {
            return product_availability;
        }

        public void setProduct_availability(String product_availability) {
            this.product_availability = product_availability;
        }

        public List<String> getAbout_product() {
            return about_product;
        }

        public void setAbout_product(List<String> about_product) {
            this.about_product = about_product;
        }

        public String getProduct_description() {
            return product_description;
        }

        public void setProduct_description(String product_description) {
            this.product_description = product_description;
        }

        public HashMap<String, String> getProduct_information() {
            return product_information;
        }

        public void setProduct_information(HashMap<String, String> product_information) {
            this.product_information = product_information;
        }

        public List<String> getProduct_photos() {
            return product_photos;
        }

        public void setProduct_photos(List<String> product_photos) {
            this.product_photos = product_photos;
        }

        public HashMap<String, String> getProduct_details() {
            return product_details;
        }

        public void setProduct_details(HashMap<String, String> product_details) {
            this.product_details = product_details;
        }

        public String getCustomers_say() {
            return customers_say;
        }

        public void setCustomers_say(String customers_say) {
            this.customers_say = customers_say;
        }

        public List<Category> getCategory_path() {
            return category_path;
        }

        public void setCategory_path(List<Category> category_path) {
            this.category_path = category_path;
        }

        public HashMap<String, List<String>> getProduct_variations() {
            return product_variations;
        }

        public void setProduct_variations(HashMap<String, List<String>> product_variations) {
            this.product_variations = product_variations;
        }
    }
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
