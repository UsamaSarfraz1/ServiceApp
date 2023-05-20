package com.myapp.serviceapp.activities.admin_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityAdminBinding;
import com.myapp.serviceapp.model.User;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
}