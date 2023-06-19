package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.serviceapp.databinding.ItemAllTaskBinding;
import com.myapp.serviceapp.model.TaskModel;

import java.util.List;

public class AllTask extends RecyclerView.Adapter<AllTask.TaskViewHolder> {
    List<TaskModel> taskModelList;
    Context context;
    public AllTask(List<TaskModel> taskModelList, Context context) {
        this.taskModelList = taskModelList;
        this.context=context;
    }

    @NonNull
    @Override
    public AllTask.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAllTaskBinding binding=ItemAllTaskBinding.inflate(LayoutInflater.from(context),parent,false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTask.TaskViewHolder holder, int position) {
        TaskModel taskModel=taskModelList.get(position);
        holder.bind(taskModel);

    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        ItemAllTaskBinding binding;
        public TaskViewHolder(@NonNull ItemAllTaskBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(TaskModel taskModel){
            binding.taskTitle.setText(taskModel.getTaskTitle());
            String price="Rs . "+taskModel.getBudget();
            binding.price.setText(price);
            binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
