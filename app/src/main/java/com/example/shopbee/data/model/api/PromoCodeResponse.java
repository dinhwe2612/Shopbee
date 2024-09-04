package com.example.shopbee.data.model.api;

import android.util.Pair;

import androidx.annotation.Nullable;

import com.example.shopbee.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PromoCodeResponse {
    String style;

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Integer> getStyle() {
        if ("red".equals(style)) {
            return new ArrayList<>(Arrays.asList(R.drawable.slight_rounded_red_rectangle, R.style.White_Bold_34dp, R.style.White_Bold_14dp));
        }
        return null;
    }

    public PromoCodeResponse(Integer percent, Float max_discount, String name, String code, String due_date, String style) {
        this.percent = percent;
        this.max_discount = max_discount;
        this.name = name;
        this.code = code;
        this.due_date = due_date;
        this.style = style;
    }
    Integer percent;
    Float max_discount;
    String name;
    String code;
    String due_date;
    public Float getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(Float max_discount) {
        this.max_discount = max_discount;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }


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
    public static float roundToTwoDecimalPlaces(float value) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public float processDiscount(float price) {
        float discount = Math.min(price * percent / 100, max_discount);
        return roundToTwoDecimalPlaces(discount);
    }

    public float processPrice(float price) {
        float finalPrice = Math.max(price - max_discount, price - price * percent / 100);
        return roundToTwoDecimalPlaces(finalPrice);
    }

    public void syncDataFirebase() {

    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
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
}
