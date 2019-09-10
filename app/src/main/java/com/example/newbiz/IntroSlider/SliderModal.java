package com.example.newbiz.IntroSlider;

import com.example.newbiz.CardsGenerator.Single_Card;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SliderModal {

    @SerializedName("success")
    private String success;

    public SliderModal(String success, ArrayList<Slide_recycler> slider) {
        this.success = success;
        this.slider = slider;
    }

    public ArrayList<Slide_recycler> getSlider() {
        return slider;
    }

    public void setSlider(ArrayList<Slide_recycler> slider) {
        this.slider = slider;
    }

    @SerializedName("data")
    private ArrayList<Slide_recycler> slider ;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


}
