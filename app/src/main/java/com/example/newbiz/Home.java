package com.example.newbiz;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.newbiz.CardsGenerator.Cards;
import com.example.newbiz.CardsGenerator.CardsAdapter;
import com.example.newbiz.CardsGenerator.CardsModal;
import com.example.newbiz.CardsGenerator.Single_Card;
import com.example.newbiz.IntroSlider.SlideAdapter;
import com.example.newbiz.IntroSlider.Slide_recycler;
import com.example.newbiz.IntroSlider.Slider;
import com.example.newbiz.IntroSlider.SliderModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class  Home extends Fragment
//        implements CardsAdapter.ItemSelected
{
private Single_Card single_card;
private float initialX;
private ViewFlipper viewFlipper;
private GestureDetector mGestureDetector;

//slide

 private RecyclerView slideRecycler;
 ArrayList<Slide_recycler> slide_list=new ArrayList<>();
private RecyclerView.LayoutManager layoutManagerSlide;
//slideEnd


private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;
ArrayList<Single_Card> list=new ArrayList<>();

    public Home() {
        // Required empty public constructor
    }

    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View v=inflater.inflate(R.layout.fragment_home, container, false);

        slidersRetro();
        cardsRetro();

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
                        checkNetworkConnection();
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
//            Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkNetworkConnection();

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.cardsList);
        slideRecycler=view.findViewById(R.id.slideRecycler);
        //slide start

//        BackgroundTaskSlider backgroundTaskSlider=new BackgroundTaskSlider();
//        backgroundTaskSlider.execute("fetchIntroSlider.php");


//       BackgroundTaskCard backgroundTask=new BackgroundTaskCard();
//       backgroundTask.execute("fillCardRecyclerView.php");


    }




