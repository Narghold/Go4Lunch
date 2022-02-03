package com.dubois.yann.go4lunch.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.dubois.yann.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashScreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int RC_LOCATION_PERM = 122;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Ask for location permissions
        askLocationPermission();

        //Get the las account connected to app
        FirebaseAuth mMAuth = FirebaseAuth.getInstance();
        currentUser = mMAuth.getCurrentUser();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void askLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION) || EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
            Toast.makeText(this, "Location granted", Toast.LENGTH_LONG).show();
            startNextAct();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_rq_location), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d("MapsFragment", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("MapsFragment", "onPermissionsDenied:" + requestCode + ":" + perms.size());
        EasyPermissions.requestPermissions(this, getString(R.string.rationale_rq_location_second), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void startNextAct(){
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