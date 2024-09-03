package com.example.shopbee.data.model.api;

import androidx.annotation.Nullable;

public class PromoCodeResponse {
    Float percent;
    String name;
    String code;

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public boolean equals(PromoCodeResponse promoCodeResponse) {
        return (this.code.equals(promoCodeResponse.code));
    }

    public PromoCodeResponse() {

    }
    public float processDiscount(float price) {
        return price * percent;
    }
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
