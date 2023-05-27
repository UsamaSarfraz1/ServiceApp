package com.myapp.serviceapp.activities.user_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}