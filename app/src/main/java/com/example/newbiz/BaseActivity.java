package com.example.newbiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isOnline()) {
            //do whatever you want to do

        } else {
            try {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BaseActivity.this);
                builder.setTitle("Network Error");
                builder.setMessage("Please check your network conection");
                builder.setCancelable(false);


                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onResume();
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
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
//            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
