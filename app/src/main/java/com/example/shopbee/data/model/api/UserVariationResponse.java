package com.example.shopbee.data.model.api;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserVariationResponse {
    public static class Variation {
        Integer quantity;
        String asin;
        List<Pair<String, String>> variation;

        public Variation(String asin, List<Pair<String, String>> variation, Integer quantity) {
            this.asin = asin;
            this.variation = variation;
            this.quantity = quantity;
        }
        public Integer getQuantity() {
            return quantity;
        }
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
        public String getAsin() {
            return asin;
        }

        public void setAsin(String asin) {
            this.asin = asin;
        }

        public List<Pair<String, String>> getVariation() {
            return variation;
        }

        public void setVariation(List<Pair<String, String>> variation) {
            this.variation = variation;
        }
    }
    List<Variation> variations;

    public UserVariationResponse(List<Variation> variations) {
        this.variations = variations;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }
    public UserVariationResponse() {
        variations = new ArrayList<>();
    }
    public void addVariation(Variation variation) {
        variations.add(variation);
    }
}
