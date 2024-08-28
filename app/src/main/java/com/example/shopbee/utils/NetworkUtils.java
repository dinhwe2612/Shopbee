package com.example.shopbee.utils;

import java.util.HashMap;

public final class NetworkUtils {
    private NetworkUtils() {}
    public static final String BASE_URL_COUNTRY = "https://restcountries.com/";
    public static final String BASE_URL = "https://real-time-amazon-data.p.rapidapi.com/";
    public static final String HEADER_KEY = "x-rapidapi-key";
    public static final String KEY = "62f6f3b71fmsh832e26b1d7bf1c8p10ee91jsnbcd4439f82e5";
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
}
