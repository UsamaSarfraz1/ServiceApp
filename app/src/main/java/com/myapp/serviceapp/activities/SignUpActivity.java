package com.myapp.serviceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myapp.serviceapp.MainActivity;
import com.myapp.serviceapp.activities.user_panel.HomeActivity;
import com.myapp.serviceapp.databinding.ActivitySignUpBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.User;


public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    String selectedOption;
    private SharedPrefsManager sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences=new SharedPrefsManager(this);

        binding.radioButton.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            selectedOption = radioButton.getText().toString();
            Toast.makeText(SignUpActivity.this, "Selected Option: " + selectedOption, Toast.LENGTH_SHORT).show();
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.editTextEmail.getText().toString();
                String password=binding.editTextPassword.getText().toString();
                String name=binding.editTextName.getText().toString();
                String address=binding.editTextAddress.getText().toString();
                String phone=binding.editTextPhone.getText().toString();
                String cnic=binding.editTextCNIC.getText().toString();
                if (name.isEmpty()){
                    Toasty.show(SignUpActivity.this,"Please enter name");
                }else if (email.isEmpty() ){
                    Toasty.show(SignUpActivity.this,"Please enter email");
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toasty.show(SignUpActivity.this,"Please enter correct formated email");
                }
                else if(password.isEmpty()){
                    Toasty.show(SignUpActivity.this,"Please enter password");
                }else if(address.isEmpty()){
                    Toasty.show(SignUpActivity.this,"Please enter address");
                }else if (phone.isEmpty()){
                    Toasty.show(SignUpActivity.this,"Please enter phone");
                }else if (cnic.isEmpty()){
                    Toasty.show(SignUpActivity.this, "Please enter cnic");
                }else{
                    binding.progressBar.setVisibility(View.VISIBLE);
                    signUpWithEmail(name,email,password,address,phone,cnic);
                }
            }
        });

    }


    private void signUpWithEmail(String name, String email, String password, String address, String phone, String cnic) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User sign-up successful
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();

                            // Create a user object with the provided information
                            User newUser = new User(userId, name, email, address, phone, cnic,selectedOption.toLowerCase());

                            // Save the user object to the Firebase Realtime Database
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference(Constants.USERS);
                            usersRef.child(userId).setValue(newUser)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            // User data saved successfully
                                            // Do something else if needed
                                            sharedPreferences.saveUser(newUser);
                                            sharedPreferences.saveRole(selectedOption);
                                            binding.progressBar.setVisibility(View.GONE);
                                            Intent intent=new Intent(SignUpActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                            Toasty.show(this,"Welcome");
                                        } else {
                                            // Error occurred while saving user data
                                            String errorMessage = task1.getException().getMessage();
                                            // Handle the error
                                            Toasty.show(this,errorMessage);
                                        }
                                    });


                        }
                    } else {
                        // Sign-up failed

                        String errorMessage = task.getException().getMessage();
                        // Handle the error
                        binding.progressBar.setVisibility(View.GONE);
                        Toasty.show(this,errorMessage);
                    }
                });
    }

}