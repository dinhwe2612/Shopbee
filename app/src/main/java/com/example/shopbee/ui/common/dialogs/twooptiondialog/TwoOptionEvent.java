package com.example.shopbee.ui.common.dialogs.twooptiondialog;

public class TwoOptionEvent {
    public enum Button {
        FIRST, SECOND
    }
    final Button button;
    public TwoOptionEvent(Button button) {
        this.button = button;
    }
    public Button getButton() {
        return button;
    }
}
