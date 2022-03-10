package com.dubois.yann.go4lunch.api;

import com.dubois.yann.go4lunch.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceRepository {

    //Sensor & Type & Radius(in meters)
    @GET("json?sensor=true&type=restaurant&radius=2000")
    Call<Result> getRestaurant(@Query("location") String location, @Query("key") String key);

    //Place Details
    @GET("json?fields=name,vicinity,rating,website")
    Call<Result> getPlaceInformation(@Query("place_id") String place_id, @Query("key") String key);
}
