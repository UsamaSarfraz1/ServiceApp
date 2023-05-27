package com.myapp.serviceapp.activities.admin_panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityAddParentCategoryBinding;
import com.myapp.serviceapp.databinding.ActivityParentCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.ParentCategory;

public class AddParentCategoryActivity extends AppCompatActivity {
    private ActivityAddParentCategoryBinding binding;
    private DatabaseReference categoryRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddParentCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        categoryRef = database.getReference(Constants.CATEGORIES);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });
    }

    private void createCategory() {
        String categoryName = binding.addCategory.getText().toString().trim();

        if (!categoryName.isEmpty()) {
            String categoryId = categoryRef.push().getKey();
            ParentCategory parentCategory=new ParentCategory(categoryId,categoryName);
            categoryRef.child(categoryId).setValue(parentCategory)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Perform any additional actions or show success message
                            Toasty.show(this,"Category created");
                        } else {
                            // Handle error or show error message
                            Toasty.show(this,"There is problem try again");
                        }
                    });
        } else {
            // Show error message that category name is empty
            Toasty.show(this,"Category Name is empty");
        }
    }
}