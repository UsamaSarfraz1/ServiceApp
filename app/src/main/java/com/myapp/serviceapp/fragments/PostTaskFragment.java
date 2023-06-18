package com.myapp.serviceapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.adapter.CategoryAdapter;
import com.myapp.serviceapp.databinding.FragmentPostTaskBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.ArrayList;

public class PostTaskFragment extends Fragment {

    private FragmentPostTaskBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPostTaskBinding.inflate(inflater, container, false);
        mDatabase= FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child(Constants.CATEGORIES);
        getDate();
        return binding.getRoot();
    }
    private void getDate() {
        ArrayList<ParentCategory> list = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(list, requireActivity());
        binding.rvCategory.setAdapter(categoryAdapter);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapchat : snapshot.getChildren()) {
                    ParentCategory parentCategory = dataSnapchat.getValue(ParentCategory.class);
                    if (!parentCategory.getCatParentId().equals("")) {
                        list.add(parentCategory);
                    }
                }
                categoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
