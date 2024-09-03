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
    private List<Pair<String, String>> variation;
    public OrderDetailResponse(){
    }
    public OrderDetailResponse(String product_id, String product_name, int quantity, String price, String urlImage) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.urlImage = urlImage;
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
    public void setVariation(Map<String, String> variationMap) {
        this.variation = new ArrayList<>();
        if (variationMap != null) {
            for (Map.Entry<String, String> entry : variationMap.entrySet()) {
                Pair<String, String> pair = new Pair<>(entry.getKey(), entry.getValue());
                this.variation.add(pair);
            }
        }
    }
    public String getTotalPrice(){
        String numericString = price.replace("$", "");
        float price = Float.parseFloat(numericString);
        return "$" + String.valueOf(price * quantity);
    }
    protected OrderDetailResponse(Parcel in) {
        product_id = in.readString();
        product_name = in.readString();
        price = in.readString();
        quantity = in.readInt();
        urlImage = in.readString();
        variation = new ArrayList<>();
        in.readList(variation, Pair.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_id);
        dest.writeString(product_name);
        dest.writeString(price);
        dest.writeInt(quantity);
        dest.writeString(urlImage);
        dest.writeList(variation);
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
