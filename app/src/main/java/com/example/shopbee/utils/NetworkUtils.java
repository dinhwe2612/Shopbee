package com.example.shopbee.utils;

import java.util.HashMap;

public final class NetworkUtils {
    public static final String BASE_URL_TEXEL = "https://texel-virtual-try-on.p.rapidapi.com/";

    private NetworkUtils() {}
    public static final String BASE_URL_COUNTRY = "https://restcountries.com/";
    public static final String BASE_URL = "https://real-time-amazon-data.p.rapidapi.com/";
    public static final String HEADER_KEY = "x-rapidapi-key";
    public static final String KEY = "bb662cd28bmshc73cda7d8df6139p190b29jsn8dbcf236d12e";
    public static final String HEADER_HOST = "x-rapidapi-host";
    public static final String HOST = "real-time-amazon-data.p.rapidapi.com";
    public static HashMap<String, String> createSearchQuery(String query, String page, String country) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("query", query);
        queryMap.put("page", page);
        queryMap.put("country", country);
        queryMap.put("sort_by", "RELEVANCE");
        queryMap.put("product_condition", "ALL");
        return queryMap;
    }

    public static HashMap<String, String> createAmazonDealsQuery() {
        HashMap<String, String> queryMap = new HashMap<>();
        return queryMap;
    }
    public static HashMap<String, String> createProductDetailsQuery(String asin) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("asin", asin);
        return queryMap;
    }
}
