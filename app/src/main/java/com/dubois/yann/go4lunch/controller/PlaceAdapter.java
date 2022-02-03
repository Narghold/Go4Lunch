package com.dubois.yann.go4lunch.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dubois.yann.go4lunch.R;
import com.google.android.libraries.places.api.model.Place;

import java.util.ArrayList;
import java.util.List;

class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    Context mContext;

    //List of places
    private final List<Place> mPlaceList = new ArrayList<>();

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mView = mInflater.inflate(R.layout.rv_place_item, parent, false);
        return new PlaceViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
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
