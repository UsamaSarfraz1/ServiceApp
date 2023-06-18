package com.myapp.serviceapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.FragmentAllTaskBinding;

public class AllTaskFragment extends Fragment {
    private FragmentAllTaskBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAllTaskBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}