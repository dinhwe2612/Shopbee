package com.example.shopbee.ui.common.dialogs.optiondialog;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptionEvent {
    List<Pair<String, String>> options = new ArrayList<>();
    int quantity;
    String name;
    public OptionEvent(List<Pair<String, String>> options, int quantity, String name) {
        this.options = options;
        this.quantity = quantity;
        this.name = name;
    }
    public List<Pair<String, String>> getOptions() {
        return options;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getName() {
        return name;
    }
}
