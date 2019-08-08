package com.example.newbiz;

import java.io.Serializable;

public class Single_Card implements Serializable {

    private  String imageUrl,foodName,foodPrice,address,anyOtherInfo;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAnyOtherInfo() {
        return anyOtherInfo;
    }

    public void setAnyOtherInfo(String anyOtherInfo) {
        this.anyOtherInfo = anyOtherInfo;
    }

    public Single_Card() {
    }

}
