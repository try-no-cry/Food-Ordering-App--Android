package com.example.newbiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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

import static com.example.newbiz.MainActivity.FRAGMENT_MYACCOUNT;

public class EditInfo extends AppCompatActivity {
    private TextView editPage_title,tvEditEmail,tvEditName,tvEditContact,tvEditAddress;
    private EditText etEditEmail,etEditName,etEditContact,etEditAddress;
    private Button btn_updateDetails;
    private SharedPreferences prefs;
    private String name,email,address,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);

        //we'll come here only if user is logged in
        editPage_title=findViewById(R.id.editPage_title);


        etEditName=findViewById(R.id.etEditName);
//        etEditEmail=findViewById(R.id.etEditEmail);
        etEditAddress=findViewById(R.id.etEditAddress);
        etEditContact=findViewById(R.id.etEditContact);



        tvEditName=findViewById(R.id.tvEditName);
//        tvEditEmail=findViewById(R.id.tvEditEmail);
        tvEditAddress=findViewById(R.id.tvEditAddress);
        tvEditContact=findViewById(R.id.tvEditContact);
        btn_updateDetails=findViewById(R.id.btn_updateDetails);

        etEditName.setText(prefs.getString("name",""));
        etEditAddress.setText(prefs.getString("address",""));
        etEditContact.setText(prefs.getString("contact",""));
//        etEditEmail.setText(prefs.getString("email",""));


        btn_updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sql to update
                name=etEditName.getText().toString().trim();
                 email=prefs.getString("email","");
                 address=etEditAddress.getText().toString().trim();
                contact=etEditContact.getText().toString().trim();


                if(name.isEmpty()  || contact.isEmpty()){
                    Snackbar.make(view,"Please fill required Details",Snackbar.LENGTH_LONG).show();


                }else{
                    BackgroundTask backgroundTask=new BackgroundTask();
                    backgroundTask.execute(name,email,address,contact,"updateUserInfo.php");

                }



            }
        });


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

            try {
                JSONObject jsonObject = new JSONObject(s);
                String check=jsonObject.getString("success");
                Log.d("checkCondition", String.valueOf(check));

                if(check.equals("1")){

                    SessionManager manager=new SessionManager(getApplicationContext());
                    prefs.edit().putString("name",name);
                    prefs.edit().putString("email",email);
                    prefs.edit().putString("address",address);
                    prefs.edit().putString("contact",contact);

                    while (!prefs.edit().commit()){
                        ProgressBar progressBar=new ProgressBar(getApplicationContext());
                        progressBar.setIndeterminate(true);
//                        progressBar.setIndeterminateDrawable(R.drawable.custom_progress_bar);

                    }
                    Toast.makeText(getApplicationContext(),"Info Updated.",Toast.LENGTH_SHORT).show();


                   finish();



                }
                else{
                    Toast.makeText(getApplicationContext(),"Error. Try Again.",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error. Try Again.",Toast.LENGTH_SHORT).show();


            }
            // JSONArray jsonArray=jsonObject.getJSONArray("success");

        }

        @Override
        protected String doInBackground(String... voids) {

            String name=voids[0];
            String email=voids[1];   //no editing to email as it is primary key
            String address=voids[2];
            String contact=voids[3];
            String extension=voids[4];
            String pwd=prefs.getString("pwd",""); //sending pwd to verify the current user


            try {
                URL url=new URL(connstr+extension);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops=http.getOutputStream();

                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"utf-8"));


                String data=  URLEncoder.encode("name","UTF-8")+"="+ URLEncoder.encode(name,"UTF-8");

                data +="&" +  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8");
                data +="&" +  URLEncoder.encode("address","UTF-8")+"="+ URLEncoder.encode(address,"UTF-8");
                data +="&" +  URLEncoder.encode("contact","UTF-8")+"="+ URLEncoder.encode(contact,"UTF-8");
                data +="&" +  URLEncoder.encode("pwd","UTF-8")+"="+ URLEncoder.encode(pwd,"UTF-8");



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
