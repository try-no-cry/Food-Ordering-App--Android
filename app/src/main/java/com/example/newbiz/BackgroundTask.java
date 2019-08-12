package com.example.newbiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class BackgroundTask extends AsyncTask<String,Void,String> {
AlertDialog.Builder builder;
View view;
    private static final String KEY_SUCCESS ="success" ;
    private static final String KEY_DATA ="data" ;
    Context context;
    String result="  ";
    String connstr="http://192.168.42.234/phpAndroid/";
   // private SharedPreferences sp;

    public BackgroundTask( Context context,View view)  {
        this.context=context;
        //varPass=(passData)context;
       // sp = activity.getSharedPreferences("keyName", Context.MODE_PRIVATE);
        this.view=view;

    }

//    passData varPass;
//    public interface passData{
//        void passThisData(String data);
//    }
    @Override

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Response");
        builder.setMessage(result);
        builder.create();
        builder.show();
      //  sp.edit().putString("result",result).apply();

        if(view!=null)
        ((TextView)view).setText(result);

    //    varPass.passThisData(result);
       // MyOrders.resultFromQuery=result;
        //


        //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... voids) {

        String sql=voids[0];
        String extension=voids[1];
        //can be: selectOrder.php,insert.php


        try {
            URL url=new URL(connstr+extension);
            HttpURLConnection  http= (HttpURLConnection) url.openConnection();
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
