package com.example.shopbee.ui.common.dialogs.optiondialog;

import java.util.HashMap;

public class OptionEvent {
    HashMap<String, String> options = new HashMap<String, String>();
    int quantity;
    public OptionEvent(HashMap<String, String> options, int quantity) {
        this.options = options;
        this.quantity = quantity;
    }
    public HashMap<String, String> getOptions() {
        return options;
    }
    public int getQuantity() {
        return quantity;
    }
}
