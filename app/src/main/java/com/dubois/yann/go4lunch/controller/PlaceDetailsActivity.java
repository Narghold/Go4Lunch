package com.dubois.yann.go4lunch.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.databinding.ActivityPlaceDetailsBinding;
import com.dubois.yann.go4lunch.model.Restaurant;

public class PlaceDetailsActivity extends AppCompatActivity {

    ActivityPlaceDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPlaceDetailsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //Get bundle's information
        Bundle restaurantInformation = getIntent().getExtras();
        Restaurant restaurant = restaurantInformation.getParcelable("restaurant");

        //Set place_id into name TV
        mBinding.tvName.setText(restaurant.getName());
        mBinding.tvAddress.setText(restaurant.getAddress());
        //Opening hours
        if (restaurant.getOpeningHour() != null){
            if (restaurant.getOpeningHour().getOpenNow()){
                mBinding.tvOpeningHours.setText(getText(R.string.open_now));
            }else{
                mBinding.tvOpeningHours.setText(getText(R.string.close_now));
            }
        }else {
            mBinding.tvOpeningHours.setText(getText(R.string.no_opening_hours));
        }
    }
}