package com.example.newbiz;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserInfo";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_PWD = "pwd";

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

//    private SharedPreferences getSharedPreferences(String prefName, int private_mode) {
//        return getSharedPreferences(PREF_NAME,PRIVATE_MODE);
//    }

    public void createLoginSession(String name, String email,String address,String contact,String pwd){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_ADDRESS,address);
        editor.putString(KEY_CONTACT,contact);
        editor.putString(KEY_PWD,pwd);

        // commit changes
        editor.commit();
    }

    public  String getKeyName() {

        return pref.getString(KEY_NAME,null);
    }

    public  String getKeyEmail() {
        return pref.getString(KEY_EMAIL,null);
    }

    public  String getKeyAddress() {
        return pref.getString(KEY_ADDRESS,null);
    }

    public  String getKeyContact() {
        return pref.getString(KEY_CONTACT,null);
    }

    public  String getKeyPwd() {
        return pref.getString(KEY_PWD,null);
    }


    public void logout(Context context){
        pref.edit().clear().apply();
    }
}
