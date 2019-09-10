package com.example.newbiz.CardsGenerator;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CardsModal {
    public CardsModal(String success, ArrayList<Single_Card> cards) {
        this.success = success;
        this.cards = cards;
    }

    private String success;
    @SerializedName("data")
    private ArrayList<Single_Card> cards;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<Single_Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Single_Card> cards) {
        this.cards = cards;
    }
}


