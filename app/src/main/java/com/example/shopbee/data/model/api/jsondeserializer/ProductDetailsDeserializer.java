package com.example.shopbee.data.model.api.jsondeserializer;

import android.util.Log;

import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ProductDetailsDeserializer implements JsonDeserializer<AmazonProductDetailsResponse> {
    @Override
    public AmazonProductDetailsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || !json.isJsonObject()) {
            throw new JsonParseException("Invalid JSON format");
        }
        JsonObject obj = json.getAsJsonObject();
        AmazonProductDetailsResponse productDetails = new AmazonProductDetailsResponse();
        AmazonProductDetailsResponse.Data data = new AmazonProductDetailsResponse.Data();
        try {
            if (obj.has("data")) {
                //            String asin;
                //            String product_title;
                //            String product_price;
                //            String product_original_price;
                //            String product_byline;
                //            String product_star_rating;
                //            String product_num_ratings;
                //            String product_url;
                //            String product_photo;
                //            String product_availability;
                //            List<String> about_product;
                //            String product_description;
                //            String product_information;
                //            List<String> product_photos;
                //            HashMap<String, String> product_details;
                //            String customers_say;
                //            List<ProductDetailsResponse.Data.Category> category_path;
                //            HashMap<String, List<String>> product_variations;
                JsonObject dataObj = obj.getAsJsonObject("data");
                if (dataObj.has("asin") && !dataObj.get("asin").isJsonNull()) {
                    data.setAsin(dataObj.get("asin").getAsString());
                    Log.d("asin", data.getAsin());
                }
                if (dataObj.has("product_title") && !dataObj.get("product_title").isJsonNull()) {
                    data.setProduct_title(dataObj.get("product_title").getAsString());
                    Log.d("title", data.getProduct_title());
                }
                if (dataObj.has("product_price") && !dataObj.get("product_price").isJsonNull()) {
                    data.setProduct_price(dataObj.get("product_price").getAsString());
                    Log.d("price", data.getProduct_price());
                }
                if (dataObj.has("product_original_price") && !dataObj.get("product_original_price").isJsonNull()) {
                    data.setProduct_original_price(dataObj.get("product_original_price").getAsString());
                    Log.d("original_price", data.getProduct_original_price());
                }
                if (dataObj.has("product_byline") && !dataObj.get("product_byline").isJsonNull()) {
                    data.setProduct_byline(dataObj.get("product_byline").getAsString());
                    Log.d("byline", data.getProduct_byline());
                }
                if (dataObj.has("product_star_rating") && !dataObj.get("product_star_rating").isJsonNull()) {
                    data.setProduct_star_rating(dataObj.get("product_star_rating").getAsString());
                    Log.d("star_rating", data.getProduct_star_rating());
                }
                if (dataObj.has("product_num_ratings") && !dataObj.get("product_num_ratings").isJsonNull()) {
                    data.setProduct_num_ratings(dataObj.get("product_num_ratings").getAsInt());
                    Log.d("num_ratings", String.valueOf(data.getProduct_num_ratings()));
                }
                if (dataObj.has("product_url") && !dataObj.get("product_url").isJsonNull()) {
                    data.setProduct_url(dataObj.get("product_url").getAsString());
                    Log.d("url", data.getProduct_url());
                }
                if (dataObj.has("product_photo") && !dataObj.get("product_photo").isJsonNull()) {
                    data.setProduct_photo(dataObj.get("product_photo").getAsString());
                    Log.d("photo", data.getProduct_photo());
                }
                if (dataObj.has("product_availability") && !dataObj.get("product_availability").isJsonNull()) {
                    data.setProduct_availability(dataObj.get("product_availability").getAsString());
                    Log.d("availability", data.getProduct_availability());
                }
                if (dataObj.has("about_product") && !dataObj.get("about_product").isJsonNull()) {
                    data.setAbout_product(context.deserialize(dataObj.get("about_product"), List.class));
                    Log.d("about_product", data.getAbout_product().toString());
                }
                if (dataObj.has("product_description") && !dataObj.get("product_description").isJsonNull()) {
                    data.setProduct_description(dataObj.get("product_description").getAsString());
                    Log.d("description", data.getProduct_description());
                }
                if (dataObj.has("product_information") && !dataObj.get("product_information").isJsonNull()) {
                    data.setProduct_information(context.deserialize(dataObj.get("product_information"), HashMap.class));
                    Log.d("information", data.getProduct_information().toString());
                }
                if (dataObj.has("product_photos") && !dataObj.get("product_photos").isJsonNull()) {
                    data.setProduct_photos(context.deserialize(dataObj.get("product_photos"), List.class));
                    Log.d("photos", data.getProduct_photos().toString());
                }
                if (dataObj.has("product_details") && !dataObj.get("product_details").isJsonNull()) {
                    data.setProduct_details(context.deserialize(dataObj.get("product_details"), HashMap.class));
                    Log.d("details", data.getProduct_details().toString());
                }
                if (dataObj.has("customers_say") && !dataObj.get("customers_say").isJsonNull()) {
                    data.setCustomers_say(dataObj.get("customers_say").getAsString());
                    Log.d("customers_say", data.getCustomers_say());
                }
                if (dataObj.has("category_path") && !dataObj.get("category_path").isJsonNull()) {
                    data.setCategory_path(context.deserialize(dataObj.get("category_path"), List.class));
                    Log.d("category_path", data.getCategory_path().toString());
                }
                if (dataObj.has("product_variations") && !dataObj.get("product_variations").isJsonNull()) {
                    data.setProduct_variations(context.deserialize(dataObj.get("product_variations"), HashMap.class));
                    Log.d("variations", data.getProduct_variations().toString());
                }
                productDetails.setData(data);
            }
        }
        catch (Exception e) {
            Log.e("Deserialization Error", e.getMessage());
        }
        return productDetails;
    }
}
