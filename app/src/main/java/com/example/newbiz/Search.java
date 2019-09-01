package com.example.newbiz;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {

private SearchView searchView;
private RecyclerView searchRecyclerView;
private RecyclerView.Adapter adapter;
private RecyclerView.LayoutManager layoutManager;
private ArrayList<singleSearchInfo> list=new ArrayList<>();  //initialized to prevent null pointer error
    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_search, container, false);
        searchView=v.findViewById(R.id.simpleSearchView);
        searchRecyclerView=v.findViewById(R.id.searchRecyclerView);




        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
//                Toast.makeText(getContext(),query,Toast.LENGTH_SHORT).show();
                BackgroundTask backgroundTask=new BackgroundTask();
                backgroundTask.execute(query,"fillSearchList.php");
                //fire the query
                return false;  //idk why this returns false..it was by default
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
//                Toast.makeText(getContext(),newText,Toast.LENGTH_SHORT).show();

                return false;
            }
        });




           }



     public void setRecyclerView(String result){

        //parse JSON
         JSONObject jsonObject;
         JSONArray jsonArray;
         try {
             jsonObject=new JSONObject(result);

             String check=jsonObject.getString("success");
             Log.d("checkCondition", String.valueOf(check));

             if(check.equals("1")){

                 jsonArray=jsonObject.getJSONArray("data");
                 int count=0;
                 if(list.size()!=0)
                     list.clear();

                 String imageUrl,foodName,location,contact;
                 while(count<jsonArray.length()){

                     JSONObject JO=jsonArray.getJSONObject(count);

                     imageUrl=JO.getString("imageUrl");
                     foodName=JO.getString("foodName");
                     location=JO.getString("location");
                     contact=JO.getString("contact");

                     singleSearchInfo info=new singleSearchInfo();
                     info.setImageUrl(imageUrl);
                     info.setFoodName(foodName);
                     info.setLocation(location);
                     info.setCallToLocation_contact(contact);

                     list.add(info);
                     count++;

                 }


                 searchAdapter myadapter=new searchAdapter(list,getContext());
                 layoutManager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                 searchRecyclerView.setLayoutManager(layoutManager);
                 searchRecyclerView.setAdapter(myadapter);



             }

         } catch (JSONException e) {
             e.printStackTrace();
             Toast.makeText(getContext(),"No data related to your search was found.",Toast.LENGTH_LONG).show();
         }
         //segregate values
         //make an object of class singleSearchInfo
         //add to list



     }


     //adding to get my query executed

    public class BackgroundTask extends AsyncTask<String,Void,String> {
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
            setRecyclerView(s);
        }

        @Override
        protected String doInBackground(String... voids) {

            String searchQuery=voids[0];
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

                String data=  URLEncoder.encode("searchQuery","UTF-8")+"="+URLEncoder.encode(searchQuery,"UTF-8");

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






