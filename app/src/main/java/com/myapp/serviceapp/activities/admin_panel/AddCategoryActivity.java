package com.myapp.serviceapp.activities.admin_panel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.adapter.ParentCategoryAdapter;
import com.myapp.serviceapp.databinding.ActivityAddCategoryBinding;
import com.myapp.serviceapp.databinding.ActivityCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.ChildCategory;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.ArrayList;

public class AddCategoryActivity extends AppCompatActivity {
    private ActivityAddCategoryBinding binding;
    private DatabaseReference mRef;
    ArrayList<ParentCategory> list = new ArrayList<>();
    private String parentId;

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
        
        setupDropDown();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });


        /*binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });*/
    }

    private void setupDropDown() {
        mRef.orderByChild("catParentId").equalTo("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapchat : snapshot.getChildren()) {
                    ParentCategory parentCategory= dataSnapchat.getValue(ParentCategory.class);
                    list.add(parentCategory);
                }
                ArrayAdapter<ParentCategory> adapter = new ArrayAdapter<ParentCategory>(AddCategoryActivity.this, android.R.layout.simple_dropdown_item_1line, list) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
                        }

                        TextView textView = convertView.findViewById(android.R.id.text1);
                        ParentCategory category = getItem(position);

                        if (category != null) {
                            textView.setText(category.getCatParentName());
                        }

                        return convertView;
                    }


                };
                binding.categoryList.setAdapter(adapter);

                binding.categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ParentCategory parentCategory=(ParentCategory) parent.getItemAtPosition(position);
                        parentId=parentCategory.getCatId();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createCategory() {
        String categoryName = binding.catNameEditText.getText().toString().trim();
        String categoryDesc = binding.catDescEditText.getText().toString().trim();

        if (parentId==null){
            Toasty.show(this,"Please Select Parent Category");
        }else if (!categoryName.isEmpty()) {
            String categoryId = mRef.push().getKey();
           /* ChildCategory childCategory=new ChildCategory(categoryId,categoryName,categoryDesc);*/
            ParentCategory parentCategory=new ParentCategory(categoryId,categoryName,parentId,"");
            mRef.child(categoryId).setValue(parentCategory)
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

