package com.myapp.serviceapp.activities.user_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.myapp.serviceapp.databinding.ActivityTaskDetailsBinding;

public class TaskDetailsActivity extends AppCompatActivity {
    ActivityTaskDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}