package com.example.shopbee.data.model.api;

import java.util.List;

public class AmazonBestSellerResponse {
    Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    static public class Data {
        List<BestSeller> best_sellers;

        public List<BestSeller> getBest_sellers() {
            return best_sellers;
        }

        public void setBest_sellers(List<BestSeller> best_sellers) {
            this.best_sellers = best_sellers;
        }

        static public class BestSeller {
            String asin;
            String product_title;
            String product_price;
            String product_star_rating;
            String product_num_ratings;
            String product_url;
            String product_photo;

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
        }
    }
}
