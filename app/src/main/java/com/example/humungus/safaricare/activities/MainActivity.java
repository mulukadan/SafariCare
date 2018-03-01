package com.example.humungus.safaricare.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.example.humungus.safaricare.R;
import com.example.humungus.safaricare.fragments.homeFragment;
import com.example.humungus.safaricare.fragments.infoFragment;
import com.example.humungus.safaricare.fragments.searchFragment;
import com.example.humungus.safaricare.fragments.tweetFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new homeFragment());
    }

    private boolean loadFragment(android.support.v4.app.Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment )
                    .commit();

            return true;
        }
    return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        android.support.v4.app.Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.navigation_home:
                fragment = new homeFragment();
                break;

            case R.id.navigation_dashboard:
                fragment = new tweetFragment();
                break;

            case R.id.navigation_search:
                fragment = new searchFragment();
                break;

            case R.id.navigation_info:
                fragment = new infoFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
