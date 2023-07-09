package com.myapp.serviceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.activities.user_panel.TaskDetailsActivity;
import com.myapp.serviceapp.adapter.AllTask;
import com.myapp.serviceapp.databinding.FragmentAllTaskBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.Offers;
import com.myapp.serviceapp.model.TaskModel;
import com.myapp.serviceapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllTaskFragment extends Fragment implements AllTask.ClickCallBack {
    private FragmentAllTaskBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private ProgressDialog progressDialog;
    private SharedPrefsManager sharedPrefsManager;
    private final List<TaskModel> taskModelList = new ArrayList<>();
    AllTask allTaskAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAllTaskBinding.inflate(inflater,container,false);
        sharedPrefsManager=new SharedPrefsManager(requireContext());
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child(Constants.TASK);
        progressDialog=new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        getData();
        return binding.getRoot();
    }

    private void getData() {
        allTaskAdapter=new AllTask(taskModelList,requireContext(),this);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskModelList.clear();
                for (DataSnapshot dataSnapshot:
                     snapshot.getChildren()) {
                    for (DataSnapshot snapshot1:
                         dataSnapshot.getChildren()) {
                            if (!snapshot1.child("userId").equals(sharedPrefsManager.getUser().getUserId())) {
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
                                String taskUser=String.valueOf(snapshot1.child("taskUser").getValue());
                                List<Offers> orderlist=new ArrayList<>();
                                TaskModel taskModel=new TaskModel(taskId,userId,taskTitle,taskDetails,catId,catName,location,budget,date,status,taskUser);
                                for (DataSnapshot listshot : snapshot1.child("orderlist").getChildren()) {
                                    Offers offers=listshot.getValue(Offers.class);
                                    orderlist.add(offers);
                                }
                                taskModel.setOrderlist(orderlist);
                                    taskModelList.add(taskModel);
                            }
                    }
                }
                binding.rvAllTask.setAdapter(allTaskAdapter);

                allTaskAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.show(requireContext(),error.getMessage());
            }
        });
    }

    @Override
    public void onClickStatus(int position) {
        Intent intent=new Intent(requireContext(), TaskDetailsActivity.class);
        intent.putExtra("task",taskModelList.get(position));
        startActivity(intent);
    }
}