package com.example.shopbee.ui.chat;

public interface ChatNavigator {
    void onResponse(String answer);
    void onStreamingResponse(String chunk);
}
