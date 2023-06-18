package com.myapp.serviceapp.activities.admin_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.activities.LoginActivity;
import com.myapp.serviceapp.databinding.ActivityAdminBinding;
import com.myapp.serviceapp.model.ChildCategory;
import com.myapp.serviceapp.model.User;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });


        binding.btnFreelancers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,FreelancerActivity.class);
                startActivity(intent);
            }
        });

        binding.btnChildCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });

        binding.btnParentCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,ParentCategoryActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}