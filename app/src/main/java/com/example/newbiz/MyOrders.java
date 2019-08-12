package com.example.newbiz;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment  {

private TextView tvMyOrders;
private  BackgroundTask backgroundTask;
static  String resultFromQuery;
private RecyclerView myOrdersRecycler;
private RecyclerView.Adapter adapter;
private RecyclerView.LayoutManager layoutManager;
private ArrayList<MyOrders_SingleOrder> list=new ArrayList<>();
//    public MyOrders() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_orders, container, false);
        tvMyOrders=v.findViewById(R.id.tvMyOrders);
        myOrdersRecycler=v.findViewById(R.id.myOrdersFragRecycler);







        backgroundTask=new BackgroundTask(getContext(),tvMyOrders);

        String sql="SELECT * FROM orders";
        backgroundTask.execute(sql,"selectOrder.php");
        return v;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);




    }

    @Override
    public void onResume() {
        super.onResume();
        tvMyOrders=getView().findViewById(R.id.tvMyOrders);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();
        tvMyOrders= Objects.requireNonNull(getView()).findViewById(R.id.tvMyOrders);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            resultFromQuery = getArguments().getString("data"); //coming from main activity
        }



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvMyOrders=getView().findViewById(R.id.tvMyOrders);

    }

//    public  void setTextView(String s){
//
////       Activity activity= getActivity();
////        if(activity!=null)
//
//        tvMyOrders.setText(s);
//
//    }


}
