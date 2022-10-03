package com.dubois.yann.go4lunch.controller.ui.place_details;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dubois.yann.go4lunch.Go4Lunch;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.databinding.ActivityPlaceDetailsBinding;
import com.dubois.yann.go4lunch.model.RestaurantChoice;
import com.dubois.yann.go4lunch.model.User;
import com.dubois.yann.go4lunch.model.UserChoice;
import com.dubois.yann.go4lunch.model.details.RestaurantDetails;
import com.dubois.yann.go4lunch.model.details.ResultDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailsActivity extends AppCompatActivity {

    ActivityPlaceDetailsBinding mBinding;
    Context mContext;
    RestaurantDetails mRestaurant;
    String placeId;
    List<String> favoriteList = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    FirebaseUser mCurrentUser;
    FirebaseFirestore mDatabase;

    WorkmatesChoiceAdapter mWorkmatesChoiceAdapter;
    RecyclerView mWorkmatesChoiceRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPlaceDetailsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //Get bundle's information
        Bundle restaurantInformation = getIntent().getExtras();
        placeId = restaurantInformation.getString("place_id");

        getRestaurantDetails(placeId);

        //Get the last account connected to app
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        //Get database instance
        mDatabase = FirebaseFirestore.getInstance();
        //Get favoriteList of user
        mDatabase.collection("user").whereEqualTo("id", mCurrentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0); //get(0) cause we search only one user
                    User user = documentSnapshot.toObject(User.class);
                    favoriteList.clear(); //clear in case
                    assert user != null;
                    if(user.getFavoriteList() != null){
                        favoriteList.addAll(user.getFavoriteList()); //add the list we get from database
                    }
                }
            }
        });

        //Button Call
        mBinding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + mRestaurant.getFormattedPhoneNumber()));
                startActivity(call);
            }
        });

        //Button Website
        mBinding.btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent website = new Intent(Intent.ACTION_VIEW);
                website.setData(Uri.parse(mRestaurant.getWebsite()));
                startActivity(website);
            }
        });

        //Button Like
        mBinding.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFavorite();
            }
        });

        //Fab Place choice
        mBinding.fabPlaceChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoice();
            }
        });

        //RecyclerView for workmates choice
        //Initialize RV
        mWorkmatesChoiceRecycler = findViewById(R.id.rv_workmates_choice);
        mWorkmatesChoiceRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mWorkmatesChoiceRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mWorkmatesChoiceAdapter = new WorkmatesChoiceAdapter();
        mWorkmatesChoiceRecycler.setAdapter(mWorkmatesChoiceAdapter);
        //Get data
        mDatabase.collection("restaurant_choice").whereEqualTo("placeId", placeId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (!task.getResult().getDocuments().isEmpty()){
                        List<DocumentSnapshot> documentSnapshotList = task.getResult().getDocuments();
                        for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                            RestaurantChoice restaurantChoice = documentSnapshot.toObject(RestaurantChoice.class);
                            //Get users witch choose this place
                            assert restaurantChoice != null;
                            mDatabase.collection("user").whereEqualTo("id", restaurantChoice.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                            User user = documentSnapshot.toObject(User.class);
                                            //Add all users but not your profile
                                            assert user != null;
                                            if (!Objects.equals(user.getId(), mCurrentUser.getUid())){
                                                userList.add(user);
                                            }
                                            //Set data
                                            mWorkmatesChoiceAdapter.setData(userList);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void getRestaurantDetails(String placeId){
        String key = getString(R.string.google_place_key);
        //Language for call
        String language = Locale.getDefault().getLanguage();
        if(!language.equals("fr")){
            //If language is not fr then language is "en" by default
            language = "en";
        }
        //Call for restaurant information
        Call<ResultDetails> call = Go4Lunch.createRetrofitClient().getPlaceInformation(placeId, key, language);
        call.enqueue(new Callback<ResultDetails>() {
            @Override
            public void onResponse(Call<ResultDetails> call, Response<ResultDetails> response) {
                if (response.isSuccessful()){
                    ResultDetails result = response.body();
                    if (result != null){
                        mRestaurant = result.getRestaurantDetails();
                        populateLayout();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResultDetails> call, Throwable t) {
                Log.d("Null", t.getMessage());
            }
        });
    }

    private void populateLayout(){
        if (mRestaurant != null){
            //Set restaurant info
            mBinding.tvName.setText(mRestaurant.getName());
            mBinding.tvAddress.setText(mRestaurant.getVicinity());
            //Opening hours
            if (mRestaurant.getOpeningHour() != null){
                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK); //0 -> Sunday to 6 -> Saturday
                String weekdayText;
                if (day == 0){
                    weekdayText = mRestaurant.getOpeningHour().getWeekdayText().get(6); //Because sunday is 0 in the Calendar class & 6 in the serialized obj
                }else {
                    weekdayText = mRestaurant.getOpeningHour().getWeekdayText().get(day - 1); //For other days, we have just to get the -1 index
                }
                mBinding.tvOpeningHours.setText(weekdayText.substring(weekdayText.indexOf(":")).substring(2));
            }else {
                mBinding.tvOpeningHours.setText(getText(R.string.no_opening_hours));
            }

            //Set image
            if (mRestaurant.getPhotos() != null){
                //Get image from Place
                String url = "https://maps.googleapis.com/maps/api/place/photo" //Base url
                        + "?maxwidth=200"
                        + "&photo_reference=" + mRestaurant.getPhotos().get(0).getPhotoReference()  //Photo reference
                        + "&key=" + this.getString(R.string.google_place_key);//API Key;
                Glide.with(this).load(url).into(mBinding.ivRestaurantPicture);
            }else {
                //Set no image icon
                Glide.with(this).load(R.drawable.ic_image_not_supported).into(mBinding.ivRestaurantPicture);
            }
            //Enable buttons
            //Call button
            if (mRestaurant.getFormattedPhoneNumber() == null){
                mBinding.btnCall.setEnabled(false);
            }
            //Website Button
            if (mRestaurant.getWebsite() == null){
                mBinding.btnWebsite.setEnabled(false);
            }
            //Like button
            if (favoriteList.contains(placeId)){
                for (Drawable drawable: mBinding.btnLike.getCompoundDrawables()){
                    if (drawable != null){
                        drawable.setTint(getResources().getColor(R.color.orange_toolbar));
                    }
                }
            }
            //Choice FAB
            mDatabase.collection("user_choice").whereEqualTo("userId", mCurrentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        if (task.getResult().getDocuments().isEmpty()){
                            mBinding.fabPlaceChoice.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.darkslategray)));
                        }else {
                            //Verify if is the place chosen by user
                            UserChoice userChoice = task.getResult().getDocuments().get(0).toObject(UserChoice.class);
                            assert userChoice != null;
                            if (Objects.equals(placeId, userChoice.getPlaceId())){
                                mBinding.fabPlaceChoice.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange_toolbar)));
                            }
                        }
                    }
                }
            });
        }
    }

    private void setFavorite(){
        //If place is in favList
        if (favoriteList.contains(placeId)){
            //Delete from list
            favoriteList.remove(placeId);
            mBinding.btnLike.setTextColor(getResources().getColor(R.color.darkslategray));
            for (Drawable drawable: mBinding.btnLike.getCompoundDrawables()){
                if (drawable != null){
                    drawable.setTint(getResources().getColor(R.color.darkslategray));
                }
            }
        }else{
            //Add in list
            favoriteList.add(placeId);
            mBinding.btnLike.setTextColor(getResources().getColor(R.color.orange_toolbar));
            for (Drawable drawable: mBinding.btnLike.getCompoundDrawables()){
                if (drawable != null){
                    drawable.setTint(getResources().getColor(R.color.orange_toolbar));
                }
            }
        }
        //Update database
        User user = new User(mCurrentUser.getUid(), mCurrentUser.getDisplayName(), Objects.requireNonNull(mCurrentUser.getPhotoUrl()).toString(), favoriteList);
        mDatabase.collection("user").document(user.getId()).set(user);
    }


    private void setChoice(){
        RestaurantChoice restaurantChoice = new RestaurantChoice(placeId, mCurrentUser.getUid());
        UserChoice userChoice = new UserChoice(mCurrentUser.getUid(), placeId, mRestaurant.getName());
        mDatabase.collection("user_choice").whereEqualTo("userId", mCurrentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().getDocuments().isEmpty()){
                        //Add
                        mDatabase.collection("restaurant_choice").document(restaurantChoice.getPlaceId()).set(restaurantChoice);
                        mDatabase.collection("user_choice").document(userChoice.getUserId()).set(userChoice);
                        mBinding.fabPlaceChoice.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange_toolbar)));
                    }else{
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        UserChoice choice = documentSnapshot.toObject(UserChoice.class);
                        assert choice != null;
                        if (!Objects.equals(userChoice.getPlaceId(), choice.getPlaceId())){
                            //Update
                            //Delete the last, can't update directly cause of different placeId
                            mDatabase.collection("restaurant_choice").document(choice.getPlaceId()).delete();
                            mDatabase.collection("restaurant_choice").document(restaurantChoice.getPlaceId()).set(restaurantChoice);
                            mDatabase.collection("user_choice").document(userChoice.getUserId()).set(userChoice);
                            mBinding.fabPlaceChoice.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange_toolbar)));
                        }else {
                            //Delete
                            mDatabase.collection("restaurant_choice").document(restaurantChoice.getPlaceId()).delete();
                            mDatabase.collection("user_choice").document(userChoice.getUserId()).delete();
                            mBinding.fabPlaceChoice.setSupportImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.darkslategray)));
                        }
                    }
                }
            }
        });
    }
}