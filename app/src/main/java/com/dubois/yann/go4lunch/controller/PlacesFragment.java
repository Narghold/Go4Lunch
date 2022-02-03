package com.dubois.yann.go4lunch.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dubois.yann.go4lunch.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacesFragment extends Fragment {

    private PlaceAdapter mPlaceAdapter;
    private RecyclerView mPlaceRecycler;

    public static PlacesFragment newInstance() {
        return new PlacesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_places, container, false);
        Context mContext = mView.getContext();

        mPlaceRecycler = mView.findViewById(R.id.rv_places);
        mPlaceRecycler.setLayoutManager(new LinearLayoutManager(mContext , LinearLayoutManager.VERTICAL, false));
        mPlaceRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mPlaceAdapter = new PlaceAdapter();
        mPlaceRecycler.setAdapter(mPlaceAdapter);

        // Inflate the layout for this fragment
        return mView;
    }
}