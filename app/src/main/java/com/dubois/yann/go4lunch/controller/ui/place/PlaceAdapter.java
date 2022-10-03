package com.dubois.yann.go4lunch.controller.ui.place;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dubois.yann.go4lunch.R;
import com.dubois.yann.go4lunch.controller.ui.place_details.PlaceDetailsActivity;
import com.dubois.yann.go4lunch.model.list.Restaurant;

import java.util.List;

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

    public PlaceAdapter(List<Restaurant> placeList){
        mPlaceList = placeList;
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
        holder.mItemDistance.setText(String.format("%d m", itemRestaurant.getDistance()));
        holder.mItemDetail.setText(itemRestaurant.getAddress());

        //Rating
        if (itemRestaurant.getRating() >= 1){
            holder.mItemRating.setRating(itemRestaurant.getRating());
            holder.mItemNoRating.setVisibility(View.GONE);
            holder.mItemRating.setVisibility(View.VISIBLE);
        }else {
            holder.mItemRating.setVisibility(View.GONE);
            holder.mItemNoRating.setVisibility(View.VISIBLE);
        }

        //Opening hours
        if (itemRestaurant.getOpeningHour() != null){
            if (itemRestaurant.getOpeningHour().getOpenNow()){
                holder.mItemHour.setText(mContext.getText(R.string.open_now));
            }else{
                holder.mItemHour.setText(mContext.getText(R.string.close_now));
            }
        }else {
            holder.mItemHour.setText(mContext.getText(R.string.no_opening_hours));
        }


        //Set image
        if (itemRestaurant.getPhotos() != null){
            //Get image from Place
            String url = "https://maps.googleapis.com/maps/api/place/photo" //Base url
                    + "?maxwidth=200"
                    + "&photo_reference=" + itemRestaurant.getPhotos().get(0).getPhotoReference()  //Photo reference
                    + "&key=" + mContext.getString(R.string.google_place_key);//API Key;
            Glide.with(mContext).load(url).into(holder.mItemPhoto);
        }else {
            //Set no image icon
            Glide.with(mContext).load(R.drawable.ic_image_not_supported).into(holder.mItemPhoto);
        }

        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailActivity = new Intent(view.getContext(), PlaceDetailsActivity.class);
                Bundle restaurantInformation = new Bundle();
                restaurantInformation.putString("place_id", itemRestaurant.getPlace_id());
                detailActivity.putExtras(restaurantInformation);

                view.getContext().startActivity(detailActivity);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mPlaceList.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder{

        TextView mItemName;
        TextView mItemDetail;
        TextView mItemDistance;
        RatingBar mItemRating;
        TextView mItemNoRating;
        ImageView mItemPhoto;
        TextView mItemHour;
        ConstraintLayout mItem;

        public PlaceViewHolder(@NonNull View view) {
            super(view);
            mItemName = view.findViewById(R.id.item_place_name);
            mItemDetail = view.findViewById(R.id.item_place_detail);
            mItemDistance = view.findViewById(R.id.item_place_distance);
            mItemRating = view.findViewById(R.id.item_place_rating);
            mItemNoRating = view.findViewById(R.id.item_no_rating);
            mItemPhoto = view.findViewById(R.id.item_place_img);
            mItemHour = view.findViewById(R.id.item_place_hour);
            mItem = view.findViewById(R.id.item_list);
        }
    }

}
