package com.dubois.yann.go4lunch.controller;

import android.content.Context;
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

import java.util.List;

class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    Context mContext;

    //List of places
    private final List<Restaurant> mPlaceList;

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mView = mInflater.inflate(R.layout.rv_place_item, parent, false);
        return new PlaceViewHolder(mView);
    }

    public PlaceAdapter(List<Restaurant> placeList) { mPlaceList = placeList;}

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Restaurant itemRestaurant = mPlaceList.get(position);
        holder.mItemPhoto.setImageBitmap(itemRestaurant.getPhoto());
        holder.mItemName.setText(itemRestaurant.getName());
        holder.mItemDetail.setText(String.format("%s -- %s", itemRestaurant.getNationality(), itemRestaurant.getAddress()));
        holder.mItemDistance.setText(String.valueOf(itemRestaurant.getDistance()));
        holder.mItemRating.setText(String.valueOf(itemRestaurant.getRating()));
    }

    @Override
    public int getItemCount() {
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
