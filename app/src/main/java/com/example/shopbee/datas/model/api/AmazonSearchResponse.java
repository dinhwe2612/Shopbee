package com.example.shopbee.datas.model.api;

import java.util.ArrayList;
import java.util.List;

public class AmazonSearchResponse {

    private String status;
    private String request_id;
    private Parameters parameters;
    private Data data;

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (Data.Product product : data.getProducts()) {
            names.add(product.getProduct_title());
        }
        return names;
    }

    // Nested Parameters class
    public static class Parameters {
        private String query;
        private String country;
        private String sort_by;
        private int page;

        // Getters and setters
        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getSort_by() {
            return sort_by;
        }

        public void setSort_by(String sort_by) {
            this.sort_by = sort_by;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    // Nested Data class
    public static class Data {
        private int total_products;
        private String country;
        private String domain;
        private List<Product> products;

        // Getters and setters
        public int getTotal_products() {
            return total_products;
        }

        public void setTotal_products(int total_products) {
            this.total_products = total_products;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        // Nested Product class
        public static class Product {
            private String asin;
            private String product_title;
            private String product_price;
            private String product_original_price;
            private String currency;
            private String product_star_rating;
            private int product_num_ratings;
            private String product_url;
            private String product_photo;
            private int product_num_offers;
            private String product_minimum_offer_price;
            private boolean is_best_seller;
            private boolean is_amazon_choice;
            private boolean is_prime;
            private boolean climate_pledge_friendly;
            private String sales_volume;
            private String delivery;
            private boolean has_variations;

            // Getters and setters
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

            public int getProduct_num_ratings() {
                return product_num_ratings;
            }

            public void setProduct_num_ratings(int product_num_ratings) {
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

            public int getProduct_num_offers() {
                return product_num_offers;
            }

            public void setProduct_num_offers(int product_num_offers) {
                this.product_num_offers = product_num_offers;
            }

            public String getProduct_minimum_offer_price() {
                return product_minimum_offer_price;
            }

            public void setProduct_minimum_offer_price(String product_minimum_offer_price) {
                this.product_minimum_offer_price = product_minimum_offer_price;
            }

            public boolean isIs_best_seller() {
                return is_best_seller;
            }

            public void setIs_best_seller(boolean is_best_seller) {
                this.is_best_seller = is_best_seller;
            }

            public boolean isIs_amazon_choice() {
                return is_amazon_choice;
            }

            public void setIs_amazon_choice(boolean is_amazon_choice) {
                this.is_amazon_choice = is_amazon_choice;
            }

            public boolean isIs_prime() {
                return is_prime;
            }

            public void setIs_prime(boolean is_prime) {
                this.is_prime = is_prime;
            }

            public boolean isClimate_pledge_friendly() {
                return climate_pledge_friendly;
            }

            public void setClimate_pledge_friendly(boolean climate_pledge_friendly) {
                this.climate_pledge_friendly = climate_pledge_friendly;
            }

            public String getSales_volume() {
                return sales_volume;
            }

            public void setSales_volume(String sales_volume) {
                this.sales_volume = sales_volume;
            }

            public String getDelivery() {
                return delivery;
            }

            public void setDelivery(String delivery) {
                this.delivery = delivery;
            }

            public boolean isHas_variations() {
                return has_variations;
            }

            public void setHas_variations(boolean has_variations) {
                this.has_variations = has_variations;
            }
        }
    }
}
