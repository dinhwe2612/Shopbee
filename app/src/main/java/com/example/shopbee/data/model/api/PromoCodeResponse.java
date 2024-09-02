package com.example.shopbee.data.model.api;

public class PromoCodeResponse {
    Float percent;
    String name;
    String code;
    public float processPrice(float price) {
        return price * (1 - percent);
    }
    public void syncDataFirebase() {

    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PromoCodeResponse(Float percent, String name, String code) {
        this.percent = percent;
        this.name = name;
        this.code = code;
    }
}
