package com.dubois.yann.go4lunch.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dubois.yann.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private final int RC_SIGN_IN = 1;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Get the las account connected to app
        FirebaseAuth mMAuth = FirebaseAuth.getInstance();
        currentUser = mMAuth.getCurrentUser();

        new Handler().postDelayed(() -> {
            Intent mIntent;
            if (currentUser != null){  //if an account was connected, launch MainActivity
                mIntent = new Intent(SplashScreen.this, MainActivity.class);
            }else{  //else launch LoginActivity
                mIntent = new Intent(SplashScreen.this, LoginActivity.class);
            }
            startActivity(mIntent);
            finish();
        }, 2000);
    }
}