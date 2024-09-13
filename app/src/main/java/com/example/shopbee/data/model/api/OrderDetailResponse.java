package com.example.shopbee.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailResponse implements Parcelable {
    private String product_id;
    private String product_name;
    private String price;
    private int quantity;
    private String urlImage;
    private List<Pair<String, String>> variation = new ArrayList<>();
    private String product_star_rating; // New field
    private Integer product_num_ratings; // New field

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(String product_id, String product_name, int quantity, String price, String urlImage,
                               List<Pair<String, String>> variation, String product_star_rating, Integer product_num_ratings) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.urlImage = urlImage;
        this.variation = variation;
        this.product_star_rating = product_star_rating; // New field initialization
        this.product_num_ratings = product_num_ratings; // New field initialization
    }

    // Getters and Setters for the new fields
    public String getProduct_star_rating() {
        return product_star_rating;
    }

    public void setProduct_star_rating(String product_star_rating) {
        this.product_star_rating = product_star_rating;
    }

    public Integer getProduct_num_ratings() {
        return product_num_ratings;
    }

    public void setProduct_num_ratings(Integer product_num_ratings) {
        this.product_num_ratings = product_num_ratings;
    }
    public OrderDetailResponse(String product_id, String product_name, int quantity, String price, String urlImage, List<Pair<String, String>> variation) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.urlImage = urlImage;
        this.variation = variation;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProduct_id() {
        return product_id;
    }
    public String getProduct_name() {
        return product_name;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public List<Pair<String, String>> getVariation() {
        return variation;
    }
    public void setVariation(List<Pair<String, String>> variation) {
        this.variation = variation;
    }
    public String getTotalPrice(){
        String numericString = price.replace("$", "");
        float price = Float.parseFloat(numericString);
        return String.valueOf(price * quantity) + "$";
    }
    protected OrderDetailResponse(Parcel in) {
        product_id = in.readString();
        product_name = in.readString();
        price = in.readString();
        quantity = in.readInt();
        urlImage = in.readString();

        // Reading List<Pair<String, String>> variation
        int size = in.readInt();
        variation = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String first = in.readString();
            String second = in.readString();
            variation.add(new Pair<>(first, second));
        }

        product_star_rating = in.readString();
        if (in.readByte() == 0) {
            product_num_ratings = null;
        } else {
            product_num_ratings = in.readInt();
        }
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_id);
        dest.writeString(product_name);
        dest.writeString(price);
        dest.writeInt(quantity);
        dest.writeString(urlImage);

        // Writing List<Pair<String, String>> variation
        dest.writeInt(variation.size());
        for (Pair<String, String> pair : variation) {
            dest.writeString(pair.first);
            dest.writeString(pair.second);
        }

        dest.writeString(product_star_rating);
        if (product_num_ratings == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(product_num_ratings);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDetailResponse> CREATOR = new Creator<OrderDetailResponse>() {
        @Override
        public OrderDetailResponse createFromParcel(Parcel in) {
            return new OrderDetailResponse(in);
        }

        @Override
        public OrderDetailResponse[] newArray(int size) {
            return new OrderDetailResponse[size];
        }
    };
}
