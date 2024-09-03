package com.example.shopbee.data.model.api;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PromoCodeResponse {
    Float percent;
    Float max_discount;

    public Float getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(Float max_discount) {
        this.max_discount = max_discount;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    String name;
    String code;
    String due_date;
    public Date getDueDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Parse the string into a Date object
            return formatter.parse(due_date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // or handle it another way if you prefer
        }
    }
    public boolean hasPassedDueDate() {
        Date dueDate = getDueDate();
        if (dueDate == null) {
            // Handle the case where the due date couldn't be parsed
            return false; // or throw an exception, or handle it as needed
        }

        Date currentDate = new Date(); // Get the current date and time
        // Check if the due date is before the current date
        return dueDate.before(currentDate);
    }
    public String getDue_date() {
        return due_date;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public boolean equals(PromoCodeResponse promoCodeResponse) {
        return (this.code.equals(promoCodeResponse.code));
    }

    public PromoCodeResponse() {

    }
    public float processDiscount(float price) {
        return Math.min(price * percent, max_discount);
    }
    public float processPrice(float price) {
        return Math.max(price - max_discount, price - price * percent);
    }
    public void syncDataFirebase() {

    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PromoCodeResponse(Float percent, String name, String code) {
        this.percent = percent;
        this.name = name;
        this.code = code;
    }
}
