package com.example.khoa21c3_dacn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class GiaodienmoApp extends AppCompatActivity {

    private long delayMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaodienmo_app);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GiaodienmoApp.this, Dangnhap.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}