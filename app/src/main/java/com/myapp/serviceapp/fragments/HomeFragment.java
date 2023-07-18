package com.myapp.serviceapp.fragments;

import android.content.Intent;
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
import com.myapp.serviceapp.activities.user_panel.UpdatePost;
import com.myapp.serviceapp.adapter.MyTaskAdapter;
import com.myapp.serviceapp.databinding.FragmentHomeBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.model.Offers;
import com.myapp.serviceapp.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements MyTaskAdapter.OnItemButtonClickListener {

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
        myTaskAdapter=new MyTaskAdapter(tasklist,requireContext(),this);
        binding.rvTask.setAdapter(myTaskAdapter);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasklist.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    String taskId= String.valueOf(snapshot1.child("taskId").getValue());
                    String taskTitle=String.valueOf(snapshot1.child("taskTitle").getValue());
                    String taskDetails=String.valueOf(snapshot1.child("taskDetails").getValue());
                    String catId=String.valueOf(snapshot1.child("catId").getValue());
                    String catName=String.valueOf(snapshot1.child("catName").getValue());
                    String location=String.valueOf(snapshot1.child("location").getValue());
                    String budget=String.valueOf(snapshot1.child("budget").getValue());
                    String date=String.valueOf(snapshot1.child("date").getValue());
                    String userId=String.valueOf(snapshot1.child("userId").getValue());
                    String status=String.valueOf(snapshot1.child("status").getValue());
                    String assignUser=String.valueOf(snapshot1.child("assignUser").getValue());
                    List<Offers> orderlist=new ArrayList<>();
                    TaskModel taskModel=new TaskModel(taskId,userId,taskTitle,taskDetails,catId,catName,location,budget,date,status,assignUser);
                    for (DataSnapshot listshot : snapshot1.child("orderlist").getChildren()) {
                        Offers offers=listshot.getValue(Offers.class);
                        offers.setOffer_id("dfsf");
                        orderlist.add(offers);
                    }

                    taskModel.setOrderlist(orderlist);
                    tasklist.add(taskModel);

                }

                myTaskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onDeleteItem(int position) {
        mRef.child(tasklist.get(position).getTaskId()).removeValue();
        myTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditItem(int position) {
        Intent intent = new Intent(requireContext(),UpdatePost.class);
        intent.putExtra("post",tasklist.get(position));
        startActivity(intent);

    }
}
