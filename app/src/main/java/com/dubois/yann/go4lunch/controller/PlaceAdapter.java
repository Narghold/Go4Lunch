package com.dubois.yann.go4lunch.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.model.Restaurant;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    Context mContext;



    //List of places
    private List<Restaurant> mPlaceList;

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mView = mInflater.inflate(R.layout.rv_place_item, parent, false);
        return new PlaceViewHolder(mView);
    }

    public PlaceAdapter(){
        mPlaceList = new  ArrayList<>();
    }

    public void setData(List<Restaurant> dataList){
        mPlaceList.clear();
        mPlaceList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {

        //Initialize Place SDK
        Places.initialize(mContext, mContext.getString(R.string.google_api_key));
        PlacesClient mPlacesClient = Places.createClient(mContext);

        Restaurant itemRestaurant = mPlaceList.get(position);

        //Get place photo from photoMetadata field
        /*if(itemRestaurant.getPhoto() != null){
            final FetchPhotoRequest mPhotoRequest = FetchPhotoRequest.builder(itemRestaurant.getPhoto())
                    .setMaxHeight(200).setMaxWidth(200).build();
            mPlacesClient.fetchPhoto(mPhotoRequest).addOnSuccessListener(fetchPhotoResponse -> {
                Bitmap mBitmap = fetchPhotoResponse.getBitmap();
                holder.mItemPhoto.setImageBitmap(mBitmap);
            }).addOnFailureListener(exception -> {
                if (exception instanceof ApiException) {
                    final ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                }
            });
        }
        holder.mItemName.setText(itemRestaurant.getName());
        holder.mItemDetail.setText(String.format("%s -- %s", itemRestaurant.getNationality(), itemRestaurant.getAddress()));
        holder.mItemDistance.setText(String.valueOf(itemRestaurant.getDistance()));
        holder.mItemRating.setText(String.valueOf(itemRestaurant.getRating()));*/
    }

    @Override
    public int getItemCount(){
        return 0;
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder{

        TextView mItemName;
        TextView mItemDetail;
        TextView mItemDistance;
        TextView mItemRating;
        ImageView mItemPhoto;

        public PlaceViewHolder(@NonNull View view) {
            super(view);
            mItemName = view.findViewById(R.id.item_place_name);
            mItemDetail = view.findViewById(R.id.item_place_detail);
            mItemDistance = view.findViewById(R.id.item_place_distance);
            mItemRating = view.findViewById(R.id.item_place_rating);
            mItemPhoto = view.findViewById(R.id.item_place_img);
        }
    }
}
