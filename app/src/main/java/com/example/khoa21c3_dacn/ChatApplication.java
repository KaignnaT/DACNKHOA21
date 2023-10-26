package com.example.khoa21c3_dacn;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class ChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
