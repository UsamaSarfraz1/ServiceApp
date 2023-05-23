package com.myapp.serviceapp.activities.admin_panel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityAddCategoryBinding;
import com.myapp.serviceapp.databinding.ActivityCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.ChildCategory;
import com.myapp.serviceapp.model.ParentCategory;

public class AddCategoryActivity extends AppCompatActivity {
    private ActivityAddCategoryBinding binding;
    private DatabaseReference mRef;
    // Registers a photo picker activity launcher in single-select mode.
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: " + uri);
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        mRef=database.getReference().child(Constants.CATEGORIES);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });

        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
    }

    private void createCategory() {
        String categoryName = binding.catNameEditText.getText().toString().trim();
        String categoryDesc = binding.catDescEditText.getText().toString().trim();

        if (!categoryName.isEmpty()) {
            String categoryId = mRef.push().getKey();
            ChildCategory childCategory=new ChildCategory(categoryId,categoryName,categoryDesc,cati)
            ParentCategory parentCategory=new ParentCategory(categoryId,categoryName);
            mRef.child(categoryId).setValue(categoryName)
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