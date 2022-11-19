package com.example.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    private  static int splash_time=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences(otp_page.pres_name,0);
                boolean haslogged=sharedPreferences.getBoolean("haslogged",false);
                if(haslogged){
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    finish();

                }else{

                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        },splash_time);
    }
}