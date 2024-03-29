package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.serviceapp.activities.user_panel.TaskDetailsActivity;
import com.myapp.serviceapp.databinding.ItemTaskBinding;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.model.TaskModel;

import java.util.ArrayList;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder> {

    private ArrayList<TaskModel> taskList;
    private Context context;

    private OnItemButtonClickListener listener;
    private String tag;
    SharedPrefsManager sharedPrefsManager;

    public MyTaskAdapter(ArrayList<TaskModel> taskList, Context context, MyTaskAdapter.OnItemButtonClickListener listener) {
        this.taskList = taskList;
        this.context = context;
        this.listener=listener;
        this.tag="client";
        sharedPrefsManager=new SharedPrefsManager(context);
    }

    public MyTaskAdapter(ArrayList<TaskModel> taskList, Context context,String tag){
        this.taskList = taskList;
        this.context = context;
        this.tag=tag;
        sharedPrefsManager=new SharedPrefsManager(context);


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

            if (tag.equals("freelance")){
                binding.btnDel.setVisibility(View.GONE);
                binding.btnEdit.setVisibility(View.GONE);
                if (taskModel.getAssignUser().equals(sharedPrefsManager.getUser().getUserId())){
                    binding.txtStatus.setVisibility(View.VISIBLE);
                }else{
                    binding.txtStatus.setVisibility(View.GONE);
                }
            }

            binding.taskTitle.setText(taskModel.getTaskTitle());
            binding.price.setText(taskModel.getBudget());
            binding.location.setText(taskModel.getLocation());
            binding.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteItem(getAdapterPosition());
                }
            });
            binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context, TaskDetailsActivity.class);
                            intent.putExtra("task",taskModel);
                            context.startActivity(intent);
                        }
                    });
                }
            });

            binding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onEditItem(getAdapterPosition());
                }
            });
        }
    }


    public interface OnItemButtonClickListener{
        void onDeleteItem(int position);
        void onEditItem(int position);
    }
}
