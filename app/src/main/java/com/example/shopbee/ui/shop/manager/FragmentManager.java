package com.example.shopbee.ui.shop.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FragmentManager {
    public interface OnEventPostedListener {
        void onEventPosted(Object event);
    }
    Set<OnEventPostedListener> listeners;
    public void postEvent(Object event) {
        for (OnEventPostedListener listener : listeners) {
            listener.onEventPosted(event);
        }
    }
    public FragmentManager() {
        listeners = new HashSet<>();
    }
    public void addListener(OnEventPostedListener listener) {
        listeners.add(listener);
    }
    public void removeListener(OnEventPostedListener listener) {
        listeners.remove(listener);
    }
}
