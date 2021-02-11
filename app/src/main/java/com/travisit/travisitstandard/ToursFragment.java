package com.travisit.travisitstandard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travisit.travisitstandard.databinding.FragmentToursBinding;


public class ToursFragment extends Fragment {
    FragmentToursBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentToursBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}