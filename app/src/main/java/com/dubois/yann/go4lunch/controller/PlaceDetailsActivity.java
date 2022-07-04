package com.dubois.yann.go4lunch.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.dubois.yann.go4lunch.Go4Lunch;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.databinding.ActivityPlaceDetailsBinding;
import com.dubois.yann.go4lunch.model.details.RestaurantDetails;
import com.dubois.yann.go4lunch.model.details.ResultDetails;
import com.dubois.yann.go4lunch.model.list.ResultList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailsActivity extends AppCompatActivity {

    ActivityPlaceDetailsBinding mBinding;
    RestaurantDetails mRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPlaceDetailsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //Get bundle's information
        Bundle restaurantInformation = getIntent().getExtras();
        String placeId = restaurantInformation.getString("place_id");

        //Call for restaurant information
        Call<ResultDetails> call = Go4Lunch.createRetrofitClient().getPlaceInformation(placeId, getString(R.string.google_place_key));
        call.enqueue(new Callback<ResultDetails>() {
            @Override
            public void onResponse(Call<ResultDetails> call, Response<ResultDetails> response) {
                if(response.isSuccessful()){
                    ResultDetails result = response.body();
                    if (result != null){
                        mRestaurant = result.getRestaurantDetails();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDetails> call, Throwable t) {
                Log.d("Null", t.getMessage());
            }
        });

        //Set place_id into name TV
        mBinding.tvName.setText(mRestaurant.getName());
        mBinding.tvAddress.setText(mRestaurant.getVicinity());
        //Opening hours
        if (mRestaurant.getOpeningHour() != null){
            if (mRestaurant.getOpeningHour().getOpenNow()){
                mBinding.tvOpeningHours.setText(getText(R.string.open_now));
            }else{
                mBinding.tvOpeningHours.setText(getText(R.string.close_now));
            }
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

        //Btn call
        mBinding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"));
                startActivity(call);
            }
        });
    }
}