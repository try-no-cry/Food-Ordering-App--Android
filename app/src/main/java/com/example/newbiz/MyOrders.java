package com.example.newbiz;


import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment  {

protected static String  CONNECTION="192.168.42.23";
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







        backgroundTask=new BackgroundTask();

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
        //w2db;`tvMyOrders=getView().findViewById(R.id.tvMyOrders);

    }

    public  void setRecyclerView(String data){


        MyOrders_SingleOrder order;
//        RecyclerView


        //extract data from the JSON string

        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("success");

            if(jsonArray.get(0).toString()=="1"){

                jsonArray=jsonObject.getJSONArray("data");
                int count=0;
                int orderId,foodcard_id,users_id;
                Float quantity,totalPrice;
                String supplyAddress,orderStatus,orderDate,orderTime,foodName;
                while(count<jsonArray.length()){

                    JSONObject JO=jsonArray.getJSONObject(count);
                    orderId= Integer.parseInt(JO.getString("id"));
                    foodcard_id= Integer.parseInt(JO.getString("foodcard_id"));
                    users_id= Integer.parseInt(JO.getString("users_id"));
                    quantity= Float.valueOf(JO.getString("quantity"));
                    supplyAddress=JO.getString("supplyAddress");
                    totalPrice= Float.valueOf(JO.getString("totalPrice"));
                    orderStatus=JO.getString("orderStatus");
                    orderDate=JO.getString("orderDate");
                    orderTime=JO.getString("orderTime");
                    foodName=JO.getString("foodName");

                    order=new MyOrders_SingleOrder();
                    order.setOrderRefNo(String.valueOf(orderId) + foodcard_id+users_id);
                    order.setOrderQuantity(String.valueOf(quantity));
                    order.setFoodPrice(String.valueOf(totalPrice));
                    order.setOrderDate(orderDate);
                    order.setFoodName(foodName);

                    list.add(order);
                    count++;

                }

                MyOrdersPageRecyclerAdapter adapter=new MyOrdersPageRecyclerAdapter(list,getContext());
                myOrdersRecycler.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                myOrdersRecycler.setLayoutManager(layoutManager);
                myOrdersRecycler.setAdapter(adapter);

            }
            else {
                //show some error message for not fetching the orders
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvMyOrders.setText(data);

    }


    public class BackgroundTask extends AsyncTask<String,Void,String> {
        AlertDialog.Builder builder;
        private static final String KEY_SUCCESS ="success" ;
        private static final String KEY_DATA ="data" ;
        String result="  ";
        String connstr="http://"+ CONNECTION +"/phpAndroid/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setRecyclerView(result);
        }

        @Override
        protected String doInBackground(String... voids) {

            String sql=voids[0];
            String extension=voids[1];
            //can be: selectOrder.php,insert.php


            try {
                URL url=new URL(connstr+extension);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops=http.getOutputStream();

                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"utf-8"));

                String data=  URLEncoder.encode("sql","UTF-8")+"="+URLEncoder.encode(sql,"UTF-8");

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();


                InputStream ips=http.getInputStream();

                BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));

                String line="";

                while((line=reader.readLine())!=null){

                    result +=line;

                }

                reader.close();
                ips.close();
                http.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("Message1",e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Message2",e.getMessage());
            }


            return result;
        }
    }


}
