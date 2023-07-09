package com.myapp.serviceapp.activities.user_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityUpdatePostBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.ParentCategory;
import com.myapp.serviceapp.model.TaskModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdatePost extends AppCompatActivity {
    private ActivityUpdatePostBinding binding;
    private ParentCategory parentCategory;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private ProgressDialog progressDialog;
    SharedPrefsManager sharedPrefsManager;
    private String userId;
    TaskModel taskModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefsManager=new SharedPrefsManager(this);
        userId=sharedPrefsManager.getUser().getUserId();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference(Constants.TASK).child(userId);
        progressDialog=new ProgressDialog(this);

        taskModel= (TaskModel) getIntent().getExtras().get("post");
        String date = taskModel.getDate();
        String[] dateArray = date.split("-");
        binding.budgetEditText.setText(taskModel.getBudget());
        binding.detailEditText.setText(taskModel.getTaskDetails());
        binding.editTextCurrentDay.setText(dateArray[0]);
        binding.editTextCurrentMonth.setText(dateArray[1]);
        binding.editTextCurrentYear.setText(dateArray[2]);
        binding.locationEditText.setText(taskModel.getLocation());
        binding.titleEditText.setText(taskModel.getTaskTitle());

        binding.btnPost.setOnClickListener(v -> updateTask());

        binding.btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar instance = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdatePost.this, dateSetListener, instance.get(1),instance.get(2),instance.get(5));
                datePickerDialog.show();
            }
        });
    }

    private void updateTask() {
        progressDialog.setMessage("Please Wait...");
        String catName= taskModel.getCatName();
        String catId= taskModel.getCatId();
        String title=binding.titleEditText.getText().toString();
        String detail= binding.detailEditText.getText().toString();
        String location= "Faisalabad";
        String budget = binding.budgetEditText.getText().toString();
        String date = binding.editTextCurrentDay.getText().toString()+"-"+binding.editTextCurrentMonth.getText().toString()+"-"+binding.editTextCurrentYear.getText().toString();
        Map<String, Object> updatedData = new HashMap<>();
        TaskModel task=new TaskModel(taskModel.getTaskId(),userId,title,detail,catId,catName,location,budget,date,taskModel.getStatus(),taskModel.getAssignUser());
        updatedData.put(taskModel.getTaskId(),task);
        if (title.isEmpty()){
            Toasty.show(this,"Please Enter Title");
        }else if (detail.isEmpty()){
            Toasty.show(this,"Please Enter Details");
        }else if (budget.isEmpty()){
            Toasty.show(this,"Please Enter Your Budget");
        }else {

            mRef.updateChildren((updatedData))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toasty.show(UpdatePost.this ,"Updated");
                            }
                        }
                    });
        }
    }
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            binding.editTextCurrentYear.setText(addZero(year));
            binding.editTextCurrentMonth.setText(addZero(monthOfYear + 1));
            binding.editTextCurrentDay.setText(addZero(dayOfMonth));
        }
    };

    private static String addZero(int number) {
        String n;
        if (number < 10) {
            n = "0" + number;
        } else {
            n = Integer.toString(number);
        }
        return n;
    }

}