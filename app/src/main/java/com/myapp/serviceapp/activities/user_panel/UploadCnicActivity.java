package com.myapp.serviceapp.activities.user_panel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myapp.serviceapp.MainActivity;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityUploadCnicBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadCnicActivity extends AppCompatActivity {
    ActivityUploadCnicBinding binding;
    Uri imageUri;

    ProgressDialog mLoadingBar;

    Bitmap bmp;
    ByteArrayOutputStream baos;
    StorageReference storageRef;
    DatabaseReference mRef;
    SharedPrefsManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUploadCnicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manager=new SharedPrefsManager(this);
        mRef= FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference().child("Images");
        binding.btnCnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        
        binding.btnUploadCnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageWithCompress();
            }
        });
    }

    private void uploadImageWithCompress() {
        mLoadingBar =  new ProgressDialog(UploadCnicActivity.this);
        mLoadingBar.setTitle("Uploading Image with Compress");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        // images are stored with timestamp as their name
        String timestamp = "" + System.currentTimeMillis();

        bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        baos = new ByteArrayOutputStream();

        // here we can choose quality factor
        // in third parameter(ex. here it is 25)
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);

        byte[] fileInBytes = baos.toByteArray();

        storageRef.child(timestamp);
        UploadTask uploadTask=storageRef.putBytes(fileInBytes);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                if (task.isSuccessful()) {
                                    // Image URL is available, use it as needed
                                    Uri imageUrl = task.getResult();
                                    String imageUrlString = imageUrl.toString();
                                    mRef.child(Constants.USERS).child(manager.getUser().getUserId()).child("cnicUrl").setValue(imageUrlString)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            mLoadingBar.dismiss();
                                                        }
                                                    });
                                    Log.i("path",imageUrlString);
                                    // Here, you can save the URL to your database if needed
                                    // For example, you can save it to Cloud Firestore
                                    // FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    // String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    // db.collection("users").document(userId).update("imageUrl", imageUrlString);
                                } else {
                                    // Handle the error
                                    Toast.makeText(UploadCnicActivity.this, "Error getting image URL.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Toast.makeText(UploadCnicActivity.this,"Image uploaded successfully!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mLoadingBar.dismiss();
                        Toast.makeText(UploadCnicActivity.this,"Image upload failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        imageUri = data.getData();
                        binding.cnicImage.setImageURI(imageUri);

                    }
                }
            });
}