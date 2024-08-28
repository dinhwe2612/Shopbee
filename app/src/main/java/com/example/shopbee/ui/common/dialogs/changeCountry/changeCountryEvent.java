package com.example.shopbee.ui.common.dialogs.changeCountry;

import com.example.shopbee.ui.common.dialogs.DialogsEventBus;

public class changeCountryEvent {
    private String newCountry;
    private String newCode;
    private String pngUrl;
    private int position;

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

