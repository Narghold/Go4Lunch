package com.dubois.yann.go4lunch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkmateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkmateFragment extends Fragment {

    public WorkmateFragment() {
        // Required empty public constructor
    }

    public static WorkmateFragment newInstance() {
        return new WorkmateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workmate, container, false);
    }
}