//    public class BackgroundTaskCard extends AsyncTask<String,Void,String> {
//        AlertDialog.Builder builder;
//        private static final String KEY_SUCCESS ="success" ;
//        private static final String KEY_DATA ="data" ;
//        String result="  ";
//        String connstr= MyOrders.CONNECTION ;
////                +"/phpAndroid/";
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            //Add progress dialog to show insertion
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            super.onPostExecute(s);
//            setFoodCardRecycler(s);
//        }
//
//        @Override
//        protected String doInBackground(String... voids) {
//
//            String extension=voids[0];
//
//
//            try {
//                URL url=new URL(connstr+extension);
//                HttpURLConnection http= (HttpURLConnection) url.openConnection();
//                http.setRequestMethod("POST");
//                http.setDoInput(true);
//                http.setDoOutput(true);
//
//
//
//
//                InputStream ips=http.getInputStream();
//
//                BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
//
//                String line="";
//
//                while((line=reader.readLine())!=null){
//
//                    result +=line;
//
//                }
//
//                reader.close();
//                ips.close();
//                http.disconnect();
//
//                return result;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Log.i("Message1",e.getMessage());
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.i("Message2",e.getMessage());
//            }
//
//
//            return result;
//        }
//    }

    public class BackgroundTaskSlider extends AsyncTask<String,Void,String> {
        AlertDialog.Builder builder;
        private static final String KEY_SUCCESS ="success" ;
        private static final String KEY_DATA ="data" ;
        String result="  ";
        String connstr= MyOrders.CONNECTION ;
//        +"/phpAndroid/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Add progress dialog to show insertion
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            setImageSlider(s);
        }

        @Override
        protected String doInBackground(String... voids) {

            String extension=voids[0];


            try {
                URL url=new URL(connstr+extension);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);




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

    private void setImageSlider(String s) {


//        layoutManagerSlide=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
//        slideRecycler.setLayoutManager(layoutManagerSlide);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1,RecyclerView.HORIZONTAL, false);
//        slideRecycler.setLayoutManager(gridLayoutManager);

        SlideAdapter slideAdapter = null;


        try {
            JSONObject jsonObject = new JSONObject(s);
            String check=jsonObject.getString("success");
            Log.d("checkCondition", String.valueOf(check));
            Slide_recycler slide_recycler;
            if(check.equals("1")){

                JSONArray jsonArray=jsonObject.getJSONArray("data");
                int count=0;


                while(count<jsonArray.length()){

                    JSONObject JO=jsonArray.getJSONObject(count);
                    String introSlider_id= String.valueOf(JO.getInt("introSlider_id"));
                    String food_id= String.valueOf(JO.getInt("food_id"));
                    String iS_ImageUrl=JO.getString("iS_ImageUrl");

                    slide_recycler=new Slide_recycler();
                    slide_recycler.setSlideImageID(introSlider_id);
                    slide_recycler.setSlideImageUrl(iS_ImageUrl);
                    slide_recycler.setFoodID(food_id);

                    slide_list.add(slide_recycler);

                    if(count==0){


                        slideAdapter =new SlideAdapter(slide_list,getContext());

                        layoutManager=new CenterZoomLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                        slideRecycler.setLayoutManager(layoutManager);
                        final SnapHelper snapHelper = new LinearSnapHelper();
                        slideRecycler.setHasFixedSize(true);
                        snapHelper.attachToRecyclerView(slideRecycler);
                        slideRecycler.setAdapter(slideAdapter);

                    }
                    else {
                        slideAdapter.notifyDataSetChanged();
                    }

                    count++;


                }







            }
            else{
                Toast.makeText(getContext(),"This Email-ID is already registered.",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


    }


//    public void setFoodCardRecycler(String result){
//
//        try {
//            CardsAdapter myadapter = null;
//            JSONObject jsonObject = new JSONObject(result);
//            String check=jsonObject.getString("success");
//            Log.d("checkCondition", String.valueOf(check));
//
//            if(check.equals("1")){
//
//                JSONArray jsonArray=jsonObject.getJSONArray("data");
//                int count=0;
//
//                while(count<jsonArray.length()){
//
//                    JSONObject JO=jsonArray.getJSONObject(count);
//                    String foodcard_id= String.valueOf(JO.getInt("foodcard_id"));
//                    String food_id= String.valueOf(JO.getInt("food_id"));
//                    String cardImageUrl=JO.getString("imageUrl");
//                    String foodName=JO.getString("foodName");
//                    String foodPrice= String.valueOf(JO.getDouble("foodPrice"));
//                    String storeLocation=JO.getString("location");
//                    String any_other_info=JO.getString("any_other_info");
//
//                    single_card=new Single_Card();
//                    single_card.setFoodCard_id(foodcard_id);
//                    single_card.setFood_id(food_id);
//                    single_card.setImageUrl(cardImageUrl);
//                    single_card.setFoodName(foodName);
//                    single_card.setFoodPrice(foodPrice);
//                    single_card.setAddress(storeLocation);
//                    single_card.setAnyOtherInfo(any_other_info);
//                    list.add(single_card);
//
//                    if(count==0){
//                        myadapter=new CardsAdapter(list,getContext());
//                        layoutManager=new LinearLayoutManager(getContext());
//                        recyclerView.setLayoutManager(layoutManager);
//                        recyclerView.setAdapter(myadapter);
//                    }
//                    else {
//                        myadapter.notifyDataSetChanged();
//                    }
//
//                    count++;
//
//
//                }
//
//
//
//
//
//            }
//            else{
//                Toast.makeText(getContext(),"This Email-ID is already registered.",Toast.LENGTH_SHORT).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//
//        }
//
//
//  }



    private void slidersRetro() {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://newbizsite.000webhostapp.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Slider slider=retrofit.create(Slider.class);

        Call<SliderModal> call=slider.getSlider();


        call.enqueue(new Callback<SliderModal>() {
            @Override
            public void onResponse(Call<SliderModal> call, Response<SliderModal> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"MunnaBhai kuch Jhol hai!",Toast.LENGTH_SHORT).show();
                    return;
                }

                SliderModal sliderModal=response.body();

                if(Integer.parseInt(sliderModal.getSuccess())==1){
                        ArrayList<Slide_recycler> slide_recyclers=response.body().getSlider();

                  SlideAdapter  slideAdapter =new SlideAdapter(slide_recyclers,getContext());

                    layoutManager=new CenterZoomLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    slideRecycler.setLayoutManager(layoutManager);
                    final SnapHelper snapHelper = new LinearSnapHelper();
                    slideRecycler.setHasFixedSize(true);
                    snapHelper.attachToRecyclerView(slideRecycler);
                    slideRecycler.setAdapter(slideAdapter);

                }
            }

            @Override
            public void onFailure(Call<SliderModal> call, Throwable t) {

                Toast.makeText(getContext(),"Lafda :"+t.getMessage(),Toast.LENGTH_SHORT);
            }
        });

    }

  private void cardsRetro(){
      int cacheSize = 10 * 1024 * 1024; // 10 MB
      Cache cache = new Cache(getContext().getCacheDir(), cacheSize);

      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .cache(cache)
              .build();

      Retrofit retrofit=new Retrofit.Builder()
              .baseUrl("http://newbizsite.000webhostapp.com/")
              .client(okHttpClient)
              .addConverterFactory(GsonConverterFactory.create())
              .build();

      Cards cards=retrofit.create(Cards.class);

      Call<CardsModal> call=cards.getCards();

      call.enqueue(new Callback<CardsModal>() {
          @Override
          public void onResponse(Call<CardsModal> call, Response<CardsModal> response) {

              if(!response.isSuccessful()){
                  Toast.makeText(getContext(),"Kuch to gadbad hai Daya!",Toast.LENGTH_SHORT).show();
                  return;
              }

              CardsModal cards1=response.body();

              //karta hun yaar success ka status check

              ArrayList<Single_Card> cards2=response.body().getCards();

                CardsAdapter myadapter=new CardsAdapter(cards2,getContext());
                  layoutManager=new LinearLayoutManager(getContext());
                  recyclerView.setLayoutManager(layoutManager);
                  recyclerView.setAdapter(myadapter);

                  myadapter.notifyDataSetChanged();



          }

          @Override
          public void onFailure(Call<CardsModal> call, Throwable t) {
              Toast.makeText(getContext(),"Kuch to gadbad hai Jetha! "+t.getMessage() ,Toast.LENGTH_SHORT).show();

          }
      });
  }

}

