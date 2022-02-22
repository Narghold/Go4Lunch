package com.dubois.yann.go4lunch.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.model.Restaurant;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PlacesFragment extends Fragment {

    private PlaceAdapter mPlaceAdapter;
    private RecyclerView mPlaceRecycler;


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_places, container, false);
        Context mContext = mView.getContext();

        //Initialize RecyclerView
        mPlaceRecycler = mView.findViewById(R.id.rv_places);
        mPlaceRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mPlaceRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mPlaceAdapter = new PlaceAdapter();
        mPlaceRecycler.setAdapter(mPlaceAdapter);

        //Initialize Place SDK
        Places.initialize(mContext, getString(R.string.google_api_key));
        PlacesClient mPlacesClient = Places.createClient(mContext);

        //Get Places List
        List<Place.Field> mPlaceFields = Arrays.asList(Place.Field.NAME, Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS, Place.Field.RATING, Place.Field.TYPES);
        FindCurrentPlaceRequest mRequest = FindCurrentPlaceRequest.newInstance(mPlaceFields);

        if (EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) || EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            @SuppressLint("MissingPermission") Task<FindCurrentPlaceResponse> mPlaceResponse = mPlacesClient.findCurrentPlace(mRequest);
            mPlaceResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    List<Restaurant> mPlaceList = new ArrayList<>();
                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Place place = placeLikelihood.getPlace();

                        //Type verification to get just restaurants
                        if (place.getTypes().contains(Place.Type.RESTAURANT)){
                            //Photo Metadata
                            List<PhotoMetadata> metadata = place.getPhotoMetadatas();
                            if (metadata != null){
                                final PhotoMetadata photoMetadata = metadata.get(0);

                                //Add the restaurant to the list
                                Restaurant restaurant = new Restaurant(place.getId(), place.getName(), place.getAddress(), 100, "French", photoMetadata, place.getRating());
                                mPlaceList.add(restaurant);
                                Log.d("Place", restaurant.toString());
                            }else {
                                Restaurant restaurant = new Restaurant(place.getId(), place.getName(), place.getAddress(), 100, "French", null, place.getRating());
                                mPlaceList.add(restaurant);
                                Log.d("Place", restaurant.toString());
                            }
                        }

                    }
                    mPlaceAdapter.setData(mPlaceList);
                }
            }).addOnFailureListener(e -> Log.d("Restaurant", "Can't get place"));
        }

        // Inflate the layout for this fragment
        return mView;
    }

    //TODO:: private int calculateDistanceFromCurrentLocation(){}
}