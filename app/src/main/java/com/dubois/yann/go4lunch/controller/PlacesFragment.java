package com.dubois.yann.go4lunch.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
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

import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.model.Restaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
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

import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacesFragment extends Fragment {

    private PlaceAdapter mPlaceAdapter;
    private RecyclerView mPlaceRecycler;
    List<Restaurant> mPlaceList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_places, container, false);
        Context mContext = mView.getContext();

        //Initialize Place SDK
        Places.initialize(mContext, getString(R.string.google_api_key));
        PlacesClient mPlacesClient = Places.createClient(mContext);

        //Get Places List
        List<Place.Field> mPlaceFields = Arrays.asList(Place.Field.NAME, Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS, Place.Field.RATING);
        FindCurrentPlaceRequest mRequest = FindCurrentPlaceRequest.newInstance(mPlaceFields);

        if (EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) || EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            @SuppressLint("MissingPermission") Task<FindCurrentPlaceResponse> mPlaceResponse = mPlacesClient.findCurrentPlace(mRequest);
            mPlaceResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Place place = placeLikelihood.getPlace();

                        //Photo Metadata
                        List<PhotoMetadata> metadata = place.getPhotoMetadatas();
                        if (metadata != null){
                            final PhotoMetadata photoMetadata = metadata.get(0);
                            final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata).build();
                            mPlacesClient.fetchPhoto(photoRequest).addOnSuccessListener(fetchPhotoResponse -> {
                                Bitmap mBitmap = fetchPhotoResponse.getBitmap();
                                Restaurant restaurant = new Restaurant(place.getId(), place.getName(), place.getAddress(), 100, "French", mBitmap, place.getRating());
                                mPlaceList.add(restaurant);
                                Log.d("Place", restaurant.toString());
                            });
                        }else {
                            Restaurant restaurant = new Restaurant(place.getId(), place.getName(), place.getAddress(), 100, "French", null, place.getRating());
                            mPlaceList.add(restaurant);
                            Log.d("Place", restaurant.toString());
                        }


                    }
                    //Initialize RecyclerView
                    mPlaceRecycler = mView.findViewById(R.id.rv_places);
                    mPlaceRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                    mPlaceRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
                    mPlaceAdapter = new PlaceAdapter(mPlaceList);
                    mPlaceRecycler.setAdapter(mPlaceAdapter);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Restaurant", "Can't get place");
                }
            });
        }

        // Inflate the layout for this fragment
        return mView;
    }

    //TODO:: private int calculateDistanceFromCurrentLocation(){}
}