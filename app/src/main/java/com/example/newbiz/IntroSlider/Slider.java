package com.example.newbiz.IntroSlider;

import com.example.newbiz.CardsGenerator.CardsModal;

import retrofit2.Call;
import retrofit2.http.POST;

public interface Slider {
    @POST("fetchIntroSlider.php")
    Call<SliderModal> getSlider();

}