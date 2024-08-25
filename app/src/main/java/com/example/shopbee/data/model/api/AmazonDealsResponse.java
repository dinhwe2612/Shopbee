package com.example.shopbee.data.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class AmazonDealsResponse {
    Data data;
    public static class Data {
        List<Deal> deals;
        public static class Deal {
            String deal_id;
            String deal_type;
            String deal_title;
            String deal_photo;
            String deal_badge;
            String product_asin;

            public String getDeal_id() {
                return deal_id;
            }

            public void setDeal_id(String deal_id) {
                this.deal_id = deal_id;
            }

            public String getDeal_type() {
                return deal_type;
            }

            public void setDeal_type(String deal_type) {
                this.deal_type = deal_type;
            }

            public String getDeal_title() {
                return deal_title;
            }

            public void setDeal_title(String deal_title) {
                this.deal_title = deal_title;
            }

            public String getDeal_photo() {
                return deal_photo;
            }

            public void setDeal_photo(String deal_photo) {
                this.deal_photo = deal_photo;
            }

            public String getDeal_badge() {
                return deal_badge;
            }

            public void setDeal_badge(String deal_badge) {
                this.deal_badge = deal_badge;
            }

            public String getProduct_asin() {
                return product_asin;
            }

            public void setProduct_asin(String product_asin) {
                this.product_asin = product_asin;
            }
        }
        public List<Deal> getDeals() {
            return deals;
        }
        public void setDeals(List<Deal> deals) {
            this.deals = deals;
        }
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}