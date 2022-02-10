package com.dubois.yann.go4lunch.controller;

import android.content.Context;
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
import com.dubois.yann.go4lunch.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkmateFragment extends Fragment {

    private WorkmatesAdapter mWorkmatesAdapter;
    private RecyclerView mWorkmatesRecycler;

    private final String USER_KEY = "user";
    List<User> mUserList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_workmate, container, false);
        Context mContext = mView.getContext();

        //Get userList from database
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        mDatabase.collection(USER_KEY).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        User user = document.toObject(User.class);
                        mUserList.add(user);
                        Log.d("User", user.toString());
                    }
                    //Initialise recycler view
                    mWorkmatesRecycler = mView.findViewById(R.id.rv_workmates);
                    mWorkmatesRecycler.setLayoutManager(new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL, false));
                    mWorkmatesRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
                    mWorkmatesAdapter  = new WorkmatesAdapter(mUserList);
                    mWorkmatesRecycler.setAdapter(mWorkmatesAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("User", "Can't get user");
            }
        });
        return mView;
    }
}