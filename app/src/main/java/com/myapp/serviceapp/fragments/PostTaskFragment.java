package com.myapp.serviceapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myapp.serviceapp.adapter.CategoryAdapter;
import com.myapp.serviceapp.databinding.FragmentPostTaskBinding;
import com.myapp.serviceapp.databinding.FragmentProfileBinding;
import com.myapp.serviceapp.model.ChildCategory;

import java.util.ArrayList;

public class PostTaskFragment extends Fragment {

    private FragmentPostTaskBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPostTaskBinding.inflate(inflater, container, false);
        setUpRecyclerView();
        return binding.getRoot();

    }

    private void setUpRecyclerView() {
        CategoryAdapter categoryAdapter= new CategoryAdapter(getDate() , requireActivity());
        binding.rvCategory.setAdapter(categoryAdapter);
    }

    private ArrayList<ChildCategory> getDate(){
        ArrayList<ChildCategory> list= new ArrayList<>();
        list.add(new ChildCategory("1","Cleaning","description",""));
        list.add(new ChildCategory("1","Cleaning","description",""));
        list.add(new ChildCategory("1","Cleaning","description",""));
        list.add(new ChildCategory("1","Cleaning","description",""));
        list.add(new ChildCategory("1","Cleaning","description",""));
        list.add(new ChildCategory("1","Cleaning","description",""));
        return list;
    }
}
