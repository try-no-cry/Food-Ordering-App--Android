package com.example.newbiz;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


//protected static String  CONNECTION="https://tonsorial-gear.000webhostapp.com/";
protected static String  CONNECTION="http://192.168.43.77";
//private TextView tvMyOrders;
private  BackgroundTask backgroundTask;
static  String resultFromQuery;
private RecyclerView myOrdersRecycler;
private RecyclerView.Adapter adapter;
private RecyclerView.LayoutManager layoutManager;
private ArrayList<MyOrders_SingleOrder> list=new ArrayList<>();
SharedPreferences prefs;
private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_orders, container, false);
      // tvMyOrders=v.findViewById(R.id.tvMyOrders);
        myOrdersRecycler=v.findViewById(R.id.myOrdersFragRecycler);
        prefs = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
        refreshLayout=v.findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW);
        refreshLayout.setOnRefreshListener(this);

        backgroundTask=new BackgroundTask();

      //  String sql="SELECT * FROM orders ORDER BY id DESC";
//went in Resume
//        else startActivity(new Intent(getContext(),LoginActivity.class));
        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        if(prefs.getString("email", "").equals(""))
            setEmptyMsg("Please Login to see your orders.");

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
       // tvMyOrders= Objects.requireNonNull(getView()).findViewById(R.id.tvMyOrders);
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

    }




    public  void setRecyclerView(String data){


            MyOrders_SingleOrder order;
            JSONArray jsonArray;
            try {
                JSONObject jsonObject=new JSONObject(data);
                String check=jsonObject.getString("success");
                Log.d("checkCondition", String.valueOf(check));

                if(check.equals("1")){

                 //JSONArray jsonArray=jsonObject.getJSONArray("data");
                    jsonArray=jsonObject.getJSONArray("data");
                    int count=0;
                    if(jsonArray.length()==0)
                    {
                        setEmptyMsg("No orders given till now.");
                    }

                    String imageUrl,foodName,orderDate,orderStatus,order_id;
                    if(list.size()!=0)
                        list.clear();
                    while(count<jsonArray.length()){

                        JSONObject JO=jsonArray.getJSONObject(count);
                        imageUrl=JO.getString("imageUrl");
                        foodName=JO.getString("foodName");
                        orderDate=JO.getString("orderDate");
                        orderStatus=JO.getString("orderStatus");
                        order_id=JO.getString("order_id");

                        order=new MyOrders_SingleOrder();
                        order.setImageUrl(imageUrl);
                        order.setFoodName(foodName);
                        order.setOrderDate(orderDate);
                        order.setOrder_id(order_id);
                        order.setOrderStatus(orderStatus);

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
                    Toast.makeText(getContext(),"Orders could not be fetched. Please check your internet connection.",Toast.LENGTH_LONG).show();
                }



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"Couldn't connect. Try Again",Toast.LENGTH_LONG).show();
            }
            //tvMyOrders.setText(data);

    }

    private void setEmptyMsg(String msg) {


        LinearLayout linearLayout=getView().findViewById(R.id.myOrdersLayout);
        TextView textView=new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        textView.setText(msg);
        textView.setTextSize(30);
        textView.setTextColor(Color.BLUE);
        textView.setGravity(Gravity.CENTER);

        linearLayout.addView(textView);
    }

    @Override
    public void onResume() {
        super.onResume();
        backgroundTask=new BackgroundTask();
        if(!prefs.getString("email", "").equals(""))
            backgroundTask.execute("fillMyOrders.php");
        else {
            LinearLayout linearLayout=getView().findViewById(R.id.myOrdersLayout);
            TextView textView=new TextView(getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            textView.setText("Please Login To See Your Orders.");
            textView.setTextSize(30);
            textView.setTextColor(Color.BLUE);
            textView.setGravity(Gravity.CENTER);

            linearLayout.addView(textView);
        }
//        setEmptyMsg("Please Login To See Your Orders.");

    }

    public  class BackgroundTask extends AsyncTask<String,Void,String> {
        AlertDialog.Builder builder;
        private static final String KEY_SUCCESS ="success" ;
        private static final String KEY_DATA ="data" ;
        String result="  ";
        String connstr= CONNECTION +"/phpAndroid/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals(""))
                 setRecyclerView(result);
            else setEmptyMsg("No order has been given till now");
            onRefreshComplete();

        }

        @Override
        protected String doInBackground(String... voids) {

//            String sql=voids[0];
            String extension=voids[0];
            String email=prefs.getString("email","");
            //can be: selectOrder.php,insert.php


            try {
                URL url=new URL(connstr+extension);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops=http.getOutputStream();

                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"utf-8"));

                String data=  URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");

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
                Toast.makeText(getContext(),"Error. Please Try Again.",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Message2",e.getMessage());
                Toast.makeText(getContext(),"Error. Please Try Again.",Toast.LENGTH_SHORT).show();

            }


            return result;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        // Refresh tab data:

        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }

    @Override
    public void onRefresh() {
        backgroundTask=new BackgroundTask();

        //  String sql="SELECT * FROM orders ORDER BY id DESC";
        if(!prefs.getString("email", "").equals(""))
            backgroundTask.execute("fillMyOrders.php");
    }

    private void onRefreshComplete() {

        refreshLayout.setRefreshing(false);
    }


}
