package com.myapp.serviceapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.myapp.serviceapp.activities.LoginActivity;
import com.myapp.serviceapp.activities.admin_panel.AdminActivity;
import com.myapp.serviceapp.activities.user_panel.UploadCnicActivity;
import com.myapp.serviceapp.databinding.FragmentHomeBinding;
import com.myapp.serviceapp.databinding.FragmentProfileBinding;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.model.User;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SharedPrefsManager sharedPrefsManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        sharedPrefsManager=new SharedPrefsManager(requireContext());
        getProfileData();
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        binding.btnCnic.setOnClickListener(v -> startActivity(new Intent(requireActivity(), UploadCnicActivity.class)));
        return binding.getRoot();


    }

    private void getProfileData() {
        User user=sharedPrefsManager.getUser();
        binding.name.setText(user.getName());
        binding.email.setText(user.getEmail());
        binding.address.setText(user.getAddress());
        binding.cnic.setText(user.getCnic());
        binding.phone.setText(user.getPhone());
    }


}
