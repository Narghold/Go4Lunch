package com.dubois.yann.go4lunch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.FirebaseApp;

public class WorkmateFragment extends Fragment {

    private WorkmatesAdapter mWorkmatesAdapter;
    private RecyclerView mWorkmatesRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_workmate, container, false);
        Context mContext = mView.getContext();
        mWorkmatesRecycler = mView.findViewById(R.id.rv_workmates);
        mWorkmatesRecycler.setLayoutManager(new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL, false));
        mWorkmatesRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mWorkmatesAdapter  = new WorkmatesAdapter();
        mWorkmatesRecycler.setAdapter(mWorkmatesAdapter);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}