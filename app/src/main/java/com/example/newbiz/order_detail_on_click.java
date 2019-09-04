package com.example.newbiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

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

import javax.net.ssl.SNIHostName;

public class order_detail_on_click extends AppCompatActivity {
    private TextView tvStatusLabel_Ordered,tvFoodStatus_Ordered;
    private ImageView ivFoodImage_Ordered;
    private TextView tvFoodNameLabel_Ordered,tvFoodName_Ordered,
            tvStdPriceLabel_Ordered,tvStdPrice_Ordered,
            tvQuantityLabel_Ordered,tvQuantity_Ordered,
            tvAddressLabel_Ordered,tvAddress_Ordered,
            tvTaxesLabel_Ordered,tvTaxes_Ordered,
            tvTotalLabel_Ordered,tvTotal_Ordered;
    private Single_Card myFood_Ordered;
    SharedPreferences prefs;

    private Button btnCancelOrder_Ordered;
    private String order_id,foodName,foodPrice,imageUrl,status,orderDate,address,totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_on_click);

        prefs = getSharedPreferences("UserInfo",
                MODE_PRIVATE);

        tvStatusLabel_Ordered=findViewById(R.id.tvStatusLabel_Ordered);
        tvFoodStatus_Ordered=findViewById(R.id.tvFoodStatus_Ordered);

        ivFoodImage_Ordered=findViewById(R.id.ivFoodImage_Ordered);
        tvFoodNameLabel_Ordered=findViewById(R.id.tvFoodNameLabel_Ordered);
        tvFoodName_Ordered=findViewById(R.id.tvFoodName_Ordered);
        tvStdPriceLabel_Ordered=findViewById(R.id.tvStdPriceLabel_Ordered);
        tvStdPrice_Ordered=findViewById(R.id.tvStdPrice_Ordered);
        tvQuantityLabel_Ordered=findViewById(R.id.tvQuantityLabel_Ordered);
        tvQuantity_Ordered=findViewById(R.id.tvQuantity_Ordered);
        tvAddressLabel_Ordered=findViewById(R.id.tvAddressLabel_Ordered);
        tvAddress_Ordered=findViewById(R.id.tvAddress_Ordered);
//        tvTaxesLabel_Ordered=findViewById(R.id.tvTaxesLabel_Ordered);
//        tvTaxes_Ordered=findViewById(R.id.tvTaxes_Ordered);
        tvTotalLabel_Ordered=findViewById(R.id.tvTotalLabel_Ordered);
        tvTotal_Ordered=findViewById(R.id.tvTotal_Ordered);

        btnCancelOrder_Ordered=findViewById(R.id.btnCancelOrder_Ordered);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity

        order_id=getIntent().getStringExtra("order_id");
         foodName=getIntent().getStringExtra("foodName");
         orderDate=getIntent().getStringExtra("orderDate");
         imageUrl=getIntent().getStringExtra("imageUrl");
         status=getIntent().getStringExtra("status");

         BackgroundTask backgroundTask=new BackgroundTask();
         backgroundTask.execute(order_id,"getDetailsForClickedOrdered.php");
        //get address,foodPrice,totalPrice fro the order

        //only thing that gets updated is order status
        //call fn that sets all these values after query

        btnCancelOrder_Ordered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show alert dialog to confirm the cancellation

                if(status.equals("Cancelled")){
                    Snackbar.make(view,"This order is already cancelled.", Snackbar.LENGTH_LONG).show();
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(order_detail_on_click.this);
                    builder.setTitle("Confirm Cancellation");
                    builder.setMessage("Are you sure of cancelling this order?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //go to implement to cancel this order
                            //also inform the admin
                            BackgroundTask backgroundTask1 = new BackgroundTask();
                            backgroundTask1.execute(order_id, "cancelThisOrder.php");
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //go to implement to cancel this order
                            //also inform the admin

                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


            }
        });





    }


    public class BackgroundTask extends AsyncTask<String,Void,String> {
        AlertDialog.Builder builder;
        private static final String KEY_SUCCESS ="success" ;
        private static final String KEY_DATA ="data";
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
            setAllField(s);

        }

        @Override
        protected String doInBackground(String... voids) {

            String order_id=voids[0];


            String extension=voids[1];


            try {
                URL url=new URL(connstr+extension);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops=http.getOutputStream();

                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"utf-8"));


                String data=  URLEncoder.encode("order_id","UTF-8")+"="+ URLEncoder.encode(order_id,"UTF-8");

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home: finish(); break;
//            default:
//        }
        finish();
        return super.onOptionsItemSelected(item);

    }

    private void setAllField(String s) {



        //extract data from the JSON string

        try {
            JSONObject jsonObject=new JSONObject(s);
            // JSONArray jsonArray=jsonObject.getJSONArray("success");
            String check=jsonObject.getString("success");
            Log.d("checkCondition", String.valueOf(check));

            if(check.equals("3")){


                finish();

            }
            else

            if(check.equals("1")){



                //JSONArray jsonArray=jsonObject.getJSONArray("data");
               JSONArray jsonArray=jsonObject.getJSONArray("data");
                int count=0;


                String supplyAddress,totalPrice,quantity;

                    JSONObject JO=jsonArray.getJSONObject(count);
                    supplyAddress=JO.getString("supplyAddress");
                    totalPrice=JO.getString("totalPrice");
                    quantity=JO.getString("quantity");
                    foodPrice=JO.getString("foodPrice");
                    tvFoodStatus_Ordered.setText(JO.getString("orderStatus"));
                Glide.with(getApplicationContext()).load(imageUrl).into(ivFoodImage_Ordered);
                tvFoodName_Ordered.setText(foodName);
                tvStdPrice_Ordered.setText(foodPrice);
                tvQuantity_Ordered.setText(quantity);
                tvAddress_Ordered.setText(supplyAddress);
                tvTotal_Ordered.setText("₹"+totalPrice);







            }
            else  {
                //show some error message for not fetching the orders
                Toast.makeText(getApplicationContext(),"Order could not be fetched. Please check your internet connection.",Toast.LENGTH_LONG).show();
            }



        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Couldn't connect. Try Again",Toast.LENGTH_LONG).show();
        }
        //tvMyOrders.setText(data);

    }

}
