package com.example.shopbee.ui.common.dialogs.addNewCard;

public class addNewCardEvent {
    private String name;
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private String type;
    private Boolean def;

    public addNewCardEvent(String name, String cardNumber, String cvv, String expiryDate, String type, Boolean def) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.type = type;
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getType() {
        return type;
    }

    public Boolean getDef() {
        return def;
    }
}
