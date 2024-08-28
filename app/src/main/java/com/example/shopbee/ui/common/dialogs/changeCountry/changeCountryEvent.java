package com.example.shopbee.ui.common.dialogs.changeCountry;

public class changeCountryEvent {
    private final String newCountry;
    private final String newCode;
    private final String pngUrl;
    private final int position;

    public changeCountryEvent(String newCountry, String newCode, String pngUrl, int position) {
        this.newCountry = newCountry;
        this.newCode = newCode;
        this.pngUrl = pngUrl;
        this.position = position;
    }
    public String getNewCountry(){
        return newCountry;
    }
    public String getNewCode(){
        return newCode;
    }
    public String getPngUrl(){
        return pngUrl;
    }
    public int getPosition(){
        return position;
    }
}

