package com.example.newbiz.MyOrdersList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyOrdersInterface {

    @FormUrlEncoded
    @POST("fillMyOrders.php")
    Call<MyOrdersModal> getSingleOrders( @Field("email") String email );
}
