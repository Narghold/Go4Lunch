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
import com.dubois.yann.go4lunch.model.UserChoice;

import java.util.List;
import java.util.Objects;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {

    Context mContext;

    //List of users
    private final List<User> mUserList;
    private final List<UserChoice> mUserChoiceList;

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {

        ImageView mItemProfilePicture;
        TextView mItemRestaurantChoice;

        public WorkmatesViewHolder(View view) {
            super(view);
            mItemProfilePicture = view.findViewById(R.id.item_profile_picture);
            mItemRestaurantChoice = view.findViewById(R.id.item_restaurant_choice);
        }
    }

    public WorkmatesAdapter(List<User> userList, List<UserChoice> userChoiceList) {
        mUserList = userList;
        mUserChoiceList = userChoiceList;
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mView = mInflater.inflate(R.layout.rv_workmate_item, parent, false);
        return new WorkmatesViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesViewHolder holder, int position) {
        User itemUser = mUserList.get(position);
        Glide.with(mContext).load(itemUser.getPhotoURL()).circleCrop().into(holder.mItemProfilePicture);
        holder.mItemRestaurantChoice.setText(setItemText(itemUser));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    private String setItemText(User itemUser){
        String text = itemUser.getUsername() + " " + mContext.getString(R.string.not_chosen);
        for (UserChoice userChoice : mUserChoiceList) {
            if (Objects.equals(userChoice.getUserId(), itemUser.getId())){
                text = itemUser.getUsername() + " " + mContext.getString(R.string.eat_at) + " " + userChoice.getPlaceName();
            }
        }
        return text;
    }
}
