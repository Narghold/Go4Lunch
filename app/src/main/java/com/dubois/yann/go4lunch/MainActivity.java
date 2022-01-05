package com.dubois.yann.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dubois.yann.go4lunch.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //AppBar
        setSupportActionBar(mBinding.topAppBar);
        mBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  mBinding.drawerLayout.open();
            }
        });

        //NavigationView
        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.nd_logout:
                        FirebaseAuth.getInstance().signOut();
                        /*
                         *  Return to LoginActivity after sign-out
                         *  Don't use finish() cause of SplashScreen
                         *  If loginActivity wasn't launch, stop the app
                         */
                        Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(mIntent);
                        break;
                    default:
                        item.isChecked();
                        mBinding.drawerLayout.close();
                        break;
                }
                return true;
            }
        });

        //Header Navigation Drawer
        View mHeaderView = mBinding.navView.getHeaderView(0);
        TextView hndName = mHeaderView.findViewById(R.id.hnd_name);
        TextView hndMail = mHeaderView.findViewById(R.id.hnd_mail);
        ImageView hndProfilePicture = mHeaderView.findViewById(R.id.hdn_profile_picture);
        hndName.setText(currentUser.getDisplayName());
        hndMail.setText(currentUser.getEmail());
        Glide.with(this).load(currentUser.getPhotoUrl()).circleCrop().into(hndProfilePicture);
        
    }
}