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
import com.myapp.serviceapp.adapter.MyTaskAdapter;
import com.myapp.serviceapp.databinding.FragmentHomeBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.model.TaskModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private SharedPrefsManager sharedPrefsManager;
    private String userId;
    private ArrayList<TaskModel> tasklist = new ArrayList<>();
    private MyTaskAdapter myTaskAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        sharedPrefsManager=new SharedPrefsManager(requireContext());
        userId=sharedPrefsManager.getUser().getUserId();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child(Constants.TASK).child(userId);
        setUpRecyclerView();
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        myTaskAdapter=new MyTaskAdapter(tasklist,requireContext());
        binding.rvTask.setAdapter(myTaskAdapter);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    TaskModel taskModel=snapshot1.getValue(TaskModel.class);
                    tasklist.add(taskModel);
                }

                myTaskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
