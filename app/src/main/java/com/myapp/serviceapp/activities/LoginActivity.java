package com.myapp.serviceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.activities.admin_panel.AdminActivity;
import com.myapp.serviceapp.activities.user_panel.ForgotPasswordActivity;
import com.myapp.serviceapp.activities.user_panel.HomeActivity;
import com.myapp.serviceapp.databinding.ActivityLoginBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.User;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;
    private SharedPrefsManager sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences=new SharedPrefsManager(this);
        firebaseAuth = FirebaseAuth.getInstance();

        usersRef = FirebaseDatabase.getInstance().getReference(Constants.USERS);
        if (firebaseAuth.getCurrentUser()!=null){
            String userRole=sharedPreferences.getRole();
            switch (userRole) {
                case "client":
                case "freelancer":
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                    break;
                case "admin":
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    finish();
                    break;
            }
        }

        binding.btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    private void signIn() {
        // Use the appropriate sign-in method based on your app's requirements
        // Example: Email/Password Sign-In
        binding.progressBar.setVisibility(View.VISIBLE);
        String email = binding.emailEdittext.getText().toString();
        String password = binding.passwordEdittext.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toasty.show(this,"Please enter valid email address");
            binding.progressBar.setVisibility(View.GONE);
        }else if (password.isEmpty()){
            Toasty.show(this,"Please enter password");
            binding.progressBar.setVisibility(View.GONE);
        }else{
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign-in success, update UI with the signed-in user's information
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                usersRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User user=snapshot.getValue(User.class);
                                        sharedPreferences.saveUser(user);
                                        switch (user.getRole()) {
                                            case "admin": {
                                                sharedPreferences.saveRole("admin");
                                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                                startActivity(intent);
                                                finish();
                                                break;
                                            }
                                            case "client": {
                                                sharedPreferences.saveRole("client");
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                                break;
                                            }
                                            case "freelancer":
                                                sharedPreferences.saveRole("freelancer");
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                break;
                                        }
                                        binding.progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toasty.show(LoginActivity.this,error.getMessage());
                                    }
                                });


                                // Handle the signed-in user
                            } else {
                                // Sign-in failed, display an error message
                                binding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }

    }
}