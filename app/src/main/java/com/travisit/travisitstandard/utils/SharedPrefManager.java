package com.travisit.travisitstandard.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.travisit.travisitstandard.model.User;

//@Singleton
public class SharedPrefManager {

    //Shared Preference field used to save and retrieve JSON string
    private SharedPreferences sharedPreferences;

    //Name of Shared Preference file
    private String PREFERENCES_FILE_NAME = "PREFERENCES_TRAVISIT_STANDARD";

//    @Inject
    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences("Preferences", 0);
    }

    /**
     * Saves object into the Preferences.
     *
     **/
    public void saveUser(User user){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("User", json);
        prefsEditor.commit();
    }
    /**
     * Saves object into the Preferences.
     *
     **/
//    public void saveUser(String token){
//        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
//        User emptyUser = new User(token);
//        Gson gson = new Gson();
//        String json = gson.toJson(emptyUser);
//        prefsEditor.putString("User", json);
//        prefsEditor.commit();
//    }
    public void savePasswordResetCode(String code){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("reset_code", code);
        prefsEditor.commit();
    }
    public String getPasswordResetCode(){
        return sharedPreferences.getString("reset_code", null);
    }
    public User getUser(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }
    public String getUserToken(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", "");
        User user = gson.fromJson(json, User.class);
        return user.getToken();
    }
    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
//    public void setNotificationsEnabled(boolean enabled){
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.putBoolean(NOTIFICATIONS_ENABLED, enabled);
//        edit.commit();
//    }
//
//    public boolean isNotificationsEnabled(){
//        return sharedPreferences.getBoolean(NOTIFICATIONS_ENABLED, true);
//    }



}
