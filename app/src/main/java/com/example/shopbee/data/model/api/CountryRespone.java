package com.example.shopbee.data.model.api;
import android.widget.LinearLayout;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CountryRespone {
    private String name;
    private String code;
    private String currency;
    private String flagPngUrl;
    private String flagSvgUrl;
    public CountryRespone(String name, String code, String currency, String flagPngUrl, String flagSvgUrl) {
        this.name = name;
        this.code = code;
        this.currency = currency;
        this.flagPngUrl = flagPngUrl;
        this.flagSvgUrl = flagSvgUrl;
    }
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFlagPngUrl() {
        return flagPngUrl;
    }

    public String getFlagSvgUrl() {
        return flagSvgUrl;
    }
}
