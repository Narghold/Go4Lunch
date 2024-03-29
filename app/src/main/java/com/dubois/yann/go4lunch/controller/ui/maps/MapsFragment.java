package com.dubois.yann.go4lunch.controller.ui.maps;

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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.dubois.yann.go4lunch.Go4Lunch;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.model.list.Restaurant;
import com.dubois.yann.go4lunch.model.list.ResultList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    private LocationManager mLocationManager;
    private Location mLocation;
    private GoogleMap mMap;

    private static final int RC_LOCATION_PERM = 122;

    @Override
    public void onResume() {
        super.onResume();
        askLocationPermission();
    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLocationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mMapFragment != null) {
            mMapFragment.getMapAsync(this);
        }
     }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (mLocation != null){
            mMap = googleMap;
            LatLng mLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 14));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLocation = location;
        if (mLocation != null){
            mLocationManager.removeUpdates(this);
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
    public void getLocation(){
        if (EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) || EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
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

    private void getNearbyPlaces(Location location){
        //Build parameters
        String locationString = location.getLatitude() + "," + location.getLongitude();
        String key = getString(R.string.google_place_key);

        //Call API
        Call<ResultList> call = Go4Lunch.createRetrofitClient().getRestaurant(locationString, key);

        //Execute call
        call.enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(@NotNull Call<ResultList> call, @NotNull Response<ResultList> response) {
                if(response.isSuccessful()){
                    ResultList result = response.body();
                    if (result != null){
                        List<Restaurant> restaurantList = result.getRestaurantList();
                        for (Restaurant restaurant : restaurantList){
                            //Get info for marker
                            Double lat = restaurant.getGeometry().getLocation().getLat();
                            Double lng = restaurant.getGeometry().getLocation().getLng();
                            String name = restaurant.getName();
                            //Initialize marker
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(lat, lng));
                            markerOptions.title(name);
                            //Add marker
                            mMap.addMarker(markerOptions);
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<ResultList> call, Throwable t) {
                Log.d("Null", t.getMessage());
            }
        });
    }
}