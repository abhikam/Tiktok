package com.codencolors.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.codencolors.myapplication.R;
import com.codencolors.myapplication.fragments.AddFragment;
import com.codencolors.myapplication.fragments.DiscoverFragment;
import com.codencolors.myapplication.fragments.HomeFragment;
import com.codencolors.myapplication.fragments.InboxFragment;
import com.codencolors.myapplication.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.bottom_navigation_home) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setToolbar();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void setToolbar(){
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.transparent));
                fragment = new HomeFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_discover:
                bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.black));
                fragment = new DiscoverFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_add:
                bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.black));
                fragment = new AddFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_inbox:
                bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.black));
                fragment = new InboxFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_me:
                bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.black));
                fragment = new ProfileFragment();
                loadFragment(fragment);
                return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
