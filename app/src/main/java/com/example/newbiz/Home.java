package com.example.newbiz;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.transition.Slide;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment
//        implements CardsAdapter.ItemSelected
{
private  Single_Card single_card;
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

        return inflater.inflate(R.layout.fragment_home, container, false);
}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.cardsList);
        slideRecycler=view.findViewById(R.id.slideRecycler);
        //slide start

        BackgroundTaskSlider backgroundTaskSlider=new BackgroundTaskSlider();
        backgroundTaskSlider.execute("fetchIntroSlider.php");


       BackgroundTaskCard backgroundTask=new BackgroundTaskCard();
       backgroundTask.execute("fillCardRecyclerView.php");




    }



    public class BackgroundTaskCard extends AsyncTask<String,Void,String> {
        AlertDialog.Builder builder;
        private static final String KEY_SUCCESS ="success" ;
        private static final String KEY_DATA ="data" ;
        String result="  ";
        String connstr= MyOrders.CONNECTION +"/phpAndroid/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Add progress dialog to show insertion
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            setFoodCardRecycler(s);
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

    public class BackgroundTaskSlider extends AsyncTask<String,Void,String> {
        AlertDialog.Builder builder;
        private static final String KEY_SUCCESS ="success" ;
        private static final String KEY_DATA ="data" ;
        String result="  ";
        String connstr= MyOrders.CONNECTION +"/phpAndroid/";

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

                    count++;


                }


                SlideAdapter slideAdapter=new SlideAdapter(slide_list,getContext());

                layoutManager=new CenterZoomLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                slideRecycler.setLayoutManager(layoutManager);

                final SnapHelper snapHelper = new LinearSnapHelper();
                slideRecycler.setHasFixedSize(true);
                snapHelper.attachToRecyclerView(slideRecycler);
                slideRecycler.setAdapter(slideAdapter);



            }
            else{
                Toast.makeText(getContext(),"This Email-ID is already registered.",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


    }


    public void setFoodCardRecycler(String result){

        try {
            JSONObject jsonObject = new JSONObject(result);
            String check=jsonObject.getString("success");
            Log.d("checkCondition", String.valueOf(check));

            if(check.equals("1")){

                JSONArray jsonArray=jsonObject.getJSONArray("data");
                int count=0;

                while(count<jsonArray.length()){

                    JSONObject JO=jsonArray.getJSONObject(count);
                    String foodcard_id= String.valueOf(JO.getInt("foodcard_id"));
                    String food_id= String.valueOf(JO.getInt("food_id"));
                    String cardImageUrl=JO.getString("imageUrl");
                    String foodName=JO.getString("foodName");
                    String foodPrice= String.valueOf(JO.getDouble("foodPrice"));
                    String storeLocation=JO.getString("location");
                    String any_other_info=JO.getString("any_other_info");

                    single_card=new Single_Card();
                    single_card.setFoodCard_id(foodcard_id);
                    single_card.setFood_id(food_id);
                    single_card.setImageUrl(cardImageUrl);
                    single_card.setFoodName(foodName);
                    single_card.setFoodPrice(foodPrice);
                    single_card.setAddress(storeLocation);
                    single_card.setAnyOtherInfo(any_other_info);
                    list.add(single_card);

                    count++;


                }

                CardsAdapter myadapter=new CardsAdapter(list,getContext());
                layoutManager=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myadapter);



            }
            else{
                Toast.makeText(getContext(),"This Email-ID is already registered.",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


  }

}

