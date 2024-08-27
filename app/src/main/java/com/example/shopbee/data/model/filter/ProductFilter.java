package com.example.shopbee.data.model.filter;

import java.util.HashMap;

public class ProductFilter {
    private HashMap<SortByChoice, String> sort_by_choice_map;
    public HashMap<SortByChoice, String> getSortByChoiceMap() {
        return sort_by_choice_map;
    }
    public void setSortByChoiceMap(HashMap<SortByChoice, String> sort_by_choice_map) {
        this.sort_by_choice_map = sort_by_choice_map;
    }
    SortByChoice sort_by_choice;
    Float min_price;
    Float max_price;
    ProductCondition product_condition;
    String product_name;
    String product_asin;
    ProductCountry product_country;
    String category_id;
    Integer page;

    public ProductFilter(SortByChoice sort_by_choice
            , float min_price
            , float max_price
            , ProductCondition product_condition
            , String product_name
            , String product_asin
            , ProductCountry product_country
            , String category_id, Integer page) {
        this.sort_by_choice = sort_by_choice;
        this.min_price = min_price;
        this.max_price = max_price;
        this.product_condition = product_condition;
        this.product_name = product_name;
        this.product_asin = product_asin;
        this.product_country = product_country;
        this.category_id = category_id;
        this.page = page;
        sort_by_choice_map = new HashMap<>();
        sort_by_choice_map.put(SortByChoice.RELEVANCE, "Most Relevant");
        sort_by_choice_map.put(SortByChoice.HIGHEST_PRICE, "Highest Price");
        sort_by_choice_map.put(SortByChoice.LOWEST_PRICE, "Lowest Price");
        sort_by_choice_map.put(SortByChoice.NEWEST, "Newest");
        sort_by_choice_map.put(SortByChoice.REVIEWS, "Most Reviewed");
        sort_by_choice_map.put(SortByChoice.BEST_SELLERS, "Best Sellers");
    }
    public ProductFilter(boolean default_filter) {
        // required product_name or product_asin
        this.sort_by_choice = SortByChoice.RELEVANCE;
        this.product_country = ProductCountry.US;
        this.category_id = "aps";
        this.product_condition = ProductCondition.ALL;
        this.page = 1;
        sort_by_choice_map = new HashMap<>();
        sort_by_choice_map.put(SortByChoice.RELEVANCE, "Most Relevant");
        sort_by_choice_map.put(SortByChoice.HIGHEST_PRICE, "Highest Price");
        sort_by_choice_map.put(SortByChoice.LOWEST_PRICE, "Lowest Price");
        sort_by_choice_map.put(SortByChoice.NEWEST, "Newest");
        sort_by_choice_map.put(SortByChoice.REVIEWS, "Most Reviewed");
        sort_by_choice_map.put(SortByChoice.BEST_SELLERS, "Best Sellers");
    }
    public ProductFilter() {
        sort_by_choice_map = new HashMap<>();
        sort_by_choice_map.put(SortByChoice.RELEVANCE, "Most Relevant");
        sort_by_choice_map.put(SortByChoice.HIGHEST_PRICE, "Highest Price");
        sort_by_choice_map.put(SortByChoice.LOWEST_PRICE, "Lowest Price");
        sort_by_choice_map.put(SortByChoice.NEWEST, "Newest");
        sort_by_choice_map.put(SortByChoice.REVIEWS, "Most Reviewed");
        sort_by_choice_map.put(SortByChoice.BEST_SELLERS, "Best Sellers");
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getPage() {
        return page;
    }
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    public String getCategory_id() {
        return category_id;
    }
    public void setProduct_country(ProductCountry product_country) {
        this.product_country = product_country;
    }
    public ProductCountry getProduct_country() {
        return product_country;
    }
    public void setProduct_asin(String product_asin) {
        this.product_asin = product_asin;
    }
    public String getProduct_asin() {
        return product_asin;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getProduct_name() {
        return product_name;
    }

    public void setMin_price(float min_price) {
        this.min_price = min_price;
    }

    public void setMax_price(float max_price) {
        this.max_price = max_price;
    }

    public void setSort_by_choice(SortByChoice sort_by_choice) {
        this.sort_by_choice = sort_by_choice;
    }

    public void setProduct_condition(ProductCondition product_condition) {
        this.product_condition = product_condition;
    }

    public SortByChoice getSort_by_choice() {
        return sort_by_choice;
    }

    public float getMin_price() {
        return min_price;
    }

    public float getMax_price() {
        return max_price;
    }

    public ProductCondition getProduct_condition() {
        return product_condition;
    }

    public HashMap<String, String> getProductFilter() {
        HashMap<String, String> product_filter = new HashMap<>();
        if (product_name !=  null) {
            product_filter.put("query", this.product_name);
        }
        if (product_asin != null) {
            product_filter.put("query", this.product_asin);
        }
        if (page != null) {
            product_filter.put("page", String.valueOf(this.page));
        }
        if (product_country != null) {
            product_filter.put("country", this.product_country.toString());
        }
        if (sort_by_choice != null) {
            product_filter.put("sort_by", this.sort_by_choice.toString());
        }
        if (category_id != null) {
            product_filter.put("category_id", this.category_id);
        }
        if (min_price != null) {
            product_filter.put("min_price", String.valueOf(this.min_price));
        }
        if (max_price != null) {
            product_filter.put("max_price", String.valueOf(this.max_price));
        }
        if (product_condition != null) {
            product_filter.put("product_condition", this.product_condition.toString());
        }
        return product_filter;
    }
}
