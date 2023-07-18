package com.myapp.serviceapp.activities.user_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.myapp.serviceapp.R;
import com.myapp.serviceapp.databinding.ActivityHomeBinding;
import com.myapp.serviceapp.fragments.AllTaskFragment;
import com.myapp.serviceapp.fragments.FreelancerTask;
import com.myapp.serviceapp.fragments.HomeFragment;
import com.myapp.serviceapp.fragments.PostTaskFragment;
import com.myapp.serviceapp.fragments.ProfileFragment;
import com.myapp.serviceapp.helper.SharedPrefsManager;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    private ActivityHomeBinding binding;
    SharedPrefsManager sharedPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefsManager=new SharedPrefsManager(this);
        replaceFragment(new AllTaskFragment(),"home");
        binding.bottomNav.setOnItemSelectedListener(this);
    }

    // Example function to replace a fragment in the container
    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.host_fragment, fragment, tag);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=new Fragment();
        switch (item.getItemId()){
            case R.id.nav_myTask:
                if(sharedPrefsManager.getUser().getRole().equals("client"))
                    fragment=new HomeFragment();
                else
                    fragment=new FreelancerTask();
                break;
            case R.id.nav_post:
                fragment=new PostTaskFragment();
                break;
            case R.id.nav_profile:
                fragment=new ProfileFragment();
                break;
            case R.id.nav_task:
                fragment=new AllTaskFragment();
                break;
        }

        replaceFragment(fragment,"");
        return true;
    }
}