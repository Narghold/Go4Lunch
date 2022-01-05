package com.dubois.yann.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.dubois.yann.go4lunch.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //Get the las account connected to app
        FirebaseAuth mMAuth = FirebaseAuth.getInstance();
        currentUser = mMAuth.getCurrentUser();

        Glide.with(this).load(currentUser.getPhotoUrl()).into(mBinding.imageView);

        mBinding.twName.setText(currentUser.getDisplayName());

        mBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                /*
                 *  Return to LoginActivity after sign-out
                 *  Don't use finish() cause of SplashScreen
                 *  If loginActivity wasn't launch, stop the app
                 */
                Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mIntent);
            }
        });
    }
}