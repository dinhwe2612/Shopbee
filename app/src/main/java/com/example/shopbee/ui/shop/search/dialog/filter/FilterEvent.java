package com.example.shopbee.ui.shop.search.dialog.filter;

public class FilterEvent {
    private Filter filter;
    FilterEvent(Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
