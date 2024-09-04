package com.example.shopbee.data.model.api;

import android.graphics.Bitmap;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private String user_name;
    private String phone;
    private String email;
    private String country;
    private String dob;
    private String full_name;
    private List<AddressResponse> address = new ArrayList<>();
    private List<PaymentResponse> payment = new ArrayList<>();

    public UserResponse() {
    }

    public UserResponse(String user_name, String phone, String email, String country, String dob, String full_name, List<AddressResponse> address, List<PaymentResponse> payment) {
        this.user_name = user_name;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.dob = dob;
        this.full_name = full_name;
        this.address = address;
        this.payment = payment;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<AddressResponse> getAddress() {
        return address;
    }

    public void setAddress(List<AddressResponse> address) {
        this.address = address;
    }

    public List<PaymentResponse> getPayment() {
        return payment;
    }

    public void setPayment(List<PaymentResponse> payment) {
        this.payment = payment;
    }

}
