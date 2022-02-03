package com.dubois.yann.go4lunch.controller;

import android.content.Context;
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
import com.dubois.yann.go4lunch.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {

    Context mContext;

    //List of users
    private final List<User> mUserList;

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {

        ImageView mItemProfilePicture;
        TextView mItemRestaurantChoice;

        public WorkmatesViewHolder(View view) {
            super(view);
            mItemProfilePicture = view.findViewById(R.id.item_profile_picture);
            mItemRestaurantChoice = view.findViewById(R.id.item_restaurant_choice);
        }
    }

    public WorkmatesAdapter(List<User> userList) {
        mUserList = userList;
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
        holder.mItemRestaurantChoice.setText(itemUser.getUsername());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
