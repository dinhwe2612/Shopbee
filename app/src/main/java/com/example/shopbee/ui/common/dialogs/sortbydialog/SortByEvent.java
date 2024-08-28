package com.example.shopbee.ui.common.dialogs.sortbydialog;

import com.example.shopbee.data.model.filter.SortByChoice;

public class SortByEvent {
    SortByChoice sortByChoice;
    public SortByEvent(SortByChoice sortByChoice) {
        this.sortByChoice = sortByChoice;
    }
    public SortByChoice getSortByChoice() {
        return sortByChoice;
    }
}
