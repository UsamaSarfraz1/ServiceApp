package com.myapp.serviceapp.activities.admin_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.adapter.ParentCategoryAdapter;
import com.myapp.serviceapp.databinding.ActivityParentCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.ArrayList;

public class ParentCategoryActivity extends AppCompatActivity {
    private ActivityParentCategoryBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityParentCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase=FirebaseDatabase.getInstance();
        mRef=FirebaseDatabase.getInstance().getReference(Constants.CATEGORIES);
        setUpRecyclerView();
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentCategoryActivity.this,AddParentCategoryActivity.class));
            }
        });

    }

    private void setUpRecyclerView() {
        ParentCategoryAdapter adapter = new ParentCategoryAdapter(getList(),this);
        binding.rvParentCategory.setAdapter(adapter);
    }


    private ArrayList<ParentCategory> getList(){
        ArrayList<ParentCategory> list = new ArrayList<>();

        return list;
    }
}