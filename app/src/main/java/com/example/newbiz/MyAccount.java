package com.example.newbiz;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccount extends Fragment {
private TextView tvGreeting,tvEmail_Account,tvContact_Account,tvAddress_Account,tvName_Account;
private Button btnEditInfo,btnChangePwd,btnLogin_Account,btnSignUp_Account;
private LinearLayout loggedIn,loggedOut;

SharedPreferences prefs;
    public MyAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_my_account, container, false);
        tvGreeting=v.findViewById(R.id.tvGreeting);


        tvEmail_Account=v.findViewById(R.id.tvEmail_Account);
        tvContact_Account=v.findViewById(R.id.tvContact_Account);
        tvAddress_Account=v.findViewById(R.id.tvLocation_Account);
        tvName_Account=v.findViewById(R.id.tvName_Account);
        btnEditInfo=v.findViewById(R.id.btnEditInfo);
        btnChangePwd=v.findViewById(R.id.btnChangePassword);
        loggedIn=v.findViewById(R.id.loggedInView);
        loggedOut=v.findViewById(R.id.loggedOutView);
        btnLogin_Account=v.findViewById(R.id.btnLogin_Account);
        btnSignUp_Account=v.findViewById(R.id.btnSignUp_Account);




        return v;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        setUserVisibleHint(true);
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
menu.clear();
        inflater.inflate(R.menu.menu_top_bar,menu);
       MenuItem loginMenu= menu.findItem(R.id.login);
       MenuItem logoutMenu=menu.findItem(R.id.logout);
        if(prefs.getString("email","")!=""){
            loginMenu.setVisible(false);
            logoutMenu.setVisible(true);
        }
        else {
            loginMenu.setVisible(true);
            logoutMenu.setVisible(false);
        }

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
        setHasOptionsMenu(true);

        super.onResume();
        checkNetworkConnection();
        prefs = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);

        if(!prefs.getString("email", "").equals("")){

            loggedIn.setVisibility(View.VISIBLE);
            loggedOut.setVisibility(View.GONE);


            setData();


        }
        else{
            loggedIn.setVisibility(View.GONE);
            loggedOut.setVisibility(View.VISIBLE);

        }


//        setData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });

        btnSignUp_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),SignUp.class));
            }
        });


        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfo();
            }
        });

        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),changePwd.class));
            }
        });

    }


    private void editInfo() {
        //custom layout for dialog box
        startActivity(new Intent(getContext(),EditInfo.class));


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

    public void setData(){

        tvName_Account.setText(prefs.getString("name",""));
        tvAddress_Account.setText(prefs.getString("address",""));
        tvContact_Account.setText(prefs.getString("contact",""));
        tvEmail_Account.setText(prefs.getString("email",""));


    }

}
