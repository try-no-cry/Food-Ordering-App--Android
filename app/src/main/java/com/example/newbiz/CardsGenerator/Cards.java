package com.example.newbiz.CardsGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;

public interface Cards {

    @POST("fillCardRecyclerView.php")
    Call<CardsModal> getCards();
}
