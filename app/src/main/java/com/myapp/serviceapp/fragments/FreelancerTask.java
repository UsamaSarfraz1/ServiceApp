package com.myapp.serviceapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.adapter.MyTaskAdapter;
import com.myapp.serviceapp.databinding.FragmentFreelancerTaskBinding;
import com.myapp.serviceapp.databinding.FragmentHomeBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.model.Offers;
import com.myapp.serviceapp.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class FreelancerTask extends Fragment {
    FragmentFreelancerTaskBinding binding;
    private SharedPrefsManager sharedPrefsManager;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private String userId;
    private ArrayList<TaskModel> tasklist = new ArrayList<>();
    private MyTaskAdapter myTaskAdapter;
    private Boolean isMyTask=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFreelancerTaskBinding.inflate(inflater, container, false);
        sharedPrefsManager=new SharedPrefsManager(requireContext());
        mRef=FirebaseDatabase.getInstance().getReference().child(Constants.TASK);
        setUpRecyclerView();
        return binding.getRoot();
    }
    private void setUpRecyclerView() {
        myTaskAdapter=new MyTaskAdapter(tasklist,requireContext(),"freelance");
        binding.rvTask.setAdapter(myTaskAdapter);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasklist.clear();
                for (DataSnapshot taskSnap:snapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : taskSnap.getChildren()) {
                        String taskId = String.valueOf(snapshot1.child("taskId").getValue());
                        String taskTitle = String.valueOf(snapshot1.child("taskTitle").getValue());
                        String taskDetails = String.valueOf(snapshot1.child("taskDetails").getValue());
                        String catId = String.valueOf(snapshot1.child("catId").getValue());
                        String catName = String.valueOf(snapshot1.child("catName").getValue());
                        String location = String.valueOf(snapshot1.child("location").getValue());
                        String budget = String.valueOf(snapshot1.child("budget").getValue());
                        String date = String.valueOf(snapshot1.child("date").getValue());
                        String userId = String.valueOf(snapshot1.child("userId").getValue());
                        String status = String.valueOf(snapshot1.child("status").getValue());
                        String assignUser = String.valueOf(snapshot1.child("assignUser").getValue());
                        List<Offers> orderlist = new ArrayList<>();
                        TaskModel taskModel = new TaskModel(taskId, userId, taskTitle, taskDetails, catId, catName, location, budget, date, status, assignUser);
                        for (DataSnapshot listshot : snapshot1.child("orderlist").getChildren()) {
                            Offers offers = listshot.getValue(Offers.class);
                            offers.setOffer_id("dfsf");
                            orderlist.add(offers);
                            if (offers.getFreelancer_id().equals(sharedPrefsManager.getUser().getUserId())){
                                isMyTask=true;
                            }

                        }
                        taskModel.setOrderlist(orderlist);
                        if (isMyTask)
                            tasklist.add(taskModel);
                    }

                }

                myTaskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}