package com.example.shopbee.data.model.api;

public class PaymentResponse {
    private String cvv;
    private Boolean def;
    private String expiryDate;
    private String name;
    private String number;
    private String type;
    PaymentResponse(){}

    public PaymentResponse(String cvv, Boolean def, String expiryDate, String name, String number, String type) {
        this.cvv = cvv;
        this.def = def;
        this.expiryDate = expiryDate;
        this.name = name;
        this.number = number;
        this.type = type;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Boolean getDef() {
        return def;
    }

    public void setDef(Boolean def) {
        this.def = def;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
