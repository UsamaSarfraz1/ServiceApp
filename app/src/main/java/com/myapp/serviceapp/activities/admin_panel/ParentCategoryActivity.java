package com.myapp.serviceapp.activities.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.adapter.ParentCategoryAdapter;
import com.myapp.serviceapp.databinding.ActivityParentCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.ArrayList;

public class ParentCategoryActivity extends AppCompatActivity implements ParentCategoryAdapter.OnItemButtonClickListener {
    private ActivityParentCategoryBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    ArrayList<ParentCategory> list;
    ParentCategoryAdapter adapter;
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
        list = new ArrayList<>();
        adapter = new ParentCategoryAdapter(list,ParentCategoryActivity.this,this);
        binding.rvParentCategory.setAdapter(adapter);
        mRef.orderByChild("catParentId").equalTo("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapchat : snapshot.getChildren()) {
                    ParentCategory parentCategory= dataSnapchat.getValue(ParentCategory.class);
                    list.add(parentCategory);
                }
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    @Override
    public void onDeleteItem(int position) {
        mRef.child(list.get(position).getCatId()).removeValue();
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onEditItem(int position) {
        Intent intent=new Intent(ParentCategoryActivity.this,AddCategoryActivity.class);
        intent.putExtra("category",list.get(position));
        startActivity(intent);
    }
}