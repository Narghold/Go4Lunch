package com.dubois.yann.go4lunch;

import android.app.Application;

import com.dubois.yann.go4lunch.util.PlaceRepository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Go4Lunch extends Application {

    private static Retrofit retrofit;

    public static PlaceRepository createRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/place/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(PlaceRepository.class);
    }
}
