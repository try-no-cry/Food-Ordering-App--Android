package com.example.newbiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderPage extends AppCompatActivity {
private TextView tvFoodBillLabel;
private ImageView ivFoodImage;
private TextView tvFoodNameLabel,tvFoodName,
tvStdPriceLabel,tvStdPrice,
tvQuantityLabel,etQuantity,
tvAddressLabel,etAddress,
tvTaxesLabel,tvTaxes,
tvTotalLabel,tvTotal;
private Single_Card myFood;
    SharedPreferences prefs;

private float totalPrice;
private Button btnCalculateTotal,btnPlaceOrder;

SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

      hideKeyboard(this);
      manager=new SessionManager(OrderPage.this);
        prefs = getSharedPreferences("UserInfo",
                MODE_PRIVATE);

        tvFoodBillLabel=findViewById(R.id.tvFoodBillLabel);
        ivFoodImage=findViewById(R.id.ivFoodImage);
        tvFoodNameLabel=findViewById(R.id.tvFoodNameLabel);
        tvFoodName=findViewById(R.id.tvFoodName);
        tvStdPriceLabel=findViewById(R.id.tvStdPriceLabel);
        tvStdPrice=findViewById(R.id.tvStdPrice);
        tvQuantityLabel=findViewById(R.id.tvQuantityLabel);
        etQuantity=findViewById(R.id.etQuantity);
        tvAddressLabel=findViewById(R.id.tvAddressLabel);
        etAddress=findViewById(R.id.etAddress);
        tvTaxesLabel=findViewById(R.id.tvTaxesLabel);
        tvTaxes=findViewById(R.id.tvTaxes);
        tvTotalLabel=findViewById(R.id.tvTotalLabel);
        tvTotal=findViewById(R.id.tvTotal);
        btnCalculateTotal=findViewById(R.id.btnCalculateTotal);
        btnPlaceOrder=findViewById(R.id.btnPlaceOrder);


        if(prefs.getString("address",null)!=null){
            etAddress.setText(prefs.getString("address",""));
        }
        else{
            etAddress.setText("");

        }
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
         myFood= (Single_Card) bundle.getSerializable("single_card");

        Glide.with(this).load(myFood.getImageUrl()).into(ivFoodImage);
        tvFoodName.setText(myFood.getFoodName());
        tvStdPrice.setText("₹"+myFood.getFoodPrice());
        etQuantity.setText("1");

        tvTaxes.setText("GST & others");

        btnCalculateTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalPrice=Float.parseFloat(myFood.getFoodPrice())*Float.parseFloat(etQuantity.getText().toString());
//                        + Float.parseFloat(tvTaxes.getText().toString());
//                above line should be uncommented after putting real values
                tvTotal.setText("₹"+totalPrice);

            }

        });


        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyOrders myOrders=new MyOrders();
             //  MyOrders.BackgroundTask backgroundTask= myOrders.BackgroundTask(OrderPage.this,null);

                //trying to insert into the db
                int foodCardID=1;  //idhar kaise find karenge foodcard id   (crying)😭
                String userID= (prefs.getString("user_id",""));
                String supplyAddress=etAddress.getText().toString().trim();
                String foodName=myFood.getFoodName();
                float totalPrice=Float.parseFloat(myFood.getFoodPrice())*Float.parseFloat(etQuantity.getText().toString());
//                        + Float.parseFloat(tvTaxes.getText().toString());
                String status="Order Placed";

                Date c = Calendar.getInstance().getTime();
             //   String orderTime=c.toString();
                String[] s=c.toString().split(" ");
                String orderTime=s[3];

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String orderDate = df.format(c);


BackgroundTask backgroundTask=new BackgroundTask();
                String sql="INSERT INTO orders(foodcard_id,user_id,quantity,supplyAddress,totalPrice," +
                        "orderStatus,orderDate,orderTime)" +
                        " VALUES("+foodCardID +","+
                        Integer.parseInt(userID) +", "
                        +Float.parseFloat(etQuantity.getText().toString())+","
                        +"'"+ supplyAddress + "'"+","
                        +totalPrice  +","
                        + "'"+status+"'"+ ","
                        +"'"+ orderDate + "'"+ "," +
                        "'"+ orderTime + "'"+
                        ")" ;

//                String sql="SELECT * FROM orders";
                backgroundTask.execute(sql,"insert.php");


                Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_SHORT).show();
                //order will now move to my order fragment
                //write a query to move into my orders fragments table whic will eventually come into
                //fragment as card view with status of delivered/undelivered
                Toast.makeText(getApplicationContext(),"Go To 'My Orders' ",Toast.LENGTH_LONG).show();


//                finish();


            }
        });



    }

    public void finishThisAct(){
        finish();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }








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
            finishThisAct();
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
