package com.myapp.serviceapp.activities.user_panel;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.serviceapp.databinding.ActivityPostBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.ParentCategory;
import com.myapp.serviceapp.model.TaskModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding binding;
    private ParentCategory parentCategory;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private ProgressDialog progressDialog;
    SharedPrefsManager sharedPrefsManager;

    private GregorianCalendar calendar;
    private DatePickerDialog datePickerDialog;

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefsManager=new SharedPrefsManager(this);
        userId=sharedPrefsManager.getUser().getUserId();
        progressDialog=new ProgressDialog(this);
        setUpCurrentDate();


        binding.btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar instance = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostActivity.this, dateSetListener, instance.get(1),instance.get(2),instance.get(5));
                datePickerDialog.show();
            }
        });


        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference(Constants.TASK).child(userId);
        if (getIntent().getExtras()!=null){
            parentCategory= (ParentCategory) getIntent().getExtras().get("cat");
        }

        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();
            }
        });
    }
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            binding.editTextCurrentYear.setText(addZero(year));
            binding.editTextCurrentMonth.setText(addZero(monthOfYear + 1));
            binding.editTextCurrentDay.setText(addZero(dayOfMonth));

        }
    };
    private void setUpCurrentDate(){
        Calendar c=Calendar.getInstance();
        binding.editTextCurrentYear.setText(java.lang.String.valueOf(c.get(Calendar.YEAR)));
        binding.editTextCurrentMonth.setText(addZero(c.get(Calendar.MONTH) + 1));
        binding.editTextCurrentDay.setText(addZero(c.get(Calendar.DAY_OF_MONTH)));
    }
    private static String addZero(int number) {
        String n;
        if (number < 10) {
            n = "0" + number;
        } else {
            n = Integer.toString(number);
        }
        return n;
    }



    private void uploadPost() {
        progressDialog.setMessage("Please Wait...");
        String catName= parentCategory.getCatParentName();
        String catId= parentCategory.getCatId();
        String title=binding.titleEditText.getText().toString();
        String detail= binding.detailEditText.getText().toString();
        String location= "Faisalabad";
        String budget = binding.budgetEditText.getText().toString();
        String date = binding.editTextCurrentDay.getText().toString()+"-"+binding.editTextCurrentMonth.getText().toString()+"-"+binding.editTextCurrentYear.getText().toString();
        String key=mRef.push().getKey();
        TaskModel task=new TaskModel(key,userId,title,detail,catId,catName,location,budget,date);

        if (title.isEmpty()){
            Toasty.show(this,"Please Enter Title");
        }else if (detail.isEmpty()){
            Toasty.show(this,"Please Enter Details");
        }else if (budget.isEmpty()){
            Toasty.show(this,"Please Enter Your Budget");
        }else {
            assert key != null;
            mRef.child(key).setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toasty.show(PostActivity.this,"Uploaded");
                    }else {
                        Toasty.show(PostActivity.this, "Something went wrong");
                    }
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toasty.show(PostActivity.this , e.toString());
                    progressDialog.dismiss();
                }
            });
        }
    }
}