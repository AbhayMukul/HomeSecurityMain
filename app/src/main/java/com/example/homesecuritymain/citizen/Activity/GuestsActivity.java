package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Fragment.FragmentGuestActive;
import com.example.homesecuritymain.citizen.Fragment.FragmentGuestAll;
import com.example.homesecuritymain.citizen.FragmentAdapter.ViewPageAdapterGuestActivity;
import com.google.android.material.tabs.TabLayout;

public class GuestsActivity extends AppCompatActivity {

    //Tab Layout
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guests);

        initialize();

        setTabLayout();

    }

    private void setTabLayout() {
        final ViewPageAdapterGuestActivity viewPageAdapter = new ViewPageAdapterGuestActivity(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPageAdapter.addFragment(FragmentGuestActive.getInstance(), "Active Guest");
                viewPageAdapter.addFragment(FragmentGuestAll.getInstance(), "All Guest");

                viewPager.setAdapter(viewPageAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void initialize() {
        tabLayout = findViewById(R.id.Tl_GuestActivity);
        viewPager = findViewById(R.id.Vp_GuestActivity);
    }
}