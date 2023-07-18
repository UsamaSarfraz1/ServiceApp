package com.myapp.serviceapp.activities.user_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.adapter.CategoryAdapter;
import com.myapp.serviceapp.databinding.ActivityMainCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.ArrayList;

public class MainCategoryActivity extends AppCompatActivity {
    ActivityMainCategoryBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private ProgressDialog progressDialog;
    private ParentCategory parentCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase= FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child(Constants.CATEGORIES);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (getIntent().getExtras()!=null){
            parentCategory= (ParentCategory) getIntent().getExtras().get("cat");
        }
        getDate();
    }

    private void getDate() {
        ArrayList<ParentCategory> list = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(list, this,"child");
        binding.rvCategory.setAdapter(categoryAdapter);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapchat : snapshot.getChildren()) {
                    ParentCategory category = dataSnapchat.getValue(ParentCategory.class);


                    if (parentCategory.getCatId().equals(category.getCatParentId())) {
                        list.add(category);
                    }
                }
                categoryAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}