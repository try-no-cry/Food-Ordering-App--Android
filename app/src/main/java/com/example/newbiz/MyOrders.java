package com.example.newbiz;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbiz.IntroSlider.SlideAdapter;
import com.example.newbiz.IntroSlider.Slide_recycler;
import com.example.newbiz.IntroSlider.Slider;
import com.example.newbiz.IntroSlider.SliderModal;
import com.example.newbiz.MyOrdersList.MyOrdersInterface;
import com.example.newbiz.MyOrdersList.MyOrdersModal;
import com.example.newbiz.MyOrdersList.MyOrdersPageRecyclerAdapter;
import com.example.newbiz.MyOrdersList.MyOrders_SingleOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


protected static String  CONNECTION="https://newbizsite.000webhostapp.com/";
//protected static String  CONNECTION="http://192.168.43.77/phpAndroid/";
//private TextView tvMyOrders;
//private  BackgroundTask backgroundTask;
static  String resultFromQuery;
private RecyclerView myOrdersRecycler;
private RecyclerView.Adapter adapter;
private RecyclerView.LayoutManager layoutManager;
private ArrayList<MyOrders_SingleOrder> list=new ArrayList<>();
SharedPreferences prefs;
private TextView tvOfflineMessage;
private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_orders, container, false);
      // tvMyOrders=v.findViewById(R.id.tvMyOrders);
        myOrdersRecycler=v.findViewById(R.id.myOrdersFragRecycler);
        tvOfflineMessage=v.findViewById(R.id.tvOfflineMessage);
        prefs = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
        refreshLayout=v.findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW);
        refreshLayout.setOnRefreshListener(this);

//        backgroundTask=new BackgroundTask();

      //  String sql="SELECT * FROM orders ORDER BY id DESC";
//went in Resume
//        else startActivity(new Intent(getContext(),LoginActivity.class));
        return v;

    }




    public void  checkNetworkConnection(){

        if (isOnline()) {
            //do whatever you want to do

        } else {
            try {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setTitle("Network Error");
                builder.setMessage("Please check your network conection");
                builder.setCancelable(false);


                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        checkNetworkConnection();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();



            } catch (Exception e) {
                Log.d("Dialog", "Show Dialog: " + e.getMessage());
            }
        }


    }


    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkNetworkConnection();
        if(!prefs.getString("email", "").equals("")){
            retroFillOrders(prefs.getString("email",""));
            tvOfflineMessage.setVisibility(View.GONE);

        }
        else {
            tvOfflineMessage.setVisibility(View.VISIBLE);

        }

    }




   private void retroFillOrders(String email){


       int cacheSize = 10 * 1024 * 1024; // 10 MB
       Cache cache = new Cache(getContext().getCacheDir(), cacheSize);


       Gson gson = new GsonBuilder().serializeNulls().create();


       HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
       loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       OkHttpClient okHttpClient = new OkHttpClient.Builder()
               .addInterceptor(loggingInterceptor)
               .cache(cache)
               .build();






       Retrofit retrofit=new Retrofit.Builder()
               .baseUrl("http://newbizsite.000webhostapp.com/")
               .client(okHttpClient)
               .addConverterFactory(GsonConverterFactory.create(gson))
               .build();

       MyOrdersInterface myOrdersInterface=retrofit.create(MyOrdersInterface.class);

       Call<MyOrdersModal> call=myOrdersInterface.getSingleOrders(email);


       call.enqueue(new Callback<MyOrdersModal>() {
           @Override
           public void onResponse(Call<MyOrdersModal> call, Response<MyOrdersModal> response) {
               if(!response.isSuccessful()){
                   Toast.makeText(getContext(),"MunnaBhai kuch Jhol hai!",Toast.LENGTH_SHORT).show();
                   return;
               }

               if (    response.raw().networkResponse() != null &&
                       response.raw().networkResponse().code() == HttpURLConnection.HTTP_NOT_MODIFIED) {
                   // the response hasn't changed, so you do not need to do anything
                   return;
               }

               MyOrdersModal myOrdersModal=response.body();

               if(Integer.parseInt(myOrdersModal.getSuccess())==1){

                   ArrayList<MyOrders_SingleOrder> singleOrdersList=response.body().getSingleOrders();

                   MyOrdersPageRecyclerAdapter adapter=new MyOrdersPageRecyclerAdapter(singleOrdersList,getContext());
                   myOrdersRecycler.setHasFixedSize(true);
                   layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                   myOrdersRecycler.setLayoutManager(layoutManager);
                   myOrdersRecycler.setAdapter(adapter);

               }
               else {
                   Toast.makeText(getContext(),"Fail hogya task bhidu!",Toast.LENGTH_SHORT).show();
                   return;
               }
           }

           @Override
           public void onFailure(Call<MyOrdersModal> call, Throwable t) {

               Toast.makeText(getContext(),"Gadbadjhala!! "+t.getMessage(),Toast.LENGTH_SHORT).show();
               return;
           }
       });

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

        if(prefs.getString("email","")!=""){
            if(!prefs.getString("email", "").equals(""))
                retroFillOrders(prefs.getString("email",""));
             }
        else onRefreshComplete();

    }

    private void onRefreshComplete() {

        refreshLayout.setRefreshing(false);
    }






}
