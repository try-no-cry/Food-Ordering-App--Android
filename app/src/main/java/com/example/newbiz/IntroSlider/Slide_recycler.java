package com.example.newbiz.IntroSlider;

import com.google.gson.annotations.SerializedName;

public class Slide_recycler {

    @SerializedName("iS_ImageUrl")
    private String slideImageUrl;

    @SerializedName("introSlider_id")
    private String slideImageID;

    @SerializedName("food_id")
    private String foodID;

    public Slide_recycler() {


    }

    public String getSlideImageUrl() {
        return slideImageUrl;
    }

    public void setSlideImageUrl(String slideImageUrl) {
        this.slideImageUrl = slideImageUrl;
    }

    public void setSlideImageID(String slideImageID) {

        this.slideImageID = slideImageID;
    }

    public String getSlideImageID() {
        return slideImageID;
    }

    public void setFoodID(String foodID) {

        this.foodID = foodID;
    }

    public String getFoodID() {
        return foodID;
    }
}
