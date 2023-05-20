package com.myapp.serviceapp.activities.admin_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myapp.serviceapp.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    private ActivityCategoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}