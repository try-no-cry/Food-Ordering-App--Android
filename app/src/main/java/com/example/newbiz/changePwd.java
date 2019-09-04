package com.example.newbiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class changePwd extends BaseActivity {
private TextView tvOldPwd,tvNewPwd,tvNewPwdConf;
private EditText etOldPwd,etNewPwd,etNewPwdConf;
private Button btnChangePassword;
 SharedPreferences prefs;
    private String newPwdConf,oldPwd,newPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        tvOldPwd=findViewById(R.id.tvOldPwd);
        tvNewPwd=findViewById(R.id.tvNewPwd);
        tvNewPwdConf=findViewById(R.id.tvNewPwdConf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etOldPwd=findViewById(R.id.etOldPwd);
        etNewPwd=findViewById(R.id.etNewPwd);
        etNewPwdConf=findViewById(R.id.etNewPwdConf);
        btnChangePassword=findViewById(R.id.btnChangePassword);
        prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);


        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPwd=etOldPwd.getText().toString().trim();
               newPwd=etNewPwd.getText().toString().trim();
                newPwdConf=etNewPwdConf.getText().toString().trim();

                if(oldPwd.isEmpty() || newPwd.isEmpty() || newPwdConf.isEmpty()){
                    Snackbar.make(view,"Please fill the requirements.",Snackbar.LENGTH_LONG).show();
                }
                else if(!newPwd.equals(newPwdConf)) {
                    Snackbar.make(view,"New Password And Confirmed New Password do not match.",Snackbar.LENGTH_LONG).show();

                }
                else {
                    BackgroundTask backgroundTask=new BackgroundTask();
                    backgroundTask.execute(oldPwd,newPwd,"changePwd.php");
                }




            }
        });


    }


    public class BackgroundTask extends AsyncTask<String,Void,String> {
        AlertDialog.Builder builder;
        private static final String KEY_SUCCESS ="success" ;
        private static final String KEY_DATA ="data" ;
        String result="  ";
        String connstr= MyOrders.CONNECTION;
//        +"/phpAndroid/";

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

                    Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_SHORT).show();
                    prefs.edit().clear().apply();

                  SessionManager  manager=new SessionManager(getApplicationContext());
                    manager.createLoginSession(prefs.getString("user_id",""),prefs.getString("name",""),prefs.getString("email",""),prefs.getString("address",""),prefs.getString("contact",""),newPwd);
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Check Your Inputs",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error. Try Again.",Toast.LENGTH_SHORT).show();


            }
            // JSONArray jsonArray=jsonObject.getJSONArray("success");

        }

        @Override
        protected String doInBackground(String... voids) {

            String pwd=voids[0];
            String newPwd=voids[1];
            String email=prefs.getString("email","");


            String extension=voids[2];


            try {
                URL url=new URL(connstr+extension);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops=http.getOutputStream();

                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"utf-8"));


                String data=  URLEncoder.encode("newPwd","UTF-8")+"="+ URLEncoder.encode(newPwd,"UTF-8");
                data +="&" +  URLEncoder.encode("pwd","UTF-8")+"="+ URLEncoder.encode(pwd,"UTF-8");
                data +="&" +  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8");


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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()){
//            case  R.id.home:finish(); break;
//        }

        finish();

        return super.onOptionsItemSelected(item);

    }

}
