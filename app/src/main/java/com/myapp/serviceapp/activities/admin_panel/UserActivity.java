package com.myapp.serviceapp.activities.admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.adapter.UserAdapter;
import com.myapp.serviceapp.databinding.ActivityUserBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;
    private List<User> userList;
    private UserAdapter userAdapter;
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize the Firebase Realtime Database reference
        usersRef = FirebaseDatabase.getInstance().getReference(Constants.USERS);

        // Initialize the user list
        userList = new ArrayList<>();

        // Set up the RecyclerView adapter
        userAdapter = new UserAdapter(userList, this);
        binding.rvUser.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUser.setAdapter(userAdapter);

        // Retrieve the user list from Firebase
        retrieveUserList();

    }

    private void retrieveUserList() {
        usersRef.orderByChild("role").equalTo("client").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing user list
                userList.clear();

                // Iterate through the dataSnapshot to get each user
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve the user data
                    User user = userSnapshot.getValue(User.class);

                    // Add the user to the list
                    userList.add(user);
                }

                // Notify the adapter that the data has changed
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors during data retrieval
                Toast.makeText(UserActivity.this, "Failed to retrieve user list", Toast.LENGTH_SHORT).show();
            }
        });
    }

}