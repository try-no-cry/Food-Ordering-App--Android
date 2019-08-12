package com.example.newbiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
//        implements Home.fromHomeFragToactivity, CardsAdapter.ItemSelected
{
private BottomNavigationView menu_b;
boolean doubleBackToExitPressedOnce = false;
 boolean at_home= false;
 private  MyOrders myOrders;

//ArrayList<Single_Card> cardsList=new ArrayList<>();
//int CardClickPos;



    public static final int FRAGMENT_HOME= 1;
    public static final int FRAGMENT_MYORDERS= 2;
    public static final int FRAGMENT_SEARCH= 3;
    public static final int FRAGMENT_MYACCOUNT=4;
    private Object passtoMyOrders;


    passtoMyOrders varToMO;
    public interface passtoMyOrders{
        void passIt(String s);
    }

    public void setListener(passtoMyOrders listener)
    {
        varToMO = listener ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(FRAGMENT_HOME);

       myOrders=new MyOrders();




       menu_b=findViewById(R.id.bottom_navigation);

       menu_b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               switch (menuItem.getItemId()) {
                   case R.id.home:
                      // Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();
                       setFragment(FRAGMENT_HOME);
                       break;
                   case R.id.myorders:

                       if(MyOrders.resultFromQuery!=null || MyOrders.resultFromQuery!=""){
                           setFragment(FRAGMENT_MYORDERS);
                       }

                       //Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();

                       break;

                   case R.id.search:
                       //Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();
                       setFragment(FRAGMENT_SEARCH);
                       break;

                   case R.id.myaccount:
                       //Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();
                       setFragment(FRAGMENT_MYACCOUNT);
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

            case FRAGMENT_HOME:
                ft.replace(R.id.main_activity_yes,new Home(),"HOME_FRAGMENT"); break;
                //ft.add(new Home(),"HOME_FRAGMENT");break;

            case FRAGMENT_MYORDERS:
               ft.replace(R.id.main_activity_yes,new MyOrders(),"MYORDERS_FRAGMENT"); break;
            //ft.add(new MyOrders(),"MYORDERS_FRAGMENT");break;
                //startActivity(new Intent(this,MyOrdersActivity.class)); break;

            case FRAGMENT_SEARCH:
               ft.replace(R.id.main_activity_yes,new Search(),"SEARCH_FRAGMENT");break;
                //ft.add(new Search(),"SEARCH_FRAGMENT");break;

            case  FRAGMENT_MYACCOUNT:
                ft.replace(R.id.main_activity_yes,new MyAccount(),"MYACCOUNT_FRAGMENT"); break;
               // ft.add(new MyAccount(),"MYACCOUNT_FRAGMENT");break;

            default: ft.replace(R.id.main_activity_yes,new Home(),"HOME_FRAGMENT"); break;

        }
            ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);

           // ft.addToBackStack(null);
            ft.commit();
        getSupportFragmentManager().executePendingTransactions();

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
////
//    @Override
//    public void passThisData(final String data) {
//        Bundle bundle = new Bundle();
//        bundle.putString("data",data);
//// set Fragmentclass Arguments
//        MyOrders fragobj = new MyOrders();
//        fragobj.setArguments(bundle);
//        MyOrders.resultFromQuery=data;
////        setListener((MainActivity.passtoMyOrders) passtoMyOrders);
////
////      varToMO.passIt(data);
//       // myOrders.setTextView(data);
//
//
//
//
//    }


//    @Override
//    public void PassToAcivity(ArrayList<Single_Card> list) {
//        cardsList=new ArrayList<>(list);
//
//
//
//    }
//
//    @Override
//    public void onItemClick(int index) {
//        Toast.makeText(this,cardsList.get(index).getFoodName(),Toast.LENGTH_SHORT).show();
//
//    }
}
