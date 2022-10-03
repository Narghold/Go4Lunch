package com.dubois.yann.go4lunch.util;

import com.dubois.yann.go4lunch.model.details.ResultDetails;
import com.dubois.yann.go4lunch.model.list.ResultList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceRepository {

    //Sensor & Type & Radius(in meters)
    @GET("nearbysearch/json?sensor=true&type=restaurant&radius=2000")
    Call<ResultList> getRestaurant(@Query("location") String location, @Query("key") String key);

    //Place Details
    @GET("details/json?fields=name,vicinity,rating,website,formatted_phone_number,photos,opening_hours")
    Call<ResultDetails> getPlaceInformation(@Query("place_id") String place_id, @Query("key") String key, @Query("language") String language);
}
