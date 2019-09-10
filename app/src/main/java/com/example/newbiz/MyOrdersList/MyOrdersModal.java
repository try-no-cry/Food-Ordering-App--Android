package com.example.newbiz.MyOrdersList;

import com.example.newbiz.IntroSlider.Slide_recycler;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyOrdersModal {

    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private  ArrayList<MyOrders_SingleOrder> singleOrders ;


    public MyOrdersModal(String success, ArrayList<MyOrders_SingleOrder> singleOrders) {
        this.success = success;
        this.singleOrders = singleOrders;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<MyOrders_SingleOrder> getSingleOrders() {
        return singleOrders;
    }

    public void setSingleOrders(ArrayList<MyOrders_SingleOrder> singleOrders) {
        this.singleOrders = singleOrders;
    }
}
