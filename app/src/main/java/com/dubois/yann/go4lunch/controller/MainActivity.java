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
import com.dubois.yann.go4lunch.controller.ui.place_details.PlaceDetailsActivity;
import com.dubois.yann.go4lunch.model.User;
import com.dubois.yann.go4lunch.model.UserChoice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    private static final int RC_LOCATION_PERM = 122;

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
                int menuId = item.getItemId();
                /* Use if/else cause switch can't be use with not final objects
                 * R.id.****** are not final objects
                 */
                if (menuId == R.id.nd_your_lunch) {
                    //Get user choice from database
                    mDatabase.collection("user_choice").whereEqualTo("userId", mCurrentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //Serialize result to UserChoice.class
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                UserChoice userChoice = documentSnapshot.toObject(UserChoice.class);
                                //Create intent
                                Intent detailActivity = new Intent(getApplicationContext(), PlaceDetailsActivity.class);
                                Bundle restaurantInformation = new Bundle();
                                assert userChoice != null;
                                restaurantInformation.putString("place_id", userChoice.getPlaceId());
                                detailActivity.putExtras(restaurantInformation);
                                startActivity(detailActivity);
                            }
                        }
                    });
                } else if (menuId == R.id.nd_logout) {
                    FirebaseAuth.getInstance().signOut();
                    /*
                     *  Return to LoginActivity after sign-out
                     *  Don't use finish() cause of SplashScreen
                     *  If loginActivity wasn't launch, stop the app
                     */
                    Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(mIntent);
                } else {
                    item.isChecked();
                    mBinding.drawerLayout.close();
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
        //Verify if user already exist to not delete his favorites restaurants
        mDatabase.collection("user").whereEqualTo("id", mCurrentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                User currentUser = new User(mCurrentUser.getUid(), mCurrentUser.getDisplayName(), Objects.requireNonNull(mCurrentUser.getPhotoUrl()).toString(), null);
                QuerySnapshot result = task.getResult();
                if (result.getDocuments().size() > 0){
                    DocumentSnapshot documentSnapshot;
                    documentSnapshot = result.getDocuments().get(0);   //get(0) cause we search only one user
                    User user = documentSnapshot.toObject(User.class);
                    //Verify if info are the same for user & mCurrentUser
                    assert user != null;
                    if (!Objects.equals(user.getUsername(), currentUser.getUsername()) || !Objects.equals(user.getPhotoURL(), currentUser.getPhotoURL())){
                        //Then modify info
                        user.setUsername(currentUser.getUsername());
                        user.setPhotoURL(currentUser.getPhotoURL());
                        //Update user
                        mDatabase.collection("user").document(user.getId()).set(user);
                    }
                }else{
                    //Add new user
                    mDatabase.collection("user").document(currentUser.getId()).set(currentUser);
                }
            }
        });

    }

}