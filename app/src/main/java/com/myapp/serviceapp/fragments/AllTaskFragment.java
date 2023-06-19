package com.myapp.serviceapp.fragments;

import android.os.Bundle;
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
import com.myapp.serviceapp.adapter.AllTask;
import com.myapp.serviceapp.databinding.FragmentAllTaskBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class AllTaskFragment extends Fragment {
    private FragmentAllTaskBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private final List<TaskModel> taskModelList = new ArrayList<>();
    AllTask allTaskAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAllTaskBinding.inflate(inflater,container,false);
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child(Constants.TASK);
        getData();
        return binding.getRoot();
    }

    private void getData() {
        allTaskAdapter=new AllTask(taskModelList,requireContext());

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskModelList.clear();
                for (DataSnapshot dataSnapshot:
                     snapshot.getChildren()) {
                    for (DataSnapshot snapshot1:
                         dataSnapshot.getChildren()) {
                        TaskModel taskModel= snapshot1.getValue(TaskModel.class);
                        taskModelList.add(taskModel);
                    }
                }
                binding.rvAllTask.setAdapter(allTaskAdapter);

                allTaskAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.show(requireContext(),error.getMessage());
            }
        });
    }
}