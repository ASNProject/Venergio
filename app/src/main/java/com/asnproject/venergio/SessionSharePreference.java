package com.asnproject.venergio;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionSharePreference {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int private_mode = 0;
    private static final String PREF_NAME= "Venergio";

    SessionSharePreference(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, private_mode);
        editor = pref.edit();
    }

    //SharePreference Auto Login
    public void setUsername (String username){
        editor.putString("username", username);
        editor.commit();
    }
    public String getUsername(){
        return pref.getString("username", null);
    }
    public void setPassword (String password){
        editor.putString("password", password);
        editor.commit();
    }
    public String getPassword (){
        return pref.getString("password", null);
    }
    //////////////////////////////////////////////////////


}
