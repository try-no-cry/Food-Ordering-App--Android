package com.example.newbiz;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment  {

private TextView tvMyOrders;
private  BackgroundTask backgroundTask;
static  String resultFromQuery;
    public MyOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        backgroundTask=new BackgroundTask(getContext());

        String sql="SELECT * FROM orders";
        backgroundTask.execute(sql,"selectOrder.php");


        return inflater.inflate(R.layout.fragment_my_orders, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMyOrders=view.findViewById(R.id.tvMyOrders);


        if (getArguments() != null) {
            resultFromQuery = getArguments().getString("data"); //coming from main activity
        }



    }

    public  void setTextView(String s){

        tvMyOrders.setText(s);

    }

}
