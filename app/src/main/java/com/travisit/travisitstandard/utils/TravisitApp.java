package com.travisit.travisitstandard.utils;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class TravisitApp extends Application {
    private static TravisitApp APPINSTANCE=null;
    @Override
    public void onCreate() {
        super.onCreate();
        APPINSTANCE=this;
        Fresco.initialize(this);
    }
    public static synchronized TravisitApp getInstance(){
        synchronized (TravisitApp.class){
            if (APPINSTANCE==null){
                APPINSTANCE=new TravisitApp();
            }return APPINSTANCE;
        }
    }

}
