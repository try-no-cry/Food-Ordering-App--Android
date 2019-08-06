package com.example.newbiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private BottomNavigationView menu_b;
boolean doubleBackToExitPressedOnce = false;
 boolean at_home= false;


    public static final int FRAGMENT_MESSAGE = 1;
    public static final int FRAGMENT_SHOP= 2;
    public static final int FRAGMENT_HOME= 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(FRAGMENT_HOME);





       menu_b=findViewById(R.id.bottom_navigation);

       menu_b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               switch (menuItem.getItemId()) {
                   case R.id.shop:
                      // Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();
                       setFragment(FRAGMENT_SHOP);
                       break;
                   case R.id.home:
                       //Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();
                       setFragment(FRAGMENT_HOME);
                       break;

                   case R.id.message:
                       //Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();
                       setFragment(FRAGMENT_MESSAGE);
                       break;
               }
                   return true;

           }
       });


    }


    public void setFragment(int frag){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        switch(frag){

            case FRAGMENT_MESSAGE:
                ft.replace(R.id.main_activity_yes,new Message(),"MESSAGE_FRAGMENT"); break;

            case FRAGMENT_SHOP:ft.replace(R.id.main_activity_yes,new Shop(),"SHOP_FRAGMENT"); break;

            default: ft.replace(R.id.main_activity_yes,new Home(),"HOME_FRAGMENT"); break;

        }
        ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);

       // ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onBackPressed() {


        Home myFragment= (Home) getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");

        //after first back press will come at home frag
        if(myFragment == null ){

            setFragment(FRAGMENT_HOME);

        }
        else{

            if(doubleBackToExitPressedOnce){
                super.onBackPressed();
                return;

            }


            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }




    }
}
