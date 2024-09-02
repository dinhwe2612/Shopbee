package com.example.shopbee.data.model.api.jsondeserializer;

import android.util.Log;

import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
                    String price = dataObj.get("product_price").getAsString();
                    price = price.startsWith("$") ? price : "$" + price;
                    data.setProduct_price(price);
                    Log.d("price", data.getProduct_price());
                }
                if (dataObj.has("product_original_price") && !dataObj.get("product_original_price").isJsonNull()) {
                    String price = dataObj.get("product_original_price").getAsString();
                    price = price.startsWith("$") ? price : "$" + price;
                    data.setProduct_original_price(price);
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
//                    data.setCategory_path(context.deserialize(dataObj.get("category_path"), List.class));
                    if (dataObj.get("category_path").isJsonArray()) {
                        List<AmazonProductDetailsResponse.Data.Category> categoryPath = new ArrayList<>();
                        for (JsonElement element : dataObj.get("category_path").getAsJsonArray()) {
                            AmazonProductDetailsResponse.Data.Category category = new AmazonProductDetailsResponse.Data.Category();
                            JsonObject categoryObj = element.getAsJsonObject();
                            if (categoryObj.has("name") && !categoryObj.get("name").isJsonNull()) {
                                category.setName(categoryObj.get("name").getAsString());
                                Log.d("category_name", category.getName());
                            }
                            if (categoryObj.has("link") && !categoryObj.get("link").isJsonNull()) {
                                category.setLink(categoryObj.get("link").getAsString());
                                Log.d("category_url", category.getLink());
                            }
                            if (categoryObj.has("id") && !categoryObj.get("id").isJsonNull()) {
                                category.setId(categoryObj.get("id").getAsString());
                                Log.d("category_photo", category.getId());
                            }
                            categoryPath.add(category);
                        }
                        data.setCategory_path(categoryPath);
                    }
                    Log.d("category_path", data.getCategory_path().toString());
                }
                if (dataObj.has("product_variations") && !dataObj.get("product_variations").isJsonNull()) {
                    HashMap<String, List<AmazonProductDetailsResponse.Data.VariationDetail>> variations = new HashMap<>();
                    if (dataObj.get("product_variations").isJsonObject()) {
                        for (String key : dataObj.get("product_variations").getAsJsonObject().keySet()) {
                            List<AmazonProductDetailsResponse.Data.VariationDetail> variationDetails = new ArrayList<>();
                            for (JsonElement variationElement : dataObj.get("product_variations").getAsJsonObject().get(key).getAsJsonArray()) {
                                AmazonProductDetailsResponse.Data.VariationDetail variationDetail = new AmazonProductDetailsResponse.Data.VariationDetail();
                                JsonObject variationObj = variationElement.getAsJsonObject();
                                if (variationObj.has("asin") && !variationObj.get("asin").isJsonNull()) {
                                    variationDetail.setAsin(variationObj.get("asin").getAsString());
                                    Log.d("variation_name", variationDetail.getAsin());
                                }
                                if (variationObj.has("value") && !variationObj.get("value").isJsonNull()) {
                                    variationDetail.setValue(variationObj.get("value").getAsString());
                                    Log.d("variation_value", variationDetail.getValue());
                                }
                                if (variationObj.has("photo") && !variationObj.get("photo").isJsonNull()) {
                                    variationDetail.setPhoto(variationObj.get("photo").getAsString());
                                    Log.d("variation_photo", variationDetail.getPhoto());
                                }
                                variationDetails.add(variationDetail);
                            }
                            variations.put(key, variationDetails);
                        }
                        data.setProduct_variations(variations);
                        Log.d("variations", data.getProduct_variations().toString());
                    }
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
