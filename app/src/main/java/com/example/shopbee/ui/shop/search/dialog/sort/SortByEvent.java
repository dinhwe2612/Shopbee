package com.example.shopbee.ui.shop.search.dialog.sort;

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
