package com.example.shopbee.data.remote;

import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductReviewResponse;
import com.example.shopbee.data.model.api.AmazonSearchResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AmazonApiService {
    @GET("search")
    Observable<AmazonSearchResponse> search(@QueryMap Map<String, String> queries);
    enum BestSellersType {
        BEST_SELLERS("BEST_SELLERS"),
        GIFT_IDEAS("GIFT_IDEAS"),
        MOST_WISHED_FOR("MOST_WISHED_FOR"),
        NEW_RELEASES("NEW_RELEASES");
        BestSellersType(String bestSellersType) {
            this.bestSellersType = bestSellersType;
        }
        private String bestSellersType;
        public String getBestSellersType() {
            return bestSellersType;
        }
    }
    @GET("deals-v2")
    Observable<AmazonDealsResponse> getAmazonDeals(@QueryMap Map<String, String> queries);
    @GET("products-by-category")
    Observable<AmazonProductByCategoryResponse> getAmazonProductByCategory(@QueryMap Map<String, String> queries);
    @GET("search")
    Observable<AmazonProductByCategoryResponse> getAmazonProductBySearching(@QueryMap Map<String, String> queries);
    @GET("product-details")
    Observable<AmazonProductDetailsResponse> getAmazonProductDetails(@QueryMap Map<String, String> queries);
    @GET("product-reviews")
    Observable<AmazonProductReviewResponse> getAmazonProductReviews(@QueryMap Map<String, String> queries);
}
