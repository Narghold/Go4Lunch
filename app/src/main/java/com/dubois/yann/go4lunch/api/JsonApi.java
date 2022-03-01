package com.dubois.yann.go4lunch.api;

import androidx.appcompat.app.AppCompatActivity;

import com.dubois.yann.go4lunch.model.Restaurant;
import com.dubois.yann.go4lunch.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonApi {

    //Sensor & Type & Radius(in meters)
    @GET("json?sensor=true&type=restaurant&radius=2000")
    Call<Result> getRestaurant(@Query("location") String location, @Query("key") String key);
}
