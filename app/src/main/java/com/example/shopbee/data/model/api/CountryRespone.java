package com.example.shopbee.data.model.api;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CountryRespone {
    @SerializedName("name")
    private Name name;

    @SerializedName("currencies")
    private Map<String, Currency> currencies;

    @SerializedName("cca2")
    private String cca2;

    @SerializedName("flags")
    private Flags flags;

    public Name getName() {
        return name;
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    public String getCca2() {
        return cca2;
    }

    public Flags getFlags() {
        return flags;
    }

    public static class Name {
        @SerializedName("common")
        private String common;

        public String getCommon() {
            return common;
        }
    }

    public static class Currency {
        @SerializedName("symbol")
        private String symbol;

        public String getSymbol() {
            return symbol;
        }
    }

    public static class Flags {
        @SerializedName("png")
        private String png;

        @SerializedName("svg")
        private String svg;

        public String getPng() {
            return png;
        }

        public String getSvg() {
            return svg;
        }
    }
}
