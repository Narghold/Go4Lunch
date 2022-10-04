package com.dubois.yann.go4lunch.controller.ui.workmates;

import android.content.Context;
import android.os.Bundle;
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
import com.dubois.yann.go4lunch.model.UserChoice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkmateFragment extends Fragment {

    private WorkmatesAdapter mWorkmatesAdapter;
    private RecyclerView mWorkmatesRecycler;

    FirebaseAuth firebaseAuth;

    List<User> mUserList  = new ArrayList<>();
    List<UserChoice> mUserChoiceList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_workmate, container, false);
        Context mContext = mView.getContext();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //Get userList from database
        FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
        mDatabase.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        User user = document.toObject(User.class);
                        //Add all users but not your profile
                        assert currentUser != null;
                        if (!Objects.equals(user.getId(), currentUser.getUid())){
                            mUserList.add(user);
                        }
                    }
                    //Get userChoiceList from database
                    mDatabase.collection("user_choice").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    UserChoice userChoice = document.toObject(UserChoice.class);
                                    mUserChoiceList.add(userChoice);
                                }
                            }
                            //Initialise recycler view
                            mWorkmatesRecycler = mView.findViewById(R.id.rv_workmates);
                            mWorkmatesRecycler.setLayoutManager(new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL, false));
                            mWorkmatesRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
                            mWorkmatesAdapter  = new WorkmatesAdapter(mUserList, mUserChoiceList);
                            mWorkmatesRecycler.setAdapter(mWorkmatesAdapter);
                        }
                    });

                }
            }
        });

        return mView;
    }
}