package com.myapp.serviceapp.activities.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityAddParentCategoryBinding;
import com.myapp.serviceapp.databinding.ActivityUpdateCategoryBinding;
import com.myapp.serviceapp.databinding.ActivityUpdateParentCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.HashMap;
import java.util.Map;

public class UpdateParentCategory extends AppCompatActivity {
    private ActivityUpdateParentCategoryBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUpdateParentCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRef=FirebaseDatabase.getInstance().getReference(Constants.CATEGORIES);
        ParentCategory parentCategory= (ParentCategory) getIntent().getExtras().get("category");
        binding.addCategory.setText(parentCategory.getCatParentName());
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> updateCatMap=new HashMap<>();
                updateCatMap.put(parentCategory.getCatId(),new ParentCategory(
                        parentCategory.getCatId()
                        ,binding.addCategory.getText().toString()
                        ,parentCategory.getCatParentId())
                );
                mRef.updateChildren(updateCatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateParentCategory.this,"Updated",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}