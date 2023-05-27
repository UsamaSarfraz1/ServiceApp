package com.myapp.serviceapp.activities.user_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}