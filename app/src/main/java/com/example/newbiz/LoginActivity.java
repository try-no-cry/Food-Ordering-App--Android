package com.example.newbiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.newbiz.MyOrders.CONNECTION;
import static com.example.newbiz.MyOrders.resultFromQuery;
import static com.example.newbiz.SignUp.MY_PREFS_NAME;

public class LoginActivity extends AppCompatActivity {
    private TextView tvLogin_title,tvForgetPwd,tvUName,tvPwd;
    private EditText etUName,etPwd;
    private Button btnLogin,btnGoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvLogin_title=findViewById(R.id.login_title);
        tvForgetPwd=findViewById(R.id.tvForgetPwd);
        etUName=findViewById(R.id.etUName);
        tvUName=findViewById(R.id.tvUName);
        tvPwd=findViewById(R.id.tvPwd);
        etPwd=findViewById(R.id.etPwd);
        btnLogin=findViewById(R.id.btnLogin);
        btnGoToSignUp=findViewById(R.id.btnGoToSignUp);

        btnGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUp.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etUName.getText().toString().trim();
                String pwd=etPwd.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(email.isEmpty() || pwd.isEmpty()){
//                    Toast.makeText(LoginActivity.this,"Please fill the details.",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view,"Please fill the details.",Snackbar.LENGTH_SHORT).show();
                }

                else{
                    if(!email.matches(emailPattern)){
                            //email syntax verify
                        Snackbar.make(view,"Invalid Email-ID.",Snackbar.LENGTH_LONG).show();

                    }
                    else{
                        ///fire the query to check correctness
                        BackgroundTask backgroundTask=new BackgroundTask();
                        backgroundTask.execute(email,pwd,"checkLogin.php");
                    }
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
            resultFromQuery(s);
            // JSONArray jsonArray=jsonObject.getJSONArray("success");

        }

        @Override
        protected String doInBackground(String... voids) {

            String email=voids[0];
            String pwd=voids[1];
            String extension=voids[2];


            try {
                URL url=new URL(connstr+extension);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops=http.getOutputStream();

                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"utf-8"));



                String data =  URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8");

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



    private void resultFromQuery(String data) {


        try {
            JSONObject jsonObject=new JSONObject(data);

            String check=jsonObject.getString("success");
            Log.d("checkCondition", String.valueOf(check));

            if(check.equals("1")){
                //login successful

                JSONArray array=jsonObject.getJSONArray("data");


                JSONObject JO=array.getJSONObject(0);
                String name=JO.getString("name");
                String email=JO.getString("email");
                String pwd=JO.getString("pwd");
                String address=JO.getString("address");
                String contact=JO.getString("contact");


                SessionManager manager=new SessionManager(getApplicationContext());
                manager.createLoginSession(name,email,address,contact,pwd);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                editor.putBoolean("loggedIn", true);  //vvi variable
//                editor.putString("name", name);
//                editor.putString("email", email);
//                editor.putString("address", address);
//                editor.putString("contact", contact);
//                editor.putString("pwd", pwd);
//
//                editor.apply();


                startActivity(new Intent(getApplicationContext(),MainActivity.class));




            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid Credentials", Toast.LENGTH_SHORT).show();
            }



        } catch (JSONException e) {
            e.printStackTrace();
//            Toast.makeText(getApplicationContext(),"Please check your internet connection.",Toast.LENGTH_SHORT).show();
            Log.i("errorMessage",e.getMessage());
        }

    }

}
