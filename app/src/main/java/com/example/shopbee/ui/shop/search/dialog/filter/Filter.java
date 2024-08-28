package com.example.shopbee.ui.shop.search.dialog.filter;

import com.example.shopbee.data.model.filter.ProductCondition;
import com.example.shopbee.data.model.filter.ProductCountry;

public class Filter {
    Float min_price;
    Float max_price;
    ProductCondition productCondition;
    public Filter(Float min_price, Float max_price, ProductCondition productCondition, ProductCountry productCountry) {
        this.min_price = min_price;
        this.max_price = max_price;
        this.productCondition = productCondition;
    }

    public Float getMin_price() {
        return min_price;
    }

    public void setMin_price(Float min_price) {
        this.min_price = min_price;
    }

    public Float getMax_price() {
        return max_price;
    }

    public void setMax_price(Float max_price) {
        this.max_price = max_price;
    }

    public ProductCondition getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(ProductCondition productCondition) {
        this.productCondition = productCondition;
    }
}
