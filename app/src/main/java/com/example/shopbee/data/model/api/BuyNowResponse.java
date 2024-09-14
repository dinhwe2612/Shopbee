package com.example.shopbee.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class BuyNowResponse implements Parcelable {
    private List<OrderDetailResponse> orderDetailResponseList;

    public BuyNowResponse(List<OrderDetailResponse> orderDetailResponseList) {
        this.orderDetailResponseList = orderDetailResponseList;
    }

    protected BuyNowResponse(Parcel in) {
        orderDetailResponseList = in.createTypedArrayList(OrderDetailResponse.CREATOR);
    }

    public static final Creator<BuyNowResponse> CREATOR = new Creator<BuyNowResponse>() {
        @Override
        public BuyNowResponse createFromParcel(Parcel in) {
            return new BuyNowResponse(in);
        }

        @Override
        public BuyNowResponse[] newArray(int size) {
            return new BuyNowResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(orderDetailResponseList);
    }

    public List<OrderDetailResponse> getOrderDetailResponseList() {
        return orderDetailResponseList;
    }

    public void setOrderDetailResponseList(List<OrderDetailResponse> orderDetailResponseList) {
        this.orderDetailResponseList = orderDetailResponseList;
    }
}
