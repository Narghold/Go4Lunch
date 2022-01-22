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

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {


    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {

        public WorkmatesViewHolder(View view) {
            super(view);
            ImageView mMItemProfilePicture = view.findViewById(R.id.item_profile_picture);
            TextView mMItemRestaurantChoice = view.findViewById(R.id.item_restaurant_choice);
        }
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mView = mInflater.inflate(R.layout.rv_workmate_item, parent, false);
        return new WorkmatesViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
