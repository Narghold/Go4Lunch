package com.dubois.yann.go4lunch.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dubois.yann.go4lunch.Go4Lunch;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.model.list.Restaurant;
import com.dubois.yann.go4lunch.model.list.ResultList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesFragment extends Fragment implements LocationListener{

    private PlaceAdapter mPlaceAdapter;
    private RecyclerView mPlaceRecycler;

    private LocationManager mLocationManager;
    private Location mLocation;
    private static final int RC_LOCATION_PERM = 122;

    private List<Restaurant> mRestaurantList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        askLocationPermission();
        mPlaceAdapter.setData(mRestaurantList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_places, container, false);
        Context mContext = mView.getContext();

        //Get location
        mLocationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        //Initialize RecyclerView
        mPlaceRecycler = mView.findViewById(R.id.rv_places);
        mPlaceRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mPlaceRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mPlaceAdapter = new PlaceAdapter();
        mPlaceRecycler.setAdapter(mPlaceAdapter);
        mPlaceAdapter.setData(mRestaurantList);

        // Inflate the layout for this fragment
        return mView;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLocation = location;
        if (mLocation != null){
            mLocationManager.removeUpdates(this);
            //Get places
            getNearbyPlaces(mLocation);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void askLocationPermission() {
        if (EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) || EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)){
            getLocation();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_rq_location), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        if (EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) || EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    private void getNearbyPlaces(Location location){
        //Build parameters
        String locationString = location.getLatitude() + "," + location.getLongitude();
        String key = getString(R.string.google_place_key);

        //Call Interface
        Call<ResultList> call = Go4Lunch.createRetrofitClient().getRestaurant(locationString, key);

        //Execute call
        call.enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(@NotNull Call<ResultList> call, @NotNull Response<ResultList> response) {
                if(response.isSuccessful()){
                    ResultList result = response.body();
                    if (result != null){
                        mRestaurantList = result.getRestaurantList();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                Log.d("Null", t.getMessage());
            }
        });
        mPlaceAdapter.setData(mRestaurantList);
    }

    //TODO:: private int calculateDistanceFromCurrentLocation(){}
}