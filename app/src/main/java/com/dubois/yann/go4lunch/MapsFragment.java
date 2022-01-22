package com.dubois.yann.go4lunch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pub.devrel.easypermissions.EasyPermissions;

public class MapsFragment extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    private LocationManager mLocationManager;
    private Location mLocation;

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLocationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) || EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

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
            LatLng mLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 13));
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLocation = location;
        if (mLocation != null){
            mLocationManager.removeUpdates(this);
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
}