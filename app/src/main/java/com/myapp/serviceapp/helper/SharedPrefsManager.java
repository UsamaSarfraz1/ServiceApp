package com.myapp.serviceapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.myapp.serviceapp.model.User;

public class SharedPrefsManager {
    private static final String PREFS_NAME = "com.service.app";
    private static final String KEY_ROLE = "role";
    private static final String KEY_USER = "user";

    private Gson gson;

    private SharedPreferences sharedPrefs;

    public SharedPrefsManager(Context context) {
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson=new Gson();
    }

    public void saveRole(String role) {
        sharedPrefs.edit().putString(KEY_ROLE, role).apply();
    }


    public String getRole() {
        return sharedPrefs.getString(KEY_ROLE, null);
    }

    public void setUserStatus(String status){
        User user=getUser();
        user.setStatus(status);
        saveUser(user);
    }



    public void saveUser(User user) {
        String userInfo=gson.toJson(user);
         sharedPrefs.edit().putString(KEY_USER, userInfo).apply();
    }

    public User getUser() {
        String userInfo=sharedPrefs.getString(KEY_USER, null);
        return gson.fromJson(userInfo,User.class);
    }
}
