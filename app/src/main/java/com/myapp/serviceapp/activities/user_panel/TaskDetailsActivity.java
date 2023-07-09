package com.myapp.serviceapp.activities.user_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.adapter.OfferAdapter;
import com.myapp.serviceapp.databinding.ActivityTaskDetailsBinding;
import com.myapp.serviceapp.dialog.ReviewDialogFragment;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.Offers;
import com.myapp.serviceapp.model.Reviews;
import com.myapp.serviceapp.model.TaskModel;
import com.myapp.serviceapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailsActivity extends AppCompatActivity implements OfferAdapter.OfferCallBacks ,ReviewDialogFragment.ReviewDialogListener{
    ActivityTaskDetailsBinding binding;
    TaskModel taskModel;
    FirebaseDatabase mdatabase;
    SharedPrefsManager sharedPrefsManager;
    DatabaseReference mRef;
    OfferAdapter offerAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        sharedPrefsManager=new SharedPrefsManager(this);
        taskModel= (TaskModel) getIntent().getExtras().get("task");
        mdatabase=FirebaseDatabase.getInstance();
        mRef=mdatabase.getReference();
        updateTaskData();

        if (taskModel.getUserId().equals(sharedPrefsManager.getUser().getUserId())||sharedPrefsManager.getRole().equals("client")){
            binding.btnOffer.setVisibility(View.GONE);
        }
        binding.btnOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefsManager.getUser().getStatus().equals("close")){
                    new AlertDialog.Builder(TaskDetailsActivity.this)
                            .setMessage("Already Have Task")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }else{
                    if (taskModel.getOrderlist().size()>0){
                        mRef.child(Constants.TASK).child(taskModel.getUserId()).child(taskModel.getTaskId()).child("orderlist").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren() ) {
                                    Offers offer=dataSnapshot.getValue(Offers.class);
                                    if (!offer.getFreelancer_id().equals(sharedPrefsManager.getUser().getUserId())) {
                                        makeOffer();
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        makeOffer();
                    }
                }
            }

        });



    }

    private void updateTaskData() {
        mRef.child(Constants.TASK).child(taskModel.getUserId()).child(taskModel.getTaskId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
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
                        taskModel=new TaskModel(taskId,userId,taskTitle,taskDetails,catId,catName,location,budget,date,status,taskUser);
                        List<Offers> orderlist=new ArrayList<>();
                        for (DataSnapshot listshot : snapshot1.child("orderlist").getChildren()) {
                            Offers offers=listshot.getValue(Offers.class);
                            orderlist.add(offers);
                        }
                        taskModel.setOrderlist(orderlist);
                        updateView();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void updateView() {
        mRef.child(Constants.USERS).child(taskModel.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                String userName= user.getName().toString();
                    binding.userName.setText(userName);
                Log.i("TaskDetails",userName);
                binding.address.setText(taskModel.getLocation());
                String price="Rs : "+ taskModel.getBudget();
                binding.price.setText(price);
                binding.title.setText(taskModel.getTaskTitle());
                binding.taskDetail.setText(taskModel.getTaskDetails());
                binding.date.setText(taskModel.getDate());

                if (!taskModel.getStatus().equals("open"))
                {
                    binding.btnOffer.setClickable(false);
                    binding.btnOffer.setBackgroundColor(Color.LTGRAY);
                    binding.btnOffer.setEnabled(false);
                    binding.btnOffer.setText("Assigned");
                    binding.btnOffer.setTextColor(Color.WHITE);
                }

                setUpRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUpRecyclerView() {
        progressDialog.dismiss();
        boolean status= taskModel.getUserId().equals(sharedPrefsManager.getUser().getUserId());
        if (taskModel.getOrderlist()!=null) {
            Log.i("size",""+taskModel.getOrderlist().size());
            offerAdapter = new OfferAdapter(this, taskModel.getOrderlist(), status,this);
            binding.rvOffers.setAdapter(offerAdapter);
        }else{
            Log.i("size",""+null);
        }
    }

    public void makeOffer() {
        String key = mRef.push().getKey();
        mRef.child(Constants.USERS).child(sharedPrefsManager.getUser().getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                String totalReviews;
                if (user.getReviewsList() != null)
                    totalReviews = "" + user.getReviewsList().size();
                else
                    totalReviews = "0";

                Offers offers = new Offers(
                        key, totalReviews, 5, user.getPhone(), user.getName(), user.getUserId(),false,false
                );
                mRef.child(Constants.TASK).child(taskModel.getUserId()).child(taskModel.getTaskId()).child("orderlist").child(key).setValue(offers)
                        .addOnCompleteListener(task -> {

                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    public void onClickAssign(int position) {
      updateDatabase(position,"Assigned","Task Assigned","close","close","assign");

    }

    public void updateDatabase(int position,String status,String msg ,String assignStatusFreelancer,String userStatus,String tag){
        Offers offers=taskModel.getOrderlist().get(position);
        if (tag.equals("assign"))
            offers.setAssigned(true);
        else if(tag.equals("complete"))
            offers.setCompleted(true);

        taskModel.setStatus(status);
        taskModel.setAssignUser(offers.getFreelancer_id());
        mRef.child(Constants.TASK).child(taskModel.getUserId()).child(taskModel.getTaskId())
                .setValue(taskModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        mRef.child(Constants.USERS).child(offers.getFreelancer_id()).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String status1 =snapshot.getValue(String.class);
                                if (status1.equals("close") && tag.equals("assign")){
                                    Toasty.show(TaskDetailsActivity.this,"Currently working on other Task");
                                }else{
                                    mRef.child(Constants.USERS).child(offers.getFreelancer_id()).child("status")
                                            .setValue(userStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toasty.show(TaskDetailsActivity.this,msg);
                                                        sharedPrefsManager.setUserStatus(assignStatusFreelancer);
                                                    }
                                                }
                                            });
                                }
                                offerAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });


    }

    @Override
    public void onClickComplete(int position) {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to mark as complete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateDatabase(position,"Completed","Task Completed","open","open","complete");
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onClickReview(int position) {
        showReviewDialog(position);

    }

    private void showReviewDialog(int position) {
        ReviewDialogFragment dialog = new ReviewDialogFragment(position);
        dialog.show(getSupportFragmentManager(), "review_dialog");
    }

    @Override
    public void onReviewSubmitted(float rating, String comment,int position) {
        String key=mRef.push().getKey();
        Reviews reviews=new Reviews(rating,comment);
        mRef.child(Constants.USERS).child(taskModel.getOrderlist().get(position).getFreelancer_id()).child("reviewsList").child(key).setValue(reviews)
                .addOnCompleteListener(task -> {
                    Toasty.show(this,"Done");
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.show(TaskDetailsActivity.this,e.getMessage());
                    }
                });

    }
}