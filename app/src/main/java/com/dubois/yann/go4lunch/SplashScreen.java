package com.dubois.yann.go4lunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Get the last account connected to app
        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(this);

        new Handler().postDelayed(() -> {
            Intent mIntent;
            if (mAccount != null){  //if an account was connected, launch MainActivity
                mIntent = new Intent(SplashScreen.this, MainActivity.class);
            }else{  //else launch LoginActivity
                mIntent = new Intent(SplashScreen.this, LoginActivity.class);
            }
            startActivity(mIntent);
            finish();
        }, 2000);
    }
}