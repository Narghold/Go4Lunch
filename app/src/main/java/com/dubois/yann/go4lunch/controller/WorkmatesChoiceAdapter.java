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
import com.dubois.yann.go4lunch.model.User;

import java.util.List;

public class WorkmatesChoiceAdapter extends RecyclerView.Adapter<WorkmatesChoiceAdapter.WorkmatesChoiceViewHolder> {

    Context mContext;
    List<User> mUserList;

    public static class WorkmatesChoiceViewHolder extends RecyclerView.ViewHolder{

        ImageView mItemProfilePicture;
        TextView mItemRestaurantChoice;

        public WorkmatesChoiceViewHolder(@NonNull View view) {
            super(view);
            mItemProfilePicture = view.findViewById(R.id.item_profile_picture);
            mItemRestaurantChoice = view.findViewById(R.id.item_restaurant_choice);
        }
    }


    @NonNull
    @Override
    public WorkmatesChoiceAdapter.WorkmatesChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mView = mInflater.inflate(R.layout.rv_workmate_item, parent, false);
        return new WorkmatesChoiceViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesChoiceAdapter.WorkmatesChoiceViewHolder holder, int position) {
        User itemUser = mUserList.get(position);
        Glide.with(mContext).load(itemUser.getPhotoURL()).circleCrop().into(holder.mItemProfilePicture);
        holder.mItemRestaurantChoice.setText(itemUser.getUsername());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
