package com.example.newbiz.CardsGenerator;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Single_Card implements Serializable {

    private  String imageUrl;
    String foodName,foodPrice;

    @SerializedName("location")
    String address;

    @SerializedName("any_other_info")
    String anyOtherInfo;

    @SerializedName("foodcard_id")
    String foodCard_id;
    private String food_id;

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


    public void setFoodCard_id(String foodCard_id) {
        this.foodCard_id = foodCard_id;
    }

    public String getFoodCard_id() {
        return foodCard_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_id() {
        return food_id;
    }
}
