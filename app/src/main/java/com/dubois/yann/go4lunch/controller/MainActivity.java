package com.dubois.yann.go4lunch.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.databinding.ActivityMainBinding;
import com.dubois.yann.go4lunch.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    private static final int RC_LOCATION_PERM = 122;

    private final String USER_KEY = "user";

    FirebaseUser mCurrentUser;
    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //Get the last account connected to app
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        //Get database instance
        mDatabase = FirebaseFirestore.getInstance();
        addUserToDatabase();

        //AppBar
        setSupportActionBar(mBinding.topAppBar);
        mBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.drawerLayout.open();
            }
        });

        //NavigationView
        mBinding.navigationDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
        View mHeaderView = mBinding.navigationDrawer.getHeaderView(0);
        TextView hndName = mHeaderView.findViewById(R.id.hnd_name);
        TextView hndMail = mHeaderView.findViewById(R.id.hnd_mail);
        ImageView hndProfilePicture = mHeaderView.findViewById(R.id.hdn_profile_picture);
        hndName.setText(mCurrentUser.getDisplayName());
        hndMail.setText(mCurrentUser.getEmail());
        Glide.with(this).load(mCurrentUser.getPhotoUrl()).circleCrop().into(hndProfilePicture);

        //Bottom navigation
        NavHostFragment mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fr_navigation);
        assert mNavHostFragment != null;
        NavController mNavController = mNavHostFragment.getNavController();
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, mNavController);
    }

    private void addUserToDatabase() {
        User user = new User(mCurrentUser.getUid(), mCurrentUser.getDisplayName(), Objects.requireNonNull(mCurrentUser.getPhotoUrl()).toString());
        //If user already exists, update information
        mDatabase.collection(USER_KEY).document(user.getId()).set(user);
    }

}