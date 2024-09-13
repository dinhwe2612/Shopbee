package com.example.shopbee.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;
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

public class PromoCodeResponse implements Parcelable {
    String description;
    String style;
    Integer percent;
    Float max_discount;
    String name;
    String code;
    String due_date;

    public PromoCodeResponse() {

    }

    public PromoCodeResponse(Integer percent, Float max_discount, String name, String code, String due_date, String style) {
        this.percent = percent;
        this.max_discount = max_discount;
        this.name = name;
        this.code = code;
        this.due_date = due_date;
        this.style = style;
    }

    protected PromoCodeResponse(Parcel in) {
        description = in.readString();
        style = in.readString();
        if (in.readByte() == 0) {
            percent = null;
        } else {
            percent = in.readInt();
        }
        if (in.readByte() == 0) {
            max_discount = null;
        } else {
            max_discount = in.readFloat();
        }
        name = in.readString();
        code = in.readString();
        due_date = in.readString();
    }

    public static final Creator<PromoCodeResponse> CREATOR = new Creator<PromoCodeResponse>() {
        @Override
        public PromoCodeResponse createFromParcel(Parcel in) {
            return new PromoCodeResponse(in);
        }

        @Override
        public PromoCodeResponse[] newArray(int size) {
            return new PromoCodeResponse[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Integer> getStyle() {
        if ("red".equals(style)) {
            return new ArrayList<>(Arrays.asList(R.drawable.slight_rounded_red_rectangle, R.style.White_Bold_34dp, R.style.White_Bold_14dp));
        }
        return null;
    }

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(due_date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRemainingTime() {
        Date dueDate = getDueDate();
        if (dueDate == null) {
            return "Invalid due date";
        }

        Date currentDate = new Date();
        long diffInMillies = dueDate.getTime() - currentDate.getTime();

        if (diffInMillies <= 0) {
            return "Expired";
        }

        long diffInSeconds = diffInMillies / 1000;
        long days = diffInSeconds / (60 * 60 * 24);
        diffInSeconds %= (60 * 60 * 24);

        if (days > 0) {
            return days + " day(s) remaining";
        }

        long hours = diffInSeconds / (60 * 60);
        diffInSeconds %= (60 * 60);

        if (hours > 0) {
            return hours + " hour(s) remaining";
        }

        long minutes = diffInSeconds / 60;
        diffInSeconds %= 60;

        if (minutes > 0) {
            return minutes + " minute(s) remaining";
        }

        return diffInSeconds + " second(s) remaining";
    }

    public boolean hasPassedDueDate() {
        Date dueDate = getDueDate();
        if (dueDate == null) {
            return false;
        }

        Date currentDate = new Date();
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

    public static float roundToTwoDecimalPlaces(float value) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public Float processDiscount(float price) {
        float discount = Math.min(price * percent / 100, max_discount);
        return roundToTwoDecimalPlaces(discount);
    }

    public Float processPrice(float price) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(style);
        if (percent == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(percent);
        }
        if (max_discount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(max_discount);
        }
        parcel.writeString(name);
        parcel.writeString(code);
        parcel.writeString(due_date);
    }
}
