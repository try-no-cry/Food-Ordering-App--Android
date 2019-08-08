package com.example.newbiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class OrderPage extends AppCompatActivity {
private TextView tvFoodBillLabel;
private ImageView ivFoodImage;
private TextView tvFoodNameLabel,tvFoodName,
tvStdPriceLabel,tvStdPrice,
tvQuantityLabel,etQuantity,
tvAddressLabel,etAddress,
tvTaxesLabel,tvTaxes,
tvTotalLabel,tvTotal;

private Button btnCalculateTotal,btnPlaceOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

      hideKeyboard(this);


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


        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        final Single_Card myFood= (Single_Card) bundle.getSerializable("single_card");

        Glide.with(this).load(myFood.getImageUrl()).into(ivFoodImage);
        tvFoodName.setText(myFood.getFoodName());
        tvStdPrice.setText("₹"+myFood.getFoodPrice());
        etQuantity.setText("1");
        etAddress.setText("get address if login else ask to login");
        tvTaxes.setText("GST & others");

        btnCalculateTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float totalPrice=Float.parseFloat(myFood.getFoodPrice())*Float.parseFloat(etQuantity.getText().toString());
//                        + Float.parseFloat(tvTaxes.getText().toString());
//                above line should be uncommented after putting real values
                tvTotal.setText("₹"+totalPrice);

            }

        });


        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_SHORT).show();
                //order will now move to my order fragment
                //write a query to move into my orders fragments table whic will eventually come into
                //fragment as card view with status of delivered/undelivered
                Toast.makeText(getApplicationContext(),"Go To 'My Orders' ",Toast.LENGTH_LONG).show();


                finish();


            }
        });



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
}
