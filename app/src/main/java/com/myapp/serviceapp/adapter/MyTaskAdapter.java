package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.serviceapp.databinding.ItemTaskBinding;
import com.myapp.serviceapp.model.TaskModel;

import java.util.ArrayList;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder> {

    private ArrayList<TaskModel> taskList;
    private Context context;

    public MyTaskAdapter(ArrayList<TaskModel> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyTaskAdapter.MyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding=ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyTaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskAdapter.MyTaskViewHolder holder, int position) {
        holder.bind(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyTaskViewHolder extends RecyclerView.ViewHolder {
        ItemTaskBinding binding;
        public MyTaskViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(TaskModel taskModel){
            binding.taskTitle.setText(taskModel.getTaskTitle());
            binding.price.setText(taskModel.getBudget());
            binding.location.setText(taskModel.getLocation());
        }
    }
}
