package com.myapp.serviceapp.activities.admin_panel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityUpdateCategoryBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.ArrayList;

public class UpdateCategoryActivity extends AppCompatActivity {
    private ActivityUpdateCategoryBinding binding;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    ArrayList<ParentCategory> list = new ArrayList<>();


    private String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase=FirebaseDatabase.getInstance();
        mRef=FirebaseDatabase.getInstance().getReference(Constants.CATEGORIES);
        ParentCategory category= (ParentCategory) getIntent().getExtras().get("category");
        binding.catNameEditText.setText(category.getCatParentName());
        setupDropDown();
        parentId= category.getCatParentId();
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child(Constants.CATEGORIES).child(category.getCatId())
                        .setValue(new ParentCategory(category.getCatId(),binding.catNameEditText.getText().toString(),parentId))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateCategoryActivity.this,"Category Updated",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private void setupDropDown() {
        mRef.orderByChild("catParentId").equalTo("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapchat : snapshot.getChildren()) {
                    ParentCategory parentCategory= dataSnapchat.getValue(ParentCategory.class);
                    list.add(parentCategory);
                    assert parentCategory != null;
                    if (parentCategory.getCatId().equals(parentCategory))
                           binding.categoryList.setText(parentCategory.getCatParentName());

                }
                ArrayAdapter<ParentCategory> adapter = new ArrayAdapter<ParentCategory>(UpdateCategoryActivity.this, android.R.layout.simple_dropdown_item_1line, list) {
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
}