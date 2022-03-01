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

        Restaurant itemRestaurant = mPlaceList.get(position);
        holder.mItemName.setText(itemRestaurant.getName());
        holder.mItemRating.setText(String.valueOf(itemRestaurant.getRating()));
        holder.mItemDistance.setText("100m");
        holder.mItemDetail.setText(itemRestaurant.getAddress());

        //Get image
        /*String url = "https://maps.googleapis.com/maps/api/place/photo" //Base url
                + "?key=" + getClass().getResource(String.valueOf(R.string.google_place_key)) //API Key
                + "&photo_reference" + itemRestaurant.getPhotos().get(0).getPhotoReference()  //Photo reference
                + "&maxwidth=200";
        Glide.with(mContext).load(url).into(holder.mItemPhoto);*/
    }

    @Override
    public int getItemCount(){
        return mPlaceList.size();
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
