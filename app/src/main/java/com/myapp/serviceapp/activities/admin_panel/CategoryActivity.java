package com.myapp.serviceapp.activities.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.adapter.ParentCategoryAdapter;
import com.myapp.serviceapp.databinding.ActivityCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ActivityCategoryBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase=FirebaseDatabase.getInstance();
        mRef=FirebaseDatabase.getInstance().getReference(Constants.CATEGORIES);
        setUpRecyclerView();
        binding.btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, AddCategoryActivity.class));
            }
        });


    }

    private void setUpRecyclerView() {
        ArrayList<ParentCategory> list = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapchat : snapshot.getChildren()) {
                    ParentCategory parentCategory= dataSnapchat.getValue(ParentCategory.class);
                    if (!parentCategory.getCatParentId().equals("")) {
                        list.add(parentCategory);
                    }
                }
                ParentCategoryAdapter adapter = new ParentCategoryAdapter(list,CategoryActivity.this);
                binding.rvCategory.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